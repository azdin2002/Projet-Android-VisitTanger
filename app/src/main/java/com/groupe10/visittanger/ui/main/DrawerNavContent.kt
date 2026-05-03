package com.groupe10.visittanger.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.groupe10.visittanger.ui.navigation.Screen
import com.groupe10.visittanger.ui.theme.TangerGreen
import com.groupe10.visittanger.ui.theme.TangerGreenLight

@Composable
fun DrawerNavContent(
    navItems: List<NavItem>,
    currentRoute: String?,
    onNavClick: (Screen) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 16.dp)
    ) {
        // Logo drawer
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Place,
                contentDescription = null,
                tint = TangerGreen,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                "Visit Tanger",
                style = MaterialTheme.typography.titleLarge,
                color = TangerGreen,
                fontWeight = FontWeight.Bold
            )
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(8.dp))

        navItems.forEach { item ->
            val selected = currentRoute == item.screen.route
            NavigationDrawerItem(
                icon = {
                    Icon(
                        if (selected) item.selectedIcon
                        else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = selected,
                onClick = { onNavClick(item.screen) },
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 2.dp
                ),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = TangerGreenLight,
                    selectedIconColor = TangerGreen,
                    selectedTextColor = TangerGreen,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}
