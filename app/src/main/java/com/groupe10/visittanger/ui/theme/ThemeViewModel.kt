package com.groupe10.visittanger.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupe10.visittanger.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : ViewModel() {

    val isDarkMode: StateFlow<Boolean> = userPreferencesDataStore.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun toggleDarkMode() {
        viewModelScope.launch {
            userPreferencesDataStore.setDarkMode(!isDarkMode.value)
        }
    }
}
