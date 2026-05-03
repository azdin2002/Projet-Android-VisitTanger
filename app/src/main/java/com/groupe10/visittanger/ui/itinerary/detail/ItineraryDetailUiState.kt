package com.groupe10.visittanger.ui.itinerary.detail

import com.groupe10.visittanger.domain.model.Itinerary

data class ItineraryDetailUiState(
    val itinerary: Itinerary? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val currentStopIndex: Int = 0
)
