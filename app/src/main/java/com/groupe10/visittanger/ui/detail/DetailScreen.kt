package com.groupe10.visittanger.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.groupe10.visittanger.R
import com.groupe10.visittanger.ui.components.*
import com.groupe10.visittanger.ui.language.LanguageViewModel
import com.groupe10.visittanger.ui.theme.TangerCoral
import com.groupe10.visittanger.ui.theme.TangerGreen

@Composable
fun DetailScreen(
    placeId: String,
    onBackClick: () -> Unit,
    onMapClick: (Double, Double) -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()
    val context = LocalContext.current
    val lang by languageViewModel.currentLanguage.collectAsStateWithLifecycle()
    
    // Simple estimation of scroll offset
    val isScrolled by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 200
        }
    }

    val topBarColor by animateColorAsState(
        targetValue = if (isScrolled) TangerGreen else Color.Transparent,
        label = "topBarColor"
    )

    val openNavigation = {
        val place = uiState.place
        if (place != null) {
            val navigationUri = Uri.parse(
                "google.navigation:q=${place.latitude},${place.longitude}&mode=d"
            )
            val mapsIntent = Intent(Intent.ACTION_VIEW, navigationUri).apply {
                setPackage("com.google.android.apps.maps")
            }
            if (mapsIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapsIntent)
            } else {
                val browserUri = Uri.parse(
                    "https://www.google.com/maps/dir/?api=1" +
                    "&destination=${place.latitude},${place.longitude}" +
                    "&travelmode=driving"
                )
                context.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            LoadingIndicator()
        } else if (uiState.error != null) {
            ErrorView(
                message = uiState.error ?: stringResource(R.string.error_generic),
                onRetry = { viewModel.loadPlace() }
            )
        } else if (uiState.place == null) {
            EmptyView(
                message = stringResource(R.string.map_no_places)
            )
        } else {
            val place = uiState.place!!
            val localizedDescription = remember(lang, place) {
                place.description[lang] ?: place.description["fr"] ?: ""
            }
            
            LazyColumn(
                state = scrollState,
                modifier = Modifier.fillMaxSize()
            ) {
                // 1. GALERIE PHOTOS
                item {
                    PhotoGallerySection(
                        photos = place.photos,
                        selectedIndex = uiState.selectedPhotoIndex,
                        onPhotoSelected = viewModel::onPhotoSelected
                    )
                }

                // 2. INFOS PRINCIPALES
                item {
                    PlaceInfoSection(place = place)
                }

                // 3. DESCRIPTION
                item {
                    DescriptionSection(description = localizedDescription)
                }

                // 4. INFOS PRATIQUES
                item {
                    PracticalInfoSection(place = place)
                }

                // 5. LOCALISATION
                item {
                    LocationSection(
                        place = place,
                        onMapClick = { onMapClick(place.latitude, place.longitude) }
                    )
                }

                // 6. AVIS UTILISATEURS
                item {
                    ReviewsSection(
                        reviews = uiState.reviews,
                        showAll = uiState.showAllReviews,
                        onToggleShowAll = viewModel::toggleShowAllReviews
                    )
                }

                // Bottom spacing for buttons
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }

            // TOP BAR flottante
            TangerTopBar(
                title = if (isScrolled) place.name else "",
                onBackClick = onBackClick,
                containerColor = topBarColor,
                actions = {
                    IconButton(onClick = viewModel::onFavoriteToggled) {
                        Icon(
                            imageVector = if (place.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (place.isFavorite) stringResource(R.string.detail_remove_favorite) else stringResource(R.string.detail_add_favorite),
                            tint = if (place.isFavorite) TangerCoral else Color.White
                        )
                    }
                    IconButton(onClick = { /* Share logic */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )

            // BOUTONS BAS (fixed)
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { onMapClick(place.latitude, place.longitude) },
                        modifier = Modifier.weight(1f),
                        border = BorderStroke(1.dp, TangerGreen),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TangerGreen)
                    ) {
                        Icon(Icons.Default.Map, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.nav_map))
                    }

                    Button(
                        onClick = openNavigation,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = TangerGreen)
                    ) {
                        Icon(Icons.Default.Navigation, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.itinerary_start))
                    }
                }
            }
        }
    }
}
