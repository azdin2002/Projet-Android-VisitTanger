package com.groupe10.visittanger.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.ui.theme.StitchPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TangerTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    containerColor: Color = StitchPrimary,
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 20.sp),
                color = Color.White
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour",
                        tint = Color.White
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = containerColor
        )
    )
}

@Preview
@Composable
fun TangerTopBarPreview() {
    TangerTopBar(title = "Visit Tanger", onBackClick = {})
}
