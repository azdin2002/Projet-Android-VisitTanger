package com.groupe10.visittanger.ui.profile

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.groupe10.visittanger.R
import com.groupe10.visittanger.ui.components.TangerTopBar
import com.groupe10.visittanger.ui.language.LanguageManager
import com.groupe10.visittanger.ui.language.LanguageSelectorDialog

private const val TAG_LOGOUT = "VisitTanger.Logout"

@Composable
fun ProfileScreen(
    onLogoutSuccess: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Redirection après logout via event SharedFlow
    LaunchedEffect(viewModel) {
        Log.d(TAG_LOGOUT, "ProfileScreen: start collecting navigationEvent")
        viewModel.navigationEvent.collect { destination ->
            Log.d(TAG_LOGOUT, "ProfileScreen: navigationEvent received=$destination")
            if (destination == "login") {
                Log.d(TAG_LOGOUT, "ProfileScreen: invoking onLogoutSuccess()")
                onLogoutSuccess()
            }
        }
    }

    // Recreate pour appliquer la nouvelle langue
    val initialLang = remember { uiState.currentLanguage }
    LaunchedEffect(uiState.currentLanguage) {
        if (uiState.currentLanguage != initialLang) {
            (context as? Activity)?.recreate()
        }
    }

    Scaffold(
        topBar = { TangerTopBar(title = stringResource(R.string.profile_title)) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // 1. AVATAR SECTION
            item { ProfileAvatarSection(uiState, viewModel) }

            // 2. STATS SECTION
            item { ProfileStatsSection(uiState) }

            // 3. SECTION PRÉFÉRENCES
            item {
                ProfileSectionTitle(
                    stringResource(R.string.profile_preferences)
                )
            }

            // Dark mode toggle
            item {
                ProfileToggleItem(
                    icon = Icons.Default.DarkMode,
                    title = stringResource(R.string.profile_dark_mode),
                    checked = uiState.isDarkMode,
                    onToggle = { viewModel.onDarkModeToggle() }
                )
            }

            // Langue
            item {
                ProfileMenuItem(
                    icon = Icons.Default.Language,
                    title = stringResource(R.string.profile_language),
                    subtitle = "${LanguageManager.getLanguageFlag(uiState.currentLanguage)} " +
                               LanguageManager.getLanguageNativeName(uiState.currentLanguage),
                    onClick = { viewModel.onShowLanguageDialog() }
                )
            }

            // 4. SECTION COMPTE
            item {
                ProfileSectionTitle(
                    stringResource(R.string.profile_account)
                )
            }

            item {
                ProfileMenuItem(
                    icon = Icons.Default.Edit,
                    title = stringResource(R.string.profile_edit_name),
                    subtitle = uiState.user?.displayName ?: "",
                    onClick = { viewModel.onEditNameClick() }
                )
            }

            item {
                ProfileMenuItem(
                    icon = Icons.Default.Email,
                    title = stringResource(R.string.profile_email),
                    subtitle = uiState.user?.email ?: "",
                    onClick = {}
                )
            }

            // 5. SECTION DANGER
            item { Spacer(Modifier.height(8.dp)) }

            item {
                ProfileMenuItem(
                    icon = Icons.Default.Logout,
                    title = stringResource(R.string.profile_logout),
                    subtitle = null,
                    iconTint = MaterialTheme.colorScheme.error,
                    titleColor = MaterialTheme.colorScheme.error,
                    onClick = { viewModel.onShowLogoutDialog() }
                )
            }

            // Version app
            item {
                Text(
                    text = stringResource(R.string.profile_version, "1.0.0"),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    // DIALOGS
    if (uiState.showLanguageDialog) {
        LanguageSelectorDialog(
            currentLang = uiState.currentLanguage,
            onLanguageSelected = { lang ->
                viewModel.onLanguageSelected(lang)
                viewModel.onDismissLanguageDialog()
            },
            onDismiss = { viewModel.onDismissLanguageDialog() }
        )
    }

    if (uiState.showLogoutDialog) {
        LogoutConfirmDialog(
            onConfirm = { viewModel.onLogoutConfirmed() },
            onDismiss = { viewModel.onDismissLogoutDialog() }
        )
    }

    if (uiState.isEditingName) {
        EditNameDialog(
            value = uiState.editNameValue,
            onValueChange = { viewModel.onEditNameChange(it) },
            onSave = { viewModel.onSaveName() },
            onDismiss = { viewModel.onCancelEdit() }
        )
    }
}
