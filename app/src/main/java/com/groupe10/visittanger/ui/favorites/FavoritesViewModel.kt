package com.groupe10.visittanger.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.usecase.GetFavoritesUseCase
import com.groupe10.visittanger.domain.usecase.ToggleFavoriteWithRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteWithRepoUseCase: ToggleFavoriteWithRepoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getFavoritesUseCase()
                .catch { e ->
                    _uiState.update {
                        it.copy(error = e.message, isLoading = false)
                    }
                }
                .collect { favorites ->
                    _uiState.update {
                        it.copy(
                            favorites = favorites,
                            favoritesCount = favorites.size,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onFavoriteToggled(place: Place) {
        viewModelScope.launch {
            toggleFavoriteWithRepoUseCase(place)
        }
    }

    fun onPlaceSelected(place: Place?) {
        _uiState.update { it.copy(selectedPlace = place) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
