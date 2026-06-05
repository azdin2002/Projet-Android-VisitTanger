package com.groupe10.visittanger.ui.itinerary.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.Itinerary
import com.groupe10.visittanger.ui.components.TangerTopBar
import com.groupe10.visittanger.ui.theme.*

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
                title = "Itinerary Details",
                onBackClick = onBackClick
            )
        },
        containerColor = StitchBackground
    ) { paddingValues ->
        if (itinerary != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    bottom = paddingValues.calculateBottomPadding() + 32.dp
                )
            ) {
                // 1. COVER IMAGE
                item { ItineraryCoverSection(itinerary) }

                // 2. RÉSUMÉ (durée, distance, difficulté, étapes)
                item { ItinerarySummarySection(itinerary) }

                // 3. DESCRIPTION
                item {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Overview",
                            style = MaterialTheme.typography.titleLarge,
                            color = StitchPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = itinerary.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = StitchOnSurfaceVariant,
                            lineHeight = 24.sp
                        )
                    }
                }

                // 4. TIMELINE DES ÉTAPES
                item {
                    Text(
                        text = "The Plan",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = StitchPrimary,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
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

                item { Spacer(Modifier.height(40.dp)) }
            }
        } else if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = StitchPrimary)
            }
        }
    }
}

@Composable
fun ItineraryCoverSection(itinerary: Itinerary) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        AsyncImage(
            model = R.drawable.img_welcome_bg, // Use the Moroccan door style image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            placeholder = painterResource(id = R.drawable.img_welcome_bg)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, StitchPrimary.copy(alpha = 0.8f)),
                        startY = 300f
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Surface(
                color = StitchSecondary,
                shape = CircleShape
            ) {
                Text(
                    text = itinerary.type.labelFr.uppercase(),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = itinerary.title,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ItinerarySummarySection(itinerary: Itinerary) {
    Surface(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        color = StitchSurfaceContainerLow,
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, StitchSurfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryItem(icon = Icons.Default.AccessTime, label = "Duration", value = itinerary.duration)
            SummaryItem(icon = Icons.Default.Route, label = "Distance", value = "${itinerary.totalDistanceKm} km")
            SummaryItem(icon = Icons.Default.Place, label = "Stops", value = "${itinerary.places.size}")
            SummaryItem(icon = Icons.Default.DirectionsRun, label = "Level", value = itinerary.difficulty)
        }
    }
}

@Composable
private fun SummaryItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = StitchSecondary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label, 
            style = MaterialTheme.typography.labelSmall, 
            color = StitchOutline
        )
        Text(
            text = value, 
            style = MaterialTheme.typography.labelLarge, 
            fontWeight = FontWeight.Bold,
            color = StitchPrimary
        )
    }
}
