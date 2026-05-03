package com.groupe10.visittanger.ui.profile

import com.groupe10.visittanger.domain.model.User

data class ProfileUiState(
    val user: User? = null,
    val favoritesCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEditingName: Boolean = false,
    val editNameValue: String = "",
    val isDarkMode: Boolean = false,
    val currentLanguage: String = "fr",
    val showLogoutDialog: Boolean = false,
    val showLanguageDialog: Boolean = false,
    val logoutSuccess: Boolean = false
)
