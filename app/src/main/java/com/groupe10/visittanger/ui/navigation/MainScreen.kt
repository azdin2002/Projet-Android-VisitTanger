package com.groupe10.visittanger.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.groupe10.visittanger.ui.adaptive.AdaptiveLayoutConfig
import com.groupe10.visittanger.ui.adaptive.DeviceType
import com.groupe10.visittanger.ui.main.DrawerNavContent
import com.groupe10.visittanger.ui.main.NavItem
import com.groupe10.visittanger.ui.main.TangerBottomNavBar
import com.groupe10.visittanger.ui.theme.*

private const val TAG_NAV = "VisitTanger.Nav"

private fun androidx.navigation.NavController.navigateToTab(route: String) {
    navigate(route) {
        popUpTo(Screen.Home.route) {
            saveState = true
            inclusive = false
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun MainScreen(
    windowSizeClass: WindowSizeClass
) {
    val deviceType = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> DeviceType.PHONE_PORTRAIT
        WindowWidthSizeClass.Medium -> DeviceType.PHONE_LANDSCAPE
        WindowWidthSizeClass.Expanded -> DeviceType.TABLET_LANDSCAPE
        else -> DeviceType.PHONE_PORTRAIT
    }
    val config = AdaptiveLayoutConfig.from(deviceType)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Destinations bottom nav
    val navItems = listOf(
        NavItem(Screen.Home, Icons.Default.Explore,
                Icons.Outlined.Explore, "Discover"),
        NavItem(Screen.Map, Icons.Default.Map,
                Icons.Outlined.Map, "Map"),
        NavItem(Screen.Itinerary, Icons.Default.Route,
                Icons.Outlined.Route, "Trips"),
        NavItem(Screen.Favorites, Icons.Default.Bookmark,
                Icons.Outlined.BookmarkBorder, "Saved"),
        NavItem(Screen.Profile, Icons.Default.Person,
                Icons.Outlined.Person, "Profile")
    )

    // Écrans qui cachent la nav
    val hideNavRoutes = listOf(
        Screen.Welcome.route,
        Screen.Login.route,
        Screen.Register.route,
        Screen.Details.route,
        Screen.ItineraryDetail.route,
    )
    val showNav = currentRoute !in hideNavRoutes

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                if (showNav) {
                    TangerBottomNavBar(
                        navItems = navItems,
                        currentRoute = currentRoute,
                        onNavClick = { screen ->
                            navController.navigateToTab(screen.route)
                        }
                    )
                }
            }
        ) { paddingValues ->
            AppNavGraph(
                navController = navController,
                windowSizeClass = windowSizeClass,
                // We pass paddingValues but let screens handle their own TopBars
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
            )
        }
    }
}
