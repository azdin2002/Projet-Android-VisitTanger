package com.groupe10.visittanger.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.usecase.GetPlaceByIdUseCase
import com.groupe10.visittanger.domain.usecase.GetPlacesByCategoryUseCase
import com.groupe10.visittanger.domain.usecase.GetPlacesUseCase
import com.groupe10.visittanger.domain.usecase.SearchPlacesUseCase
import com.groupe10.visittanger.domain.usecase.ToggleFavoriteWithRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlacesUseCase: GetPlacesUseCase,
    private val getPlacesByCategoryUseCase: GetPlacesByCategoryUseCase,
    private val searchPlacesUseCase: SearchPlacesUseCase,
    private val toggleFavoriteWithRepoUseCase: ToggleFavoriteWithRepoUseCase,
    private val getPlaceByIdUseCase: GetPlaceByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

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
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            places = places,
                            filteredPlaces = filterList(places, it.selectedCategory, it.searchQuery),
                            featuredPlaces = places.take(3)
                        )
                    }
                }
        }
    }

    fun onCategorySelected(category: Category?) {
        _uiState.update { it.copy(selectedCategory = category) }
        applyFilters()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            applyFilters()
        }
    }

    fun onFavoriteToggled(placeId: String) {
        viewModelScope.launch {
            val place = getPlaceByIdUseCase(placeId)
            place?.let { toggleFavoriteWithRepoUseCase(it) }
        }
    }

    private fun applyFilters() {
        val currentState = _uiState.value
        _uiState.update {
            it.copy(
                filteredPlaces = filterList(currentState.places, currentState.selectedCategory, currentState.searchQuery)
            )
        }
    }

    private fun filterList(places: List<Place>, category: Category?, query: String): List<Place> {
        return places.filter { place ->
            val matchesCategory = category == null || place.category == category
            val matchesSearch = query.isEmpty() || place.name.contains(query, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
