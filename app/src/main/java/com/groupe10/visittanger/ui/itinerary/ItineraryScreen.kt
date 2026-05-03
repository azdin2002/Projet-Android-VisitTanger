package com.groupe10.visittanger.ui.itinerary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.groupe10.visittanger.domain.model.ItineraryType
import com.groupe10.visittanger.ui.components.TangerTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryScreen(
    onItineraryClick: (String) -> Unit,
    viewModel: ItineraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TangerTopBar(title = "Itinéraires") }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. HEADER
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Explorez Tanger",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Choisissez votre aventure",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            // 2. FILTRES TYPE (LazyRow chips)
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = uiState.selectedType == null,
                            onClick = { viewModel.onTypeSelected(null) },
                            label = { Text("Tous") }
                        )
                    }
                    items(ItineraryType.values()) { type ->
                        FilterChip(
                            selected = uiState.selectedType == type,
                            onClick = { viewModel.onTypeSelected(type) },
                            label = { Text("${type.emoji} ${type.labelFr}") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(type.color),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // 3. ITINÉRAIRE FEATURED (premier de la liste)
            if (uiState.itineraries.isNotEmpty() && uiState.selectedType == null) {
                item {
                    FeaturedItineraryCard(
                        itinerary = uiState.itineraries.first(),
                        onClick = {
                            onItineraryClick(uiState.itineraries.first().id)
                        }
                    )
                }
            }

            // 4. LISTE ITINÉRAIRES
            item {
                Text(
                    text = "Tous les itinéraires",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(
                        start = 16.dp, top = 16.dp, bottom = 8.dp
                    )
                )
            }

            items(
                items = uiState.itineraries,
                key = { it.id }
            ) { itinerary ->
                ItineraryCard(
                    itinerary = itinerary,
                    onClick = { onItineraryClick(itinerary.id) },
                    modifier = Modifier.padding(
                        horizontal = 16.dp, vertical = 6.dp
                    )
                )
            }

            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}
