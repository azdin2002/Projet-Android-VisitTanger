package com.groupe10.visittanger.ui.home

import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place

data class HomeUiState(
    val places: List<Place> = emptyList(),
    val filteredPlaces: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: Category? = null,
    val searchQuery: String = "",
    val featuredPlaces: List<Place> = emptyList(),
    val recommendedPlaces: List<Place> = emptyList()
)
