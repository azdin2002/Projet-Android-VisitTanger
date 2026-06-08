package com.groupe10.visittanger.ui.itinerary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.groupe10.visittanger.R
import com.groupe10.visittanger.ui.theme.toLocalizedLabel
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.groupe10.visittanger.domain.model.Itinerary
import com.groupe10.visittanger.domain.model.*

@Composable
fun FeaturedItineraryCard(
    itinerary: Itinerary,
    currentLang: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // AsyncImage fullwidth
            AsyncImage(
                model = itinerary.coverPhoto,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Overlay gradient noir en bas pour lisibilité
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                            startY = 300f
                        )
                    )
            )

            // Badge type coloré en haut à gauche
            Surface(
                color = Color(itinerary.type.color),
                contentColor = Color.White,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = "${itinerary.type.emoji} ${itinerary.type.toLocalizedLabel()}",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                // Titre blanc bold 20sp
                Text(
                    text = itinerary.localizedTitle(currentLang),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Row infos: durée + distance + difficulté
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoItem(icon = Icons.Default.AccessTime, text = itinerary.localizedDuration(currentLang))
                    InfoItem(icon = Icons.Default.Route, text = "${itinerary.totalDistanceKm} km")
                    InfoItem(icon = Icons.Default.DirectionsRun, text = itinerary.localizedDifficulty(currentLang))
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tags chips en bas
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    itinerary.tags.take(3).forEach { tag ->
                        Surface(
                            color = Color.White.copy(alpha = 0.2f),
                            contentColor = Color.White,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = tag,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
