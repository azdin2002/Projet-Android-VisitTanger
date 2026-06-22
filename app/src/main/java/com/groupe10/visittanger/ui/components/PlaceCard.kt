package com.groupe10.visittanger.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.model.localizedName
import com.groupe10.visittanger.domain.model.localizedTeaser
import com.groupe10.visittanger.ui.theme.*
import com.groupe10.visittanger.ui.theme.toLocalizedName

@Composable
fun PlaceCard(
    place: Place,
    onPlaceClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    lang: String = "fr",
) {
    val favoriteColor by animateColorAsState(
        targetValue = if (place.isFavorite) Color.Red else StitchOutline,
        label = "favoriteColor"
    )

    val displayName = place.localizedName(lang)
    val teaserText = place.localizedTeaser(lang)
    val categoryLabel = place.category.toLocalizedName()
    val cityLabel = stringResource(R.string.city_tanger)
    val textDirection = when (lang) {
        "ar" -> TextDirection.Rtl
        else -> TextDirection.Ltr
    }

    val localImageRes = when {
        place.name.contains("Kasbah", ignoreCase = true) -> R.drawable.img_home_hero_kasbah
        place.name.contains("Cap Spartel", ignoreCase = true) -> R.drawable.img_place_cap_spartel
        place.name.contains("Hercule", ignoreCase = true) -> R.drawable.img_place_hercules_caves
        place.name.contains("Medina", ignoreCase = true) || place.name.contains("Médina", ignoreCase = true) -> R.drawable.img_place_medina
        place.name.contains("Socco", ignoreCase = true) -> R.drawable.img_place_grand_socco
        place.name.contains("Malabata", ignoreCase = true) -> R.drawable.img_place_malabata
        place.name.contains("Battouta", ignoreCase = true) || place.name.contains("Stade", ignoreCase = true) -> R.drawable.img_place_stadium
        place.name.contains("Plage de Tanger", ignoreCase = true) -> R.drawable.img_place_beach_city
        place.name.contains("Souk", ignoreCase = true) -> R.drawable.img_place_souk
        place.name.contains("Korsan", ignoreCase = true) -> R.drawable.img_place_restaurant
        place.name.contains("Perdicaris", ignoreCase = true) -> R.drawable.img_place_perdicaris
        place.name.contains("Musée", ignoreCase = true) -> R.drawable.img_place_kasbah_museum
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
                    contentDescription = displayName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
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
                        Icon(Icons.Default.Star, null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(14.dp))
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
                            text = "$categoryLabel • $cityLabel",
                            style = MaterialTheme.typography.labelSmall.copy(textDirection = textDirection),
                            color = MaterialTheme.colorScheme.secondary,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = displayName,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textDirection = textDirection,
                            ),
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
                    style = MaterialTheme.typography.bodyMedium.copy(textDirection = textDirection),
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
            names = mapOf("fr" to "Kasbah de Tanger", "en" to "Tangier Kasbah", "ar" to "قصبة طنجة"),
            description = mapOf("fr" to "Description"),
            teaser = mapOf("fr" to "Une forteresse majestueuse du XVe siècle..."),
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
