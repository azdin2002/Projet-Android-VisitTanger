package com.groupe10.visittanger.ui.itinerary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
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
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 100.dp
            )
        ) {
            // Hero Trip Header
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(RoundedCornerShape(32.dp))
                ) {
                    AsyncImage(
                        model = R.drawable.img_itinerary_header,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, StitchPrimary.copy(alpha = 0.9f)),
                            startY = 400f
                        )
                    ))
                    Column(modifier = Modifier.align(Alignment.BottomStart).padding(24.dp)) {
                        Surface(color = StitchSecondary, shape = CircleShape) {
                            Text(
                                "ACTIVE TRIP", 
                                color = Color.White, 
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "The Pearl of the Strait", 
                            style = MaterialTheme.typography.headlineLarge, 
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CalendarMonth, null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Oct 12 — Oct 15", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                }
            }

            // Timeline Section Header
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp)) {
                    Text(
                        "Plan for Today", 
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), 
                        color = StitchPrimary
                    )
                    Text(
                        "Monday, October 12 • Arrival & Old City", 
                        style = MaterialTheme.typography.bodyMedium, 
                        color = StitchOnSurfaceVariant
                    )
                }
            }

            // Timeline Items
            itemsIndexed(uiState.itineraries) { index, itinerary ->
                // Map local images based on index for variety
                val placeholderImg = when(index % 4) {
                    0 -> R.drawable.img_home_hero_kasbah
                    1 -> R.drawable.img_place_medina
                    2 -> R.drawable.img_place_grand_socco
                    else -> R.drawable.img_place_cafe_hafa
                }
                
                TimelineItem(
                    title = itinerary.title, 
                    time = "09:30 AM", 
                    image = placeholderImg,
                    isLast = index == uiState.itineraries.size - 1,
                    onItineraryClick = { onItineraryClick(itinerary.id) }
                )
            }

            // Add Event Placeholder
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        color = Color.Transparent,
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, StitchOutlineVariant.copy(alpha = 0.5f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.clickable { /* Add activity */ }
                        ) {
                            Icon(Icons.Default.AddCircleOutline, null, tint = StitchPrimary, modifier = Modifier.size(24.dp))
                            Spacer(Modifier.width(12.dp))
                            Text("Add Activity", style = MaterialTheme.typography.labelLarge, color = StitchPrimary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimelineItem(
    title: String, 
    time: String, 
    image: Int,
    isLast: Boolean,
    onItineraryClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .height(IntrinsicSize.Min)
    ) {
        // Vertical Line and Time Info
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, 
            modifier = Modifier.width(64.dp)
        ) {
            Text(
                time.split(" ")[0], 
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), 
                color = StitchPrimary
            )
            Text(
                time.split(" ")[1], 
                style = MaterialTheme.typography.labelSmall, 
                color = StitchOutline
            )
            
            Spacer(Modifier.height(12.dp))
            
            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(StitchPrimary))
            
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(2.dp)
                        .background(StitchOutlineVariant.copy(alpha = 0.3f))
                )
            } else {
                Spacer(Modifier.height(24.dp))
            }
        }
        
        Spacer(Modifier.width(16.dp))
        
        // Content Card
        Surface(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
                .clickable { onItineraryClick() },
            color = StitchSurfaceContainerLow,
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, StitchSurfaceVariant)
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                
                Spacer(Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        title, 
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), 
                        color = StitchPrimary,
                        maxLines = 1
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Visit this iconic landmark...", 
                        style = MaterialTheme.typography.bodyMedium, 
                        color = StitchOnSurfaceVariant,
                        maxLines = 1
                    )
                    
                    Spacer(Modifier.height(8.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Directions, null, tint = StitchSecondary, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "Get Directions", 
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), 
                            color = StitchSecondary
                        )
                    }
                }
            }
        }
    }
}
