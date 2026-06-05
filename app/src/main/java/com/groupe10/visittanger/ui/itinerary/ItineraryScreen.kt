package com.groupe10.visittanger.ui.itinerary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.ItineraryType
import com.groupe10.visittanger.ui.components.TangerTopBar
import com.groupe10.visittanger.ui.theme.*

@Composable
fun ItineraryScreen(
    onItineraryClick: (String) -> Unit,
    viewModel: ItineraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = { TangerTopBar(title = "My Journeys") },
        containerColor = StitchBackground,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 100.dp
            )
        ) {
            // Hero Trip Header
            item {
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    AsyncImage(
                        model = R.drawable.img_itinerary_header,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, StitchPrimary.copy(alpha = 0.8f)))))
                    Column(modifier = Modifier.align(Alignment.BottomStart).padding(24.dp)) {
                        Text("My Trip to Tangier", style = MaterialTheme.typography.headlineLarge, color = Color.White)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CalendarMonth, null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Oct 12 — Oct 15, 2026", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                }
            }

            // Timeline Header
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        color = StitchSecondaryContainer,
                        shape = CircleShape,
                        modifier = Modifier.size(56.dp),
                        border = BorderStroke(1.dp, StitchOnSecondaryContainer.copy(alpha = 0.1f))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("1", style = MaterialTheme.typography.headlineMedium, color = StitchOnSecondaryContainer)
                        }
                    }
                    Column {
                        Text("Day 1: Arrival & Old City", style = MaterialTheme.typography.headlineSmall, color = StitchPrimary)
                        Text("Monday, October 12", style = MaterialTheme.typography.labelSmall, color = StitchOnSurfaceVariant)
                    }
                }
            }

            // Timeline Items
            items(uiState.itineraries) { itinerary ->
                TimelineItem(itinerary.title, "9:00 AM", onItineraryClick = { onItineraryClick(itinerary.id) })
            }

            // Add Event Placeholder
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        color = Color.Transparent,
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(2.dp, Brush.linearGradient(listOf(StitchOutlineVariant, Color.Transparent)))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.AddCircle, null, tint = StitchOutline, modifier = Modifier.size(32.dp))
                            Text("Add Activity to Day 1", style = MaterialTheme.typography.labelMedium, color = StitchOutline)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimelineItem(title: String, time: String, onItineraryClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .height(IntrinsicSize.Min)
    ) {
        // Vertical Line with Dot
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(56.dp)) {
            Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(StitchPrimary).border(4.dp, StitchBackground, CircleShape))
            Box(modifier = Modifier.weight(1f).width(2.dp).background(Brush.verticalGradient(listOf(StitchPrimary, StitchSurfaceVariant))))
        }
        
        // Content Card
        Surface(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
                .clickable { onItineraryClick() },
            color = Color.White.copy(alpha = 0.6f),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, StitchOutlineVariant.copy(alpha = 0.3f))
        ) {
            Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp)).background(StitchSurfaceVariant)) {
                     AsyncImage(
                        model = R.drawable.img_home_hero_kasbah,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                     )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(title, style = MaterialTheme.typography.titleMedium, color = StitchPrimary, fontWeight = FontWeight.Bold)
                        Surface(color = StitchSecondaryFixed, shape = CircleShape) {
                            Text(time, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = StitchSecondary)
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text("Explore the heart of the city's most famous square.", style = MaterialTheme.typography.bodySmall, color = StitchOnSurfaceVariant)
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = {}, modifier = Modifier.height(36.dp), shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = StitchPrimary)) {
                            Icon(Icons.Default.Directions, null, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Directions", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}
