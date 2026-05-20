package com.groupe10.visittanger.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.ui.theme.StitchBackground
import com.groupe10.visittanger.ui.theme.StitchOnSurface
import com.groupe10.visittanger.ui.theme.StitchPrimary

import androidx.compose.foundation.shape.CircleShape

@Composable
fun PlaceCard(
    place: Place,
    onPlaceClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteColor by animateColorAsState(
        targetValue = if (place.isFavorite) Color.Red else Color.Gray.copy(alpha = 0.5f),
        label = "favoriteColor"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPlaceClick(place.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Image Section (Full Bleed)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (place.photos.isNotEmpty()) {
                    AsyncImage(
                        model = place.photos.first(),
                        contentDescription = place.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Gray
                        )
                    }
                }
                
                // Favorite Button Overlay
                IconButton(
                    onClick = { onFavoriteClick(place.id) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = if (place.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = favoriteColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Content Section (Cream background as per DESIGN.md)
            Column(
                modifier = Modifier
                    .background(StitchBackground.copy(alpha = 0.5f))
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontSize = 20.sp,
                        color = StitchOnSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val (_, txtColor) = getCategoryColors(place.category)
                        Text(
                            text = place.category.labelFr.uppercase(),
                            color = StitchPrimary,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                        
                        if (place.distanceKm != null) {
                            Text(
                                text = " • ${place.distanceKm} km",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }

                    RatingBar(
                        rating = place.rating,
                        reviewCount = place.reviewCount
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceCardPreview() {
    PlaceCard(
        place = Place(
            id = "1",
            name = "Kasbah de Tanger",
            description = mapOf("fr" to "Description"),
            category = Category.HISTORY,
            latitude = 0.0,
            longitude = 0.0,
            address = "Tanger",
            photos = emptyList(),
            rating = 4.8f,
            reviewCount = 120,
            distanceKm = 1.2
        ),
        onPlaceClick = {},
        onFavoriteClick = {}
    )
}
