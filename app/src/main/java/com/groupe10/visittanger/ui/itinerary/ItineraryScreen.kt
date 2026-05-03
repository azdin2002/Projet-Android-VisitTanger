package com.groupe10.visittanger.ui.itinerary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.ItineraryType
import com.groupe10.visittanger.ui.components.TangerTopBar
import com.groupe10.visittanger.ui.itinerary.detail.ItineraryDetailScreen
import com.groupe10.visittanger.ui.theme.TangerGreenLight

@Composable
fun ItineraryScreen(
    onItineraryClick: (String) -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: ItineraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isExpanded = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

    var selectedItineraryId by remember { mutableStateOf<String?>(null) }

    if (isExpanded) {
        Row(Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(0.4f).fillMaxHeight(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.itinerary_title),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                items(uiState.itineraries, key = { it.id }) { itin ->
                    val isSelected = selectedItineraryId == itin.id
                    ItineraryCard(
                        itinerary = itin,
                        onClick = { selectedItineraryId = itin.id },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }

            VerticalDivider(modifier = Modifier.fillMaxHeight().width(1.dp))

            Box(modifier = Modifier.weight(0.6f).fillMaxHeight()) {
                if (selectedItineraryId != null) {
                    ItineraryDetailScreen(
                        itineraryId = selectedItineraryId!!,
                        onBackClick = { selectedItineraryId = null },
                        onPlaceClick = onItineraryClick
                    )
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Route, null, Modifier.size(64.dp), TangerGreenLight)
                            Spacer(Modifier.height(16.dp))
                            Text("Sélectionnez un itinéraire", color = Color.Gray)
                        }
                    }
                }
            }
        }
    } else {
        ItineraryPhoneLayout(
            uiState = uiState,
            onItineraryClick = onItineraryClick,
            onTypeSelected = viewModel::onTypeSelected
        )
    }
}

@Composable
fun ItineraryPhoneLayout(
    uiState: ItineraryUiState,
    onItineraryClick: (String) -> Unit,
    onTypeSelected: (ItineraryType?) -> Unit
) {
    Scaffold(
        topBar = { TangerTopBar(title = stringResource(R.string.itinerary_title)) }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(stringResource(R.string.itinerary_explore), fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(stringResource(R.string.itinerary_subtitle), color = Color.Gray, fontSize = 14.sp)
                }
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = uiState.selectedType == null,
                            onClick = { onTypeSelected(null) },
                            label = { Text(stringResource(R.string.itinerary_all)) }
                        )
                    }
                    items(ItineraryType.values()) { type ->
                        FilterChip(
                            selected = uiState.selectedType == type,
                            onClick = { onTypeSelected(type) },
                            label = { Text("${type.emoji} ${type.labelFr}") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(type.color),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            if (uiState.itineraries.isNotEmpty() && uiState.selectedType == null) {
                item {
                    FeaturedItineraryCard(
                        itinerary = uiState.itineraries.first(),
                        onClick = { onItineraryClick(uiState.itineraries.first().id) }
                    )
                }
            }

            item {
                Text(
                    text = stringResource(R.string.itinerary_all_title),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
            }

            items(items = uiState.itineraries, key = { it.id }) { itinerary ->
                ItineraryCard(
                    itinerary = itinerary,
                    onClick = { onItineraryClick(itinerary.id) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}
