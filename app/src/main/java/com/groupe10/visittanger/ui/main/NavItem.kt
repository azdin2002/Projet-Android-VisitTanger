package com.groupe10.visittanger.ui.main

import androidx.compose.ui.graphics.vector.ImageVector
import com.groupe10.visittanger.ui.navigation.Screen

data class NavItem(
    val screen: Screen,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String
)
