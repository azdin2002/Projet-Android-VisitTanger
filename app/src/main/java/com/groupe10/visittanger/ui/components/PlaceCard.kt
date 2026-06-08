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
import com.groupe10.visittanger.R

@Composable
fun PlaceCard(
    place: Place,
    onPlaceClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    lang: String = "en"
) {
    val favoriteColor by animateColorAsState(
        targetValue = if (place.isFavorite) Color.Red else StitchOutline,
        label = "favoriteColor"
    )

    val teaserText = if (place.teaser[lang].isNullOrBlank()) {
        place.teaser["en"] ?: ""
    } else {
        place.teaser[lang]!!
    }

    val localImageRes = when {
        place.name.contains("Kasbah de Tanger", ignoreCase = true) -> R.drawable.img_home_hero_kasbah
        place.name.contains("Cap Spartel", ignoreCase = true) -> R.drawable.img_place_cap_spartel
        place.name.contains("Grottes d Hercule", ignoreCase = true) -> R.drawable.img_place_hercules_caves
        place.name.contains("Medina de Tanger", ignoreCase = true) -> R.drawable.img_place_medina
        place.name.contains("Grand Socco", ignoreCase = true) -> R.drawable.img_place_grand_socco
        place.name.contains("Plage Malabata", ignoreCase = true) -> R.drawable.img_place_malabata
        place.name.contains("Stade Ibn Battouta", ignoreCase = true) -> R.drawable.img_place_stadium
        place.name.contains("Plage de Tanger", ignoreCase = true) -> R.drawable.img_place_beach_city
        place.name.contains("Souk de Tanger", ignoreCase = true) -> R.drawable.img_place_souk
        place.name.contains("Restaurant El Korsan", ignoreCase = true) -> R.drawable.img_place_restaurant
        place.name.contains("Parc Perdicaris", ignoreCase = true) -> R.drawable.img_place_perdicaris
        place.name.contains("Musée de la Kasbah", ignoreCase = true) -> R.drawable.img_place_kasbah_museum
        place.name.contains("Hafa", ignoreCase = true) -> R.drawable.img_place_cafe_hafa
        else -> null
    }

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                val imageModel = when {
                    place.photos.isNotEmpty() -> place.photos.first()
                    localImageRes != null -> localImageRes
                    else -> R.drawable.welcome_to_tangier_image_2
                }

                AsyncImage(
                    model = imageModel,
                    contentDescription = place.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                Surface(
                    color = StitchSurface.copy(alpha = 0.9f),
                    shape = CircleShape,
                    modifier = Modifier.padding(16.dp).align(Alignment.TopEnd)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, null, tint = StitchSecondary, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(text = "${place.rating}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
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
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = StitchPrimary),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    IconButton(onClick = { onFavoriteClick(place.id) }, modifier = Modifier.offset(x = 12.dp, y = (-4).dp)) {
                        Icon(
                            imageVector = if (place.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = favoriteColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = teaserText,
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
            description = mapOf("en" to "Description"),
            teaser = mapOf("en" to "A majestic 15th-century fortress..."),
            category = Category.HISTORY,
            latitude = 0.0,
            longitude = 0.0,
            address = "Tanger",
            photos = emptyList(),
            rating = 4.8f,
            reviewCount = 120
        ),
        onPlaceClick = {},
        onFavoriteClick = {}
    )
}
