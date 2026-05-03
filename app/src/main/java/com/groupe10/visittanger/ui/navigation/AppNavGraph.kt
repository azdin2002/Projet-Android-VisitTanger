package com.groupe10.visittanger.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.groupe10.visittanger.ui.favorites.FavoritesScreen
import com.groupe10.visittanger.ui.home.HomeScreen
import com.groupe10.visittanger.ui.itinerary.ItineraryScreen
import com.groupe10.visittanger.ui.map.MapScreen
import com.groupe10.visittanger.ui.profile.ProfileScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val startDestination = if (authViewModel.isUserLoggedIn()) {
        Screen.Home.route
    } else {
        Screen.Home.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { 
            HomeScreen(onPlaceClick = { placeId ->
                navController.navigate(Screen.Details.createRoute(placeId))
            }) 
        }
        composable(Screen.Map.route) { 
            MapScreen(onPlaceClick = { placeId ->
                navController.navigate(Screen.Details.createRoute(placeId))
            }) 
        }
        composable(Screen.Itinerary.route) { ItineraryScreen() }
        composable(Screen.Favorites.route) { FavoritesScreen() }
        composable(Screen.Profile.route) { 
            ProfileScreen(navController = navController, viewModel = authViewModel) 
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
            val placeId = backStackEntry.arguments?.getString("placeId")
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Place Detail for $placeId (Coming Soon)")
            }
        }
    }
}
