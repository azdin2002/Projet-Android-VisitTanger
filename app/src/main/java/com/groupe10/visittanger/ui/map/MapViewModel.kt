package com.groupe10.visittanger.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.usecase.GetPlacesUseCase
import com.groupe10.visittanger.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG_MAP = "VisitTanger.Map"

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getPlacesUseCase: GetPlacesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    /** Liste complète chargée une fois (mock / futur API) — filtres appliqués en mémoire. */
    private var allPlaces: List<Place> = emptyList()

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadPlaces()
    }

    fun loadPlaces() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val places = getPlacesUseCase()
                .catch { e ->
                    Log.e(TAG_MAP, "loadPlaces error", e)
                    _uiState.update { it.copy(error = e.message) }
                    emit(emptyList())
                }
                .first()
            allPlaces = places
            Log.d(TAG_MAP, "loadPlaces: cached ${places.size} places")
            applyPlacesFilter()
        }
    }

    private fun matchesSearch(place: Place, query: String): Boolean {
        if (query.isBlank()) return true
        if (place.name.contains(query, ignoreCase = true)) return true
        if (place.address.contains(query, ignoreCase = true)) return true
        return place.description.values.any { it.contains(query, ignoreCase = true) }
    }

    private fun applyPlacesFilter() {
        val state = _uiState.value
        val cat = state.selectedCategory
        val q = state.searchQuery.trim()
        var list = allPlaces
        if (cat != null) {
            list = list.filter { it.category == cat }
        }
        if (q.isNotBlank()) {
            list = list.filter { place -> matchesSearch(place, q) }
        }
        Log.d(
            TAG_MAP,
            "applyPlacesFilter: allPlaces=${allPlaces.size} category=$cat query='$q' -> ${list.size} places",
        )
        val selected = state.selectedPlace
        val selectedStillVisible = selected != null && list.any { it.id == selected.id }
        _uiState.update {
            it.copy(
                places = list,
                isLoading = false,
                error = null,
                selectedPlace = if (selectedStillVisible) selected else null,
            )
        }
    }

    fun onCategorySelected(category: Category?) {
        _uiState.update { it.copy(selectedCategory = category) }
        applyPlacesFilter()
    }

    fun onSearchQueryChanged(query: String) {
        Log.d(TAG_MAP, "onSearchQueryChanged: '$query' (cachedPlaces=${allPlaces.size})")
        _uiState.update { it.copy(searchQuery = query) }
        applyPlacesFilter()
    }

    fun onPlaceSelected(place: Place?) {
        _uiState.update { it.copy(selectedPlace = place) }
    }

    fun onFavoriteToggled(placeId: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(placeId)
        }
    }

    fun onUserLocationUpdated(latLng: LatLng) {
        _uiState.update { it.copy(userLocation = latLng) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
