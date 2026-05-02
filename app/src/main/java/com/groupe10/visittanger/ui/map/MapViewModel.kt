package com.groupe10.visittanger.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.usecase.GetPlacesByCategoryUseCase
import com.groupe10.visittanger.domain.usecase.GetPlacesUseCase
import com.groupe10.visittanger.domain.usecase.SearchPlacesUseCase
import com.groupe10.visittanger.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getPlacesUseCase: GetPlacesUseCase,
    private val getPlacesByCategoryUseCase: GetPlacesByCategoryUseCase,
    private val searchPlacesUseCase: SearchPlacesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadPlaces()
    }

    fun loadPlaces() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getPlacesUseCase()
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { places ->
                    _uiState.update { it.copy(isLoading = false, places = places) }
                }
        }
    }

    fun onCategorySelected(category: Category?) {
        _uiState.update { it.copy(selectedCategory = category, isLoading = true) }
        viewModelScope.launch {
            val flow = if (category == null) {
                getPlacesUseCase()
            } else {
                getPlacesByCategoryUseCase(category)
            }
            
            flow.catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { places ->
                    _uiState.update { it.copy(isLoading = false, places = places) }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query, isLoading = true) }
        viewModelScope.launch {
            searchPlacesUseCase(query)
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { places ->
                    _uiState.update { it.copy(isLoading = false, places = places) }
                }
        }
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
