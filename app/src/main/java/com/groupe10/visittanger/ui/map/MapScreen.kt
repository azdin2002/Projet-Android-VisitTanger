package com.groupe10.visittanger.ui.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.ui.components.*
import com.groupe10.visittanger.ui.theme.TangerGreen
import kotlinx.coroutines.launch

@Composable
fun MapScreen(
    onPlaceClick: (String) -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    var hasLocationPermission by remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(uiState.cameraPosition, 13f)
    }

    LocationPermissionHandler(
        onPermissionGranted = { hasLocationPermission = true },
        onPermissionDenied = { hasLocationPermission = false }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. GOOGLE MAP
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = hasLocationPermission
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false
            ),
            onMapClick = {
                viewModel.onPlaceSelected(null)
            }
        ) {
            uiState.places.forEach { place ->
                MarkerComposable(
                    state = MarkerState(LatLng(place.latitude, place.longitude)),
                    onClick = {
                        viewModel.onPlaceSelected(place)
                        scope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLng(LatLng(place.latitude, place.longitude))
                            )
                        }
                        true
                    }
                ) {
                    CategoryMarker(
                        place = place,
                        isSelected = place == uiState.selectedPlace
                    )
                }
            }
        }

        // 2. TOP BAR Floating
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.8f))
                .padding(bottom = 8.dp)
        ) {
            TangerTopBar(title = stringResource(R.string.map_title))
            
            TangerSearchBar(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.onSearchQueryChanged(it) },
                placeholder = stringResource(R.string.home_search_hint),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = uiState.selectedCategory == null,
                        onClick = { viewModel.onCategorySelected(null) },
                        label = { Text(stringResource(R.string.category_all)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = TangerGreen,
                            selectedLabelColor = Color.White
                        )
                    )
                }
                items(Category.values()) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = uiState.selectedCategory == category,
                        onClick = { viewModel.onCategorySelected(category) }
                    )
                }
            }
        }

        // 3. BOTTOM SHEET
        AnimatedVisibility(
            visible = uiState.selectedPlace != null,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            uiState.selectedPlace?.let { place ->
                PlaceBottomSheet(
                    place = place,
                    onClose = { viewModel.onPlaceSelected(null) },
                    onDetailClick = { onPlaceClick(place.id) },
                    onFavoriteClick = { viewModel.onFavoriteToggled(it) }
                )
            }
        }

        // 4. FAB Ma position
        FloatingActionButton(
            onClick = {
                uiState.userLocation?.let { location ->
                    scope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(location, 15f)
                        )
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = if (uiState.selectedPlace != null) 220.dp else 32.dp, end = 16.dp),
            containerColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = null,
                tint = TangerGreen
            )
        }

        // 5. Loading overlay
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        }
        
        // Error handling
        uiState.error?.let { error ->
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                action = {
                    TextButton(onClick = { viewModel.clearError() }) {
                        Text("OK", color = Color.White)
                    }
                }
            ) {
                Text(error)
            }
        }
    }
}
