package com.groupe10.visittanger.ui.itinerary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe10.visittanger.domain.model.ItineraryType
import com.groupe10.visittanger.domain.usecase.GetItinerariesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryViewModel @Inject constructor(
    private val getItinerariesUseCase: GetItinerariesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItineraryUiState())
    val uiState: StateFlow<ItineraryUiState> = _uiState.asStateFlow()

    private val allItineraries = MutableStateFlow<List<com.groupe10.visittanger.domain.model.Itinerary>>(emptyList())

    init {
        loadItineraries()
    }

    fun loadItineraries() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getItinerariesUseCase()
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { itineraries ->
                    allItineraries.value = itineraries
                    filterItineraries(itineraries, _uiState.value.selectedType)
                }
        }
    }

    fun onTypeSelected(type: ItineraryType?) {
        _uiState.update { it.copy(selectedType = type) }
        filterItineraries(allItineraries.value, type)
    }

    private fun filterItineraries(itineraries: List<com.groupe10.visittanger.domain.model.Itinerary>, type: ItineraryType?) {
        val filtered = if (type == null) {
            itineraries
        } else {
            itineraries.filter { it.type == type }
        }
        _uiState.update { it.copy(itineraries = filtered, isLoading = false) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
