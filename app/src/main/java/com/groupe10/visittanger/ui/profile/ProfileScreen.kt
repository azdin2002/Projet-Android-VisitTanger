package com.groupe10.visittanger.ui.profile

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.ui.components.TangerTopBar
import com.groupe10.visittanger.ui.language.LanguageManager
import com.groupe10.visittanger.ui.language.LanguageSelectorDialog
import com.groupe10.visittanger.ui.profile.EditNameDialog
import com.groupe10.visittanger.ui.theme.*

@Composable
fun ProfileScreen(
    onLogoutSuccess: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Redirection après logout
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { destination ->
            if (destination == "login") {
                onLogoutSuccess()
            }
        }
    }

    Scaffold(
        topBar = { TangerTopBar(title = "Tangier", showProfile = false) },
        containerColor = StitchBackground,
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 24.dp,
                bottom = padding.calculateBottomPadding() + 24.dp,
                start = 20.dp,
                end = 20.dp
            )
        ) {
            // Hero Profile Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(StitchPrimary, StitchSecondary)
                                    )
                                )
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .border(4.dp, StitchSurface, CircleShape)
                            ) {
                                AsyncImage(
                                    model = uiState.user?.photoUrl ?: R.drawable.img_user_placeholder,
                                    contentDescription = "Avatar",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Surface(
                            color = StitchTertiary,
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp).offset(x = (-4).dp, y = (-4).dp),
                            shadowElevation = 4.dp
                        ) {
                            Icon(
                                Icons.Default.Verified,
                                null,
                                tint = Color.White,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = uiState.user?.displayName ?: "Explorer",
                        style = MaterialTheme.typography.headlineLarge,
                        color = StitchOnSurface
                    )
                    Spacer(Modifier.height(8.dp))
                    Surface(
                        color = StitchSecondaryContainer.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, StitchSecondary.copy(alpha = 0.2f)),
                        shape = CircleShape
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Explore, null, tint = StitchSecondary, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Explorer Status", style = MaterialTheme.typography.labelMedium, color = StitchOnSecondaryContainer)
                        }
                    }
                }
            }

            // Quick Stats Grid
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatBox("24", "Places", Icons.Default.LocationOn, StitchPrimary, Modifier.weight(1f))
                    StatBox("112", "Reviews", Icons.Default.RateReview, StitchSecondary, Modifier.weight(1f))
                    StatBox("348", "Photos", Icons.Default.PhotoLibrary, StitchTertiary, Modifier.weight(1f))
                }
            }

            // Settings List
            item {
                Surface(
                    color = StitchSurfaceContainer,
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, StitchOutlineVariant.copy(alpha = 0.3f))
                ) {
                    Column {
                        ProfileActionItem(
                            icon = Icons.Default.Bookmark,
                            title = "My Saved Spots",
                            subtitle = "12 destinations stored",
                            iconBg = StitchPrimary.copy(alpha = 0.1f),
                            iconTint = StitchPrimary
                        )
                        HorizontalDivider(color = StitchOutlineVariant.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 24.dp))
                        ProfileActionItem(
                            icon = Icons.Default.Translate,
                            title = "Language",
                            subtitle = LanguageManager.getLanguageNativeName(uiState.currentLanguage),
                            iconBg = StitchSecondary.copy(alpha = 0.1f),
                            iconTint = StitchSecondary,
                            onClick = { viewModel.onShowLanguageDialog() }
                        )
                        HorizontalDivider(color = StitchOutlineVariant.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 24.dp))
                        ProfileActionItem(
                            icon = Icons.Default.HelpCenter,
                            title = "Help & Support",
                            subtitle = "FAQs and 24/7 assistance",
                            iconBg = StitchTertiary.copy(alpha = 0.1f),
                            iconTint = StitchTertiary
                        )
                        HorizontalDivider(color = StitchOutlineVariant.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 24.dp))
                        ProfileActionItem(
                            icon = Icons.Default.Logout,
                            title = "Logout",
                            subtitle = null,
                            iconBg = Color.Red.copy(alpha = 0.1f),
                            iconTint = Color.Red,
                            textColor = Color.Red,
                            onClick = { viewModel.onShowLogoutDialog() }
                        )
                    }
                }
            }

            // Decorative Zellige
            item {
                Box(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(StitchSurfaceVariant.copy(alpha = 0.4f))
                        .border(1.dp, StitchOutlineVariant.copy(alpha = 0.2f), RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                   Text("Explore with Soul • Tangier 2026", style = MaterialTheme.typography.labelSmall, color = StitchOnSurfaceVariant.copy(alpha = 0.6f))
                }
            }
            
            item { Spacer(modifier = Modifier.height(100.dp)) }
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

@Composable
fun StatBox(value: String, label: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = StitchSurfaceContainerLow,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, StitchOutlineVariant.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.height(12.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, color = StitchOnSurface, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelSmall, color = StitchOnSurfaceVariant)
        }
    }
}

@Composable
fun ProfileActionItem(
    icon: ImageVector,
    title: String,
    subtitle: String?,
    iconBg: Color,
    iconTint: Color,
    textColor: Color = StitchOnSurface,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = iconTint, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.labelLarge, color = textColor, fontWeight = FontWeight.Bold)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = StitchOnSurfaceVariant)
            }
        }
        Icon(Icons.Default.ChevronRight, null, tint = StitchOutline, modifier = Modifier.size(20.dp))
    }
}
