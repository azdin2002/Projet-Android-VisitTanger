package com.groupe10.visittanger.ui.itinerary.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.groupe10.visittanger.domain.model.Itinerary
import com.groupe10.visittanger.ui.components.TangerTopBar

@Composable
fun ItineraryDetailScreen(
    itineraryId: String,
    onBackClick: () -> Unit,
    onPlaceClick: (String) -> Unit,
    viewModel: ItineraryDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val itinerary = uiState.itinerary

    Scaffold(
        topBar = {
            TangerTopBar(
                title = itinerary?.title ?: "Détails",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        if (itinerary != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // 1. COVER IMAGE + INFOS GÉNÉRALES
                item { ItineraryCoverSection(itinerary) }

                // 2. RÉSUMÉ (durée, distance, difficulté, étapes)
                item { ItinerarySummarySection(itinerary) }

                // 3. DESCRIPTION
                item {
                    Text(
                        text = itinerary.description,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // 4. TIMELINE DES ÉTAPES
                item {
                    Text(
                        text = "Programme",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, bottom = 12.dp, top = 8.dp)
                    )
                }

                itemsIndexed(itinerary.places) { index, stop ->
                    ItineraryStopItem(
                        stop = stop,
                        isLast = index == itinerary.places.lastIndex,
                        isSelected = index == uiState.currentStopIndex,
                        onClick = {
                            viewModel.onStopSelected(index)
                            onPlaceClick(stop.place.id)
                        }
                    )
                }

                item { Spacer(Modifier.height(80.dp)) }
            }
        } else if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ItineraryCoverSection(itinerary: Itinerary) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        AsyncImage(
            model = itinerary.coverPhoto,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Surface(
                color = Color(itinerary.type.color),
                contentColor = Color.White,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "${itinerary.type.emoji} ${itinerary.type.labelFr}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = itinerary.title,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ItinerarySummarySection(itinerary: Itinerary) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SummaryItem(icon = Icons.Default.AccessTime, label = "Durée", value = itinerary.duration)
        SummaryItem(icon = Icons.Default.Route, label = "Distance", value = "${itinerary.totalDistanceKm} km")
        SummaryItem(icon = Icons.Default.Place, label = "Étapes", value = "${itinerary.places.size}")
        SummaryItem(icon = Icons.Default.DirectionsRun, label = "Difficulté", value = itinerary.difficulty)
    }
}

@Composable
private fun SummaryItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
    }
}
