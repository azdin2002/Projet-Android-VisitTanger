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
            Log.d(TAG_MAP, "loadPlaces: starting collection")
            _uiState.update { it.copy(isLoading = true, error = null) }
            getPlacesUseCase()
                .catch { e ->
                    Log.e(TAG_MAP, "loadPlaces error", e)
                    _uiState.update { it.copy(error = e.message, isLoading = false) }
                }
                .collect { places ->
                    allPlaces = places
                    Log.d(TAG_MAP, "loadPlaces: collected ${places.size} places")
                    applyPlacesFilter()
                }
        }
    }

    private fun matchesSearch(place: Place, query: String): Boolean {
        if (query.isBlank()) return true
        val q = query.trim()
        // Recherche par nom (priorité)
        if (place.name.contains(q, ignoreCase = true)) return true
        // Recherche par adresse
        if (place.address.contains(q, ignoreCase = true)) return true
        // Recherche dans les descriptions (multi-langue)
        return place.description.values.any { it.contains(q, ignoreCase = true) }
    }

    private fun applyPlacesFilter() {
        val state = _uiState.value
        val cat = state.selectedCategory
        val q = state.searchQuery
        
        Log.d(TAG_MAP, "applyPlacesFilter: query='$q', category=$cat, total=${allPlaces.size}")
        
        var list = allPlaces
        if (cat != null) {
            list = list.filter { it.category == cat }
        }
        if (q.isNotBlank()) {
            list = list.filter { place -> matchesSearch(place, q) }
        }
        
        Log.d(TAG_MAP, "applyPlacesFilter: result=${list.size} places")
        
        val selected = state.selectedPlace
        val selectedStillVisible = selected != null && list.any { it.id == selected.id }
        
        _uiState.update {
            it.copy(
                places = list,
                isLoading = false,
                selectedPlace = if (selectedStillVisible) selected else null
            )
        }
    }

    fun onCategorySelected(category: Category?) {
        Log.d(TAG_MAP, "onCategorySelected: $category")
        _uiState.update { it.copy(selectedCategory = category) }
        applyPlacesFilter()
    }

    fun onSearchQueryChange(query: String) {
        Log.d(TAG_MAP, "onSearchQueryChange: '$query'")
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
