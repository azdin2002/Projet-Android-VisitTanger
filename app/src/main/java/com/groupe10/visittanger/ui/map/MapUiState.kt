package com.groupe10.visittanger.ui.map

import com.google.android.gms.maps.model.LatLng
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place

data class MapUiState(
    val places: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedPlace: Place? = null,
    val selectedCategory: Category? = null,
    val searchQuery: String = "",
    val userLocation: LatLng? = null,
    val cameraPosition: LatLng = LatLng(35.7595, -5.8340)
)
