package com.groupe10.visittanger.ui.favorites

import com.groupe10.visittanger.domain.model.Place

data class FavoritesUiState(
    val favorites: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val favoritesCount: Int = 0,
    val selectedPlace: Place? = null
)
