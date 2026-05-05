package com.groupe10.visittanger.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.usecase.GetFavoritesUseCase
import com.groupe10.visittanger.domain.usecase.ToggleFavoriteWithRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteWithRepoUseCase: ToggleFavoriteWithRepoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    private fun firebaseAuthUidFlow() = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.uid.orEmpty())
        }
        firebaseAuth.addAuthStateListener(listener)
        trySend(firebaseAuth.currentUser?.uid.orEmpty())
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    init {
        viewModelScope.launch {
            firebaseAuthUidFlow()
                .flatMapLatest { uid ->
                    if (uid.isBlank()) {
                        flowOf(emptyList())
                    } else {
                        getFavoritesUseCase()
                    }
                }
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
                            isLoading = false,
                            error = null,
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
