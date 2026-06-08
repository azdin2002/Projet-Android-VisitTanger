package com.groupe10.visittanger.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe10.visittanger.data.datastore.UserPreferencesDataStore
import kotlinx.coroutines.flow.first
import com.groupe10.visittanger.domain.repository.UserRepository
import com.groupe10.visittanger.domain.repository.VisitedPlaceRepository
import com.groupe10.visittanger.domain.usecase.GetCurrentUserUseCase
import com.groupe10.visittanger.domain.usecase.LogoutUseCase
import com.groupe10.visittanger.domain.usecase.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG_LOGOUT = "VisitTanger.Logout"
private const val TAG_PROFILE = "VisitTanger.Profile"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val userRepository: UserRepository,
    private val visitedPlaceRepository: VisitedPlaceRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        observeUser()
        observePreferences()
    }

    private fun observeUser() {
        getCurrentUserUseCase()
            .flatMapLatest { user ->
                val uid = user?.uid.orEmpty()
                combine(
                    userRepository.getFavoritesCount(),
                    visitedPlaceRepository.getVisitedCount(uid),
                ) { favoritesCount, visitedCount ->
                    UserStats(
                        user = user,
                        favoritesCount = favoritesCount.coerceAtLeast(0),
                        visitedCount = visitedCount.coerceAtLeast(0),
                        itineraryCount = 0,
                    )
                }
            }
            .onEach { stats ->
                _uiState.update {
                    it.copy(
                        user = stats.user,
                        favoritesCount = stats.favoritesCount,
                        visitedCount = stats.visitedCount,
                        itinerariesCount = stats.itineraryCount,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observePreferences() {
        combine(
            userPreferencesDataStore.isDarkMode,
            userPreferencesDataStore.language
        ) { isDarkMode, language ->
            _uiState.update { it.copy(isDarkMode = isDarkMode, currentLanguage = language) }
        }.launchIn(viewModelScope)
    }

    fun onEditNameClick() {
        _uiState.update { 
            it.copy(
                isEditingName = true, 
                editNameValue = it.user?.displayName ?: ""
            ) 
        }
    }

    fun onEditNameChange(value: String) {
        _uiState.update { it.copy(editNameValue = value) }
    }

    fun onSaveName() {
        val newName = _uiState.value.editNameValue.trim()
        viewModelScope.launch {
            Log.d(TAG_PROFILE, "onSaveName: submit displayName=$newName")
            _uiState.update { it.copy(isLoading = true) }
            val result = updateProfileUseCase(name = newName)
            if (result.isSuccess) {
                _uiState.update { s ->
                    s.copy(
                        isEditingName = false,
                        isLoading = false,
                        user = s.user?.copy(displayName = newName),
                    )
                }
                Log.d(TAG_PROFILE, "onSaveName: success (UI + Firebase)")
            } else {
                Log.e(TAG_PROFILE, "onSaveName: failed", result.exceptionOrNull())
                _uiState.update { it.copy(error = result.exceptionOrNull()?.message, isLoading = false) }
            }
        }
    }

    fun onCancelEdit() {
        _uiState.update { it.copy(isEditingName = false) }
    }

    fun onDarkModeToggle() {
        viewModelScope.launch {
            val current = userPreferencesDataStore.isDarkMode.first()
            val next = !current
            Log.d(TAG_PROFILE, "onDarkModeToggle: DataStore current=$current -> next=$next")
            userPreferencesDataStore.setDarkMode(next)
        }
    }

    fun onLanguageSelected(lang: String, onSaved: () -> Unit = {}) {
        viewModelScope.launch {
            userPreferencesDataStore.setLanguage(lang)
            onSaved()
        }
    }

    fun onShowLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = true) }
    }

    fun onDismissLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = false) }
    }

    fun onShowLanguageDialog() {
        _uiState.update { it.copy(showLanguageDialog = true) }
    }

    fun onDismissLanguageDialog() {
        _uiState.update { it.copy(showLanguageDialog = false) }
    }

    fun onLogoutConfirmed() {
        viewModelScope.launch {
            Log.d(TAG_LOGOUT, "onLogoutConfirmed: calling logoutUseCase()")
            val result = logoutUseCase()
            Log.d(
                TAG_LOGOUT,
                "onLogoutConfirmed: logoutUseCase result isSuccess=${result.isSuccess} " +
                    "error=${result.exceptionOrNull()?.message}"
            )
            if (result.isSuccess) {
                _uiState.update { it.copy(showLogoutDialog = false) }
                Log.d(TAG_LOGOUT, "onLogoutConfirmed: emit navigationEvent login")
                _navigationEvent.emit("login")
            } else {
                _uiState.update { it.copy(error = result.exceptionOrNull()?.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

private data class UserStats(
    val user: com.groupe10.visittanger.domain.model.User?,
    val favoritesCount: Int,
    val visitedCount: Int,
    val itineraryCount: Int,
)
