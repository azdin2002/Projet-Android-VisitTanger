package com.groupe10.visittanger.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.ui.theme.StitchPrimary
import com.groupe10.visittanger.ui.theme.StitchSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TangerTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    showProfile: Boolean = true,
    isTransparent: Boolean = false,
    containerColor: Color = if (isTransparent) StitchSurface.copy(alpha = 0.8f) else StitchSurface,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        tonalElevation = if (isTransparent) 0.dp else 4.dp,
        shadowElevation = if (isTransparent) 0.dp else 4.dp
    ) {
        Box {
            if (isTransparent) {
                // Blur background layer - only blurs what's underneath
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
                    style = MaterialTheme.typography.headlineMedium,
                    color = StitchPrimary
                )
            },
            navigationIcon = {
                if (onBackClick != null) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = StitchPrimary
                        )
                    }
                } else {
                    IconButton(onClick = { /* Open Drawer or Menu */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = StitchPrimary
                        )
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
                            .border(2.dp, StitchPrimary.copy(alpha = 0.1f), CircleShape)
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
                containerColor = containerColor,
                scrolledContainerColor = containerColor,
                titleContentColor = StitchPrimary,
                navigationIconContentColor = StitchPrimary,
                actionIconContentColor = StitchPrimary
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
