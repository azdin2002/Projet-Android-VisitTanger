package com.groupe10.visittanger.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Map.route) { MapScreen() }
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
    }
}
