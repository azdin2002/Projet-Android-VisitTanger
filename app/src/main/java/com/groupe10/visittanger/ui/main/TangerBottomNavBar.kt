package com.groupe10.visittanger.ui.main

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.ui.navigation.Screen
import com.groupe10.visittanger.ui.theme.TangerGreen
import com.groupe10.visittanger.ui.theme.TangerGreenLight

@Composable
fun TangerBottomNavBar(
    navItems: List<NavItem>,
    currentRoute: String?,
    onNavClick: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = TangerGreen,
        tonalElevation = 8.dp
    ) {
        navItems.forEach { item ->
            val selected = currentRoute == item.screen.route
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selected) item.selectedIcon
                        else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label, fontSize = 10.sp) },
                selected = selected,
                onClick = { onNavClick(item.screen) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = TangerGreen,
                    selectedTextColor = TangerGreen,
                    indicatorColor = TangerGreenLight,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}
