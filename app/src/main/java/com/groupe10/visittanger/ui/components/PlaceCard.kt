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

@Composable
fun PlaceCard(
    place: Place,
    onPlaceClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteColor by animateColorAsState(
        targetValue = if (place.isFavorite) Color.Red else Color.Gray,
        label = "favoriteColor"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPlaceClick(place.id) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color.LightGray)
            ) {
                if (place.photos.isNotEmpty()) {
                    AsyncImage(
                        model = place.photos.first(),
                        contentDescription = place.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                        tint = Color.Gray
                    )
                }
            }

            // Content Section
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { onFavoriteClick(place.id) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (place.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = favoriteColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val (bgColor, txtColor) = getCategoryColors(place.category)
                    Surface(
                        color = bgColor,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = place.category.labelFr,
                            color = txtColor,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    
                    if (place.distanceKm != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${place.distanceKm} km",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                RatingBar(
                    rating = place.rating,
                    reviewCount = place.reviewCount
                )
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
            description = "Description",
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
