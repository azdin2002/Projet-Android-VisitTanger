package com.groupe10.visittanger.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.groupe10.visittanger.ui.theme.TangerGreen

import androidx.compose.ui.res.stringResource
import com.groupe10.visittanger.R

sealed class BottomNavItem(val screen: Screen, val titleRes: Int, val icon: ImageVector) {
    object Home : BottomNavItem(Screen.Home, R.string.nav_home, Icons.Default.Home)
    object Map : BottomNavItem(Screen.Map, R.string.nav_map, Icons.Default.Map)
    object Itinerary : BottomNavItem(Screen.Itinerary, R.string.nav_itineraries, Icons.Default.Route)
    object Favorites : BottomNavItem(Screen.Favorites, R.string.nav_favorites, Icons.Default.Favorite)
    object Profile : BottomNavItem(Screen.Profile, R.string.nav_profile, Icons.Default.Person)
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Map,
        BottomNavItem.Itinerary,
        BottomNavItem.Favorites,
        BottomNavItem.Profile
    )

    val showBottomBar = items.any { it.screen.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    items.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true
                        val title = stringResource(item.titleRes)
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = title) },
                            label = { Text(title) },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = TangerGreen,
                                selectedTextColor = TangerGreen,
                                indicatorColor = TangerGreen.copy(alpha = 0.1f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
