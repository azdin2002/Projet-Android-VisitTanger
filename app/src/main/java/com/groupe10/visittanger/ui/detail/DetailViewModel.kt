package com.groupe10.visittanger.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe10.visittanger.data.mock.ReviewMockData
import com.groupe10.visittanger.domain.usecase.GetPlaceByIdUseCase
import com.groupe10.visittanger.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPlaceByIdUseCase: GetPlaceByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val placeId: String = savedStateHandle["placeId"] ?: ""

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadPlace()
    }

    fun loadPlace() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val place = getPlaceByIdUseCase(placeId)
                val reviews = ReviewMockData.getReviewsForPlace(placeId)
                _uiState.update {
                    it.copy(place = place, reviews = reviews, isLoading = false, error = null)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Une erreur est survenue")
                }
            }
        }
    }

    fun onFavoriteToggled() {
        viewModelScope.launch {
            _uiState.update { it.copy(isFavoriteToggling = true) }
            try {
                toggleFavoriteUseCase(placeId)
                _uiState.update { currentState ->
                    val updatedPlace = currentState.place?.copy(
                        isFavorite = !currentState.place.isFavorite
                    )
                    currentState.copy(
                        place = updatedPlace,
                        isFavoriteToggling = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isFavoriteToggling = false, error = e.message) }
            }
        }
    }

    fun onPhotoSelected(index: Int) {
        _uiState.update { it.copy(selectedPhotoIndex = index) }
    }

    fun toggleShowAllReviews() {
        _uiState.update { it.copy(showAllReviews = !it.showAllReviews) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
