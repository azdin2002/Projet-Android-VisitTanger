package com.groupe10.visittanger.ui.navigation

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.groupe10.visittanger.ui.auth.AuthViewModel
import com.groupe10.visittanger.ui.auth.LoginScreen
import com.groupe10.visittanger.ui.auth.RegisterScreen
import com.groupe10.visittanger.ui.detail.DetailScreen
import com.groupe10.visittanger.ui.favorites.FavoritesScreen
import com.groupe10.visittanger.ui.home.HomeScreen
import com.groupe10.visittanger.ui.itinerary.ItineraryScreen
import com.groupe10.visittanger.ui.itinerary.detail.ItineraryDetailScreen
import com.groupe10.visittanger.ui.map.MapScreen
import com.groupe10.visittanger.ui.profile.ProfileScreen
import com.groupe10.visittanger.ui.welcome.WelcomeScreen

private const val TAG_LOGOUT = "VisitTanger.Logout"

@Composable
fun AppNavGraph(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    authViewModel: AuthViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val startDestination = if (authViewModel.isUserLoggedIn()) {
        Screen.Home.route
    } else {
        Screen.Welcome.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        composable(Screen.Home.route) { 
            HomeScreen(
                windowSizeClass = windowSizeClass,
                onPlaceClick = { placeId ->
                    navController.navigate(Screen.Details.createRoute(placeId))
                }
            ) 
        }
        composable(Screen.Map.route) { 
            MapScreen(onPlaceClick = { placeId ->
                navController.navigate(Screen.Details.createRoute(placeId))
            }) 
        }
        composable(Screen.Itinerary.route) {
            ItineraryScreen(
                windowSizeClass = windowSizeClass,
                onItineraryClick = { id ->
                    navController.navigate(Screen.ItineraryDetail.createRoute(id))
                }
            )
        }
        composable(
            route = Screen.ItineraryDetail.route,
            arguments = listOf(navArgument("itineraryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itineraryId = backStackEntry.arguments?.getString("itineraryId") ?: ""
            ItineraryDetailScreen(
                itineraryId = itineraryId,
                onBackClick = { navController.popBackStack() },
                onPlaceClick = { placeId ->
                    navController.navigate(Screen.Details.createRoute(placeId))
                }
            )
        }
        composable(Screen.Favorites.route) { 
            FavoritesScreen(
                onPlaceClick = { placeId ->
                    navController.navigate(Screen.Details.createRoute(placeId))
                },
                onExploreClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            ) 
        }
        composable(Screen.Profile.route) { 
            ProfileScreen(
                onLogoutSuccess = {
                    Log.d(TAG_LOGOUT, "onLogoutSuccess: reset AuthUiState + navigate to Login")
                    authViewModel.resetAuthUiState()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            ) 
        }
        composable(Screen.Login.route) { 
            LoginScreen(navController = navController, viewModel = authViewModel) 
        }
        composable(Screen.Register.route) { 
            RegisterScreen(navController = navController, viewModel = authViewModel) 
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId") ?: ""
            DetailScreen(
                placeId = placeId,
                windowSizeClass = windowSizeClass,
                onBackClick = { navController.popBackStack() },
                onMapClick = { lat, lng ->
                    navController.navigate(Screen.Map.route) {
                        popUpTo(Screen.Map.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
