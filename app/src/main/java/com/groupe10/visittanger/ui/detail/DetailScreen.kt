package com.groupe10.visittanger.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.ui.components.*
import com.groupe10.visittanger.ui.language.LanguageViewModel
import com.groupe10.visittanger.ui.theme.*

@Composable
fun DetailScreen(
    placeId: String,
    onBackClick: () -> Unit,
    onMapClick: (Double, Double) -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: DetailViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lang by languageViewModel.currentLanguage.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        LoadingIndicator()
    } else if (uiState.error != null) {
        ErrorView(message = uiState.error ?: stringResource(R.string.error_generic), onRetry = { })
    } else if (uiState.place == null) {
        EmptyView(message = stringResource(R.string.map_no_places))
    } else {
        DetailPhoneLayout(
            uiState = uiState,
            onBackClick = onBackClick,
            onMapClick = onMapClick,
            onFavoriteToggled = viewModel::onFavoriteToggled,
            onPhotoSelected = viewModel::onPhotoSelected,
            onToggleReviews = viewModel::toggleShowAllReviews,
            lang = lang
        )
    }
}

@Composable
fun DetailPhoneLayout(
    uiState: DetailUiState,
    onBackClick: () -> Unit,
    onMapClick: (Double, Double) -> Unit,
    onFavoriteToggled: () -> Unit,
    onPhotoSelected: (Int) -> Unit,
    onToggleReviews: () -> Unit,
    lang: String
) {
    val scrollState = rememberLazyListState()
    val context = LocalContext.current
    val place = uiState.place!!
    
    val isScrolled by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 400
        }
    }

    val topBarAlpha by animateColorAsState(
        targetValue = if (isScrolled) StitchSurface.copy(alpha = 0.8f) else Color.Transparent,
        label = "topBarColor"
    )

    val openNavigation = {
        val navigationUri = Uri.parse("google.navigation:q=${place.latitude},${place.longitude}&mode=d")
        val mapsIntent = Intent(Intent.ACTION_VIEW, navigationUri).apply { setPackage("com.google.android.apps.maps") }
        if (mapsIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapsIntent)
        } else {
            val browserUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${place.latitude},${place.longitude}&travelmode=driving")
            context.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(StitchBackground)) {
        LazyColumn(
            state = scrollState, 
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            // Hero Section (Adaptive Parallax Style)
            item {
                Box(modifier = Modifier.fillMaxWidth().height(600.dp)) {
                    AsyncImage(
                        model = if (place.photos.isNotEmpty()) place.photos[uiState.selectedPhotoIndex] else R.drawable.welcome_to_tangier_image_2,
                        contentDescription = place.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, StitchBackground.copy(alpha = 0.9f)),
                                    startY = 1000f
                                )
                            )
                    )
                    
                    // Floating Info Card
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(20.dp)
                            .fillMaxWidth(),
                        color = Color.White.copy(alpha = 0.5f), // Glass card style
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                        shadowElevation = 0.dp
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    color = StitchSecondary,
                                    shape = CircleShape
                                ) {
                                    Text(
                                        text = place.category.labelFr.uppercase(),
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                                Spacer(Modifier.weight(1f))
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(18.dp))
                                Text(" ${place.rating}", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = StitchOnSurface)
                            }
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = place.name,
                                style = MaterialTheme.typography.displayMedium.copy(color = StitchPrimary, fontSize = 28.sp)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = StitchOnSurfaceVariant, modifier = Modifier.size(14.dp))
                                Text(" Tangier, Morocco", style = MaterialTheme.typography.bodySmall, color = StitchOnSurfaceVariant)
                            }
                        }
                    }
                }
            }
            
            val localizedDescription = place.description[lang] ?: place.description["fr"] ?: ""
            item { 
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 32.dp)) {
                    Text(
                        text = "The Bride of the North",
                        style = MaterialTheme.typography.headlineLarge,
                        color = StitchPrimary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = localizedDescription,
                        style = MaterialTheme.typography.bodyLarge,
                        color = StitchOnSurfaceVariant,
                        lineHeight = 28.sp
                    )
                }
            }

            // Quick Facts Bento Section
            item {
                QuickFactsSection()
            }

            item { 
                if (place.photos.size > 1) {
                    WhatToSeeSection(photos = place.photos, onPhotoSelected = onPhotoSelected)
                }
            }
            
            item { 
                LocationSection(place, onMapClick = { onMapClick(place.latitude, place.longitude) }) 
            }
            
            item { ReviewsSection(uiState.reviews, uiState.showAllReviews, onToggleReviews) }
            
            item {
                PlanYourVisitSection(onItineraryAdd = { /* Logic */ }, onDirections = openNavigation)
            }
        }

        // Top Bar - Handled carefully with window insets
        TangerTopBar(
            title = if (isScrolled) place.name else "",
            onBackClick = onBackClick,
            containerColor = topBarAlpha,
            actions = {
                IconButton(
                    onClick = onFavoriteToggled,
                    modifier = Modifier.clip(CircleShape).background(if (isScrolled) Color.Transparent else Color.Black.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = if (place.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (place.isFavorite) Color.Red else if (isScrolled) StitchPrimary else Color.White
                    )
                }
            }
        )

        // Bottom Actions floating bar
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
                .fillMaxWidth(),
            color = StitchSurfaceContainerLow.copy(alpha = 0.9f),
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 8.dp,
            border = BorderStroke(1.dp, StitchOutlineVariant.copy(alpha = 0.2f))
        ) {
            Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = { onMapClick(place.latitude, place.longitude) },
                    modifier = Modifier.weight(1f).height(52.dp),
                    border = BorderStroke(1.dp, StitchPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = StitchPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.nav_map), fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = openNavigation,
                    modifier = Modifier.weight(1f).height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StitchPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.itinerary_start), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun QuickFactsSection() {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FactCard("Sunset", "Best time", Icons.Default.WbTwilight, Modifier.weight(1f))
        FactCard("Free", "Entry Fee", Icons.Default.Payments, Modifier.weight(1f))
        FactCard("2-3h", "Duration", Icons.Default.Schedule, Modifier.weight(1f))
    }
}

