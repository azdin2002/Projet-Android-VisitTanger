package com.groupe10.visittanger.ui.itinerary.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe10.visittanger.domain.usecase.GetItineraryByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryDetailViewModel @Inject constructor(
    private val getItineraryByIdUseCase: GetItineraryByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itineraryId: String = savedStateHandle["itineraryId"] ?: ""

    private val _uiState = MutableStateFlow(ItineraryDetailUiState())
    val uiState: StateFlow<ItineraryDetailUiState> = _uiState.asStateFlow()

    init {
        loadItinerary()
    }

    fun loadItinerary() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val itinerary = getItineraryByIdUseCase(itineraryId)
            if (itinerary != null) {
                _uiState.update { it.copy(itinerary = itinerary, isLoading = false) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Itinéraire non trouvé") }
            }
        }
    }

    fun onStopSelected(index: Int) {
        _uiState.update { it.copy(currentStopIndex = index) }
    }

    fun nextStop() {
        val current = _uiState.value.currentStopIndex
        val total = _uiState.value.itinerary?.places?.size ?: 0
        if (current < total - 1) {
            _uiState.update { it.copy(currentStopIndex = current + 1) }
        }
    }

    fun previousStop() {
        val current = _uiState.value.currentStopIndex
        if (current > 0) {
            _uiState.update { it.copy(currentStopIndex = current - 1) }
        }
    }
}
