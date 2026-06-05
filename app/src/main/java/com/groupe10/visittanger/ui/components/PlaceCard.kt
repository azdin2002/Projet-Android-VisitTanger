package com.groupe10.visittanger.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.groupe10.visittanger.ui.theme.*

@Composable
fun PlaceCard(
    place: Place,
    onPlaceClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteColor by animateColorAsState(
        targetValue = if (place.isFavorite) Color.Red else StitchOutline,
        label = "favoriteColor"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPlaceClick(place.id) },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = StitchSurfaceContainerLow),
        border = BorderStroke(1.dp, StitchSurfaceVariant)
    ) {
        Column {
            // Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
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
                            .background(StitchSurfaceVariant.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Place, null, tint = StitchOutline)
                    }
                }
                
                // Star Rating Badge
                Surface(
                    color = StitchSurface.copy(alpha = 0.9f),
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = StitchSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "${place.rating}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = StitchOnSurface
                        )
                    }
                }
            }

            // Content Section
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${place.category.name.uppercase()} • TANGER",
                            style = MaterialTheme.typography.labelSmall,
                            color = StitchSecondary,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = place.name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = StitchPrimary
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    
                    IconButton(
                        onClick = { onFavoriteClick(place.id) },
                        modifier = Modifier.offset(x = 12.dp, y = (-4).dp)
                    ) {
                        Icon(
                            imageVector = if (place.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = favoriteColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = place.description["fr"] ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = StitchOnSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
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