@Composable
fun FactCard(value: String, label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = StitchSurfaceContainer,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, StitchOutlineVariant.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, null, tint = StitchSecondary, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(12.dp))
            Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, color = StitchOnSurfaceVariant)
            Text(value, style = MaterialTheme.typography.labelLarge, color = StitchPrimary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun WhatToSeeSection(photos: List<String>, onPhotoSelected: (Int) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 32.dp)) {
        Text(
            "What to See",
            style = MaterialTheme.typography.headlineMedium,
            color = StitchPrimary,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Text(
            "Don't miss these iconic spots within the Medina walls.",
            style = MaterialTheme.typography.bodyMedium,
            color = StitchOnSurfaceVariant,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )
        Spacer(Modifier.height(20.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(photos.size) { index ->
                Card(
                    modifier = Modifier.width(300.dp).height(400.dp),
                    shape = RoundedCornerShape(24.dp),
                    onClick = { onPhotoSelected(index) }
                ) {
                    Box {
                        AsyncImage(
                            model = photos[index],
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)), startY = 700f))
                        )
                        Text(
                            text = "Kasbah Museum",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.align(Alignment.BottomStart).padding(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlanYourVisitSection(onItineraryAdd: () -> Unit, onDirections: () -> Unit) {
    Surface(
        modifier = Modifier.padding(20.dp).fillMaxWidth(),
        color = StitchPrimary,
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 8.dp
    ) {
        Box {
            // Placeholder for Zellige pattern if we had an asset, or subtle gradient
            Box(modifier = Modifier.fillMaxSize().background(Brush.radialGradient(listOf(Color.White.copy(alpha = 0.05f), Color.Transparent))))
            Column(modifier = Modifier.padding(32.dp)) {
                Text(
                    "Plan Your Visit",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Ready to explore the mystical alleys of the Medina? Save this destination to your itinerary and get live directions.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(Modifier.height(32.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = onItineraryAdd,
                        colors = ButtonDefaults.buttonColors(containerColor = StitchSecondary),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f).height(56.dp)
                    ) {
                        Icon(Icons.Default.AddCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Add to Itinerary", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }
}
