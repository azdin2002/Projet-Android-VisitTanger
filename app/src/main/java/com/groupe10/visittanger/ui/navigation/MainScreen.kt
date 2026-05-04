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
import com.groupe10.visittanger.ui.theme.TangerGreen
import com.groupe10.visittanger.ui.theme.TangerGreenLight

private const val TAG_NAV = "VisitTanger.Nav"

/**
 * Racine des onglets = Home (pas [findStartDestination] du graphe, qui peut rester « login »
 * après connexion et faire sélectionner la mauvaise entrée au popUpTo).
 */
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
        NavItem(Screen.Home, Icons.Default.Home,
                Icons.Outlined.Home, "Accueil"),
        NavItem(Screen.Map, Icons.Default.Map,
                Icons.Outlined.Map, "Carte"),
        NavItem(Screen.Itinerary, Icons.Default.Route,
                Icons.Outlined.Route, "Itinéraires"),
        NavItem(Screen.Favorites, Icons.Default.Favorite,
                Icons.Outlined.FavoriteBorder, "Favoris"),
        NavItem(Screen.Profile, Icons.Default.Person,
                Icons.Outlined.Person, "Profil")
    )

    // Écrans qui cachent la nav
    val hideNavRoutes = listOf(
        Screen.Login.route,
        Screen.Register.route,
        Screen.Details.route, // Note: Screen.Details.route and not Screen.Detail.route
        Screen.ItineraryDetail.route
    )
    val showNav = currentRoute !in hideNavRoutes

    when {
        // EXPANDED → NavigationDrawer
        config.showNavigationDrawer -> {
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet(
                        modifier = Modifier.width(240.dp)
                    ) {
                        DrawerNavContent(
                            navItems = navItems,
                            currentRoute = currentRoute,
                            onNavClick = { screen ->
                                Log.d(TAG_NAV, "Drawer navigate -> ${screen.route} from=$currentRoute")
                                navController.navigateToTab(screen.route)
                            }
                        )
                    }
                }
            ) {
                AppNavGraph(
                    navController = navController,
                    windowSizeClass = windowSizeClass
                )
            }
        }

        // MEDIUM → NavigationRail
        config.showNavigationRail -> {
            Row(Modifier.fillMaxSize()) {
                if (showNav) {
                    NavigationRail(
                        containerColor = Color.White,
                        contentColor = TangerGreen
                    ) {
                        Spacer(Modifier.weight(1f))
                        navItems.forEach { item ->
                            val selected = currentRoute == item.screen.route
                            NavigationRailItem(
                                icon = {
                                    Icon(
                                        if (selected) item.selectedIcon
                                        else item.unselectedIcon,
                                        contentDescription = item.label
                                    )
                                },
                                label = { Text(item.label,
                                              fontSize = 10.sp) },
                                selected = selected,
                                onClick = {
                                    Log.d(TAG_NAV, "Rail navigate -> ${item.screen.route} from=$currentRoute")
                                    navController.navigateToTab(item.screen.route)
                                },
                                colors = NavigationRailItemDefaults
                                    .colors(
                                        selectedIconColor = TangerGreen,
                                        selectedTextColor = TangerGreen,
                                        indicatorColor = TangerGreenLight
                                    )
                            )
                        }
                        Spacer(Modifier.weight(1f))
                    }
                }
                AppNavGraph(
                    navController = navController,
                    windowSizeClass = windowSizeClass,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // COMPACT → BottomNavBar (comportement actuel)
        else -> {
            Scaffold(
                bottomBar = {
                    if (showNav) {
                        TangerBottomNavBar(
                            navItems = navItems,
                            currentRoute = currentRoute,
                            onNavClick = { screen ->
                                Log.d(TAG_NAV, "BottomNav navigate -> ${screen.route} from=$currentRoute")
                                navController.navigateToTab(screen.route)
                            }
                        )
                    }
                }
            ) { paddingValues ->
                AppNavGraph(
                    navController = navController,
                    windowSizeClass = windowSizeClass,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}
