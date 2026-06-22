package com.groupe10.visittanger.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.ui.theme.StitchPrimary
import com.groupe10.visittanger.ui.theme.StitchBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TangerTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    showProfile: Boolean = true,
    isTransparent: Boolean = false,
    isDarkMode: Boolean = false,
    onToggleDarkMode: (() -> Unit)? = null,
    onProfileClick: (() -> Unit)? = null,
    containerColor: Color? = null,
    contentColor: Color? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    // Par défaut, on utilise StitchBackground pour matcher le fond du Scaffold
    val finalContainerColor = containerColor ?: if (isTransparent) Color.Transparent else StitchBackground
    val finalContentColor = contentColor ?: MaterialTheme.colorScheme.primary
    
    Surface(
        color = finalContainerColor,
        tonalElevation = 0.dp, // Supprime la teinte automatique M3 qui casse les couleurs
        shadowElevation = if (isTransparent || finalContainerColor == Color.Transparent) 0.dp else 2.dp
    ) {
        Box {
            if (isTransparent && finalContainerColor != Color.Transparent) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .blur(20.dp)
                )
            }
            
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        ),
                        color = finalContentColor
                    )
                },
                navigationIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (onBackClick != null) {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = finalContentColor
                                )
                            }
                        }
                        
                        if (onToggleDarkMode != null) {
                            IconButton(onClick = onToggleDarkMode) {
                                Icon(
                                    imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = "Toggle Dark Mode",
                                    tint = finalContentColor
                                )
                            }
                        }
                    }
                },
                actions = {
                    actions()
                    
                    if (showProfile) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(2.dp, finalContentColor.copy(alpha = 0.1f), CircleShape)
                                .clickable(enabled = onProfileClick != null) { onProfileClick?.invoke() }
                        ) {
                            AsyncImage(
                                model = R.drawable.img_user_placeholder,
                                contentDescription = "Profile",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    titleContentColor = finalContentColor,
                    navigationIconContentColor = finalContentColor,
                    actionIconContentColor = finalContentColor
                ),
                windowInsets = TopAppBarDefaults.windowInsets
            )
        }
    }
}

@Preview
@Composable
fun TangerTopBarPreview() {
    TangerTopBar(title = "Tangier", onBackClick = {})
}
