package com.groupe10.visittanger.ui.itinerary

import com.groupe10.visittanger.domain.model.Itinerary
import com.groupe10.visittanger.domain.model.ItineraryType

data class ItineraryUiState(
    val itineraries: List<Itinerary> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedType: ItineraryType? = null
)
