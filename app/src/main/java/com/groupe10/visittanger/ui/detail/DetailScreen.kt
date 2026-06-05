package com.groupe10.visittanger.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Lightbulb
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
import androidx.compose.ui.text.font.FontStyle
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
            // Hero Section
            item {
                Box(modifier = Modifier.fillMaxWidth().height(500.dp)) {
                    val localImageRes = when {
                        place.name.contains("Kasbah de Tanger", ignoreCase = true) -> R.drawable.img_home_hero_kasbah
                        place.name.contains("Cap Spartel", ignoreCase = true) -> R.drawable.img_place_cap_spartel
                        place.name.contains("Grottes d Hercule", ignoreCase = true) -> R.drawable.img_place_hercules_caves
                        place.name.contains("Medina de Tanger", ignoreCase = true) -> R.drawable.img_place_medina
                        place.name.contains("Grand Socco", ignoreCase = true) -> R.drawable.img_place_grand_socco
                        place.name.contains("Plage Malabata", ignoreCase = true) -> R.drawable.img_place_malabata
                        place.name.contains("Stade Ibn Battouta", ignoreCase = true) -> R.drawable.img_place_stadium
                        place.name.contains("Plage de Tanger", ignoreCase = true) -> R.drawable.img_place_beach_city
                        place.name.contains("Souk de Tanger", ignoreCase = true) -> R.drawable.img_place_souk
                        place.name.contains("Restaurant El Korsan", ignoreCase = true) -> R.drawable.img_place_restaurant
                        place.name.contains("Parc Perdicaris", ignoreCase = true) -> R.drawable.img_place_perdicaris
                        place.name.contains("Musée de la Kasbah", ignoreCase = true) -> R.drawable.img_place_kasbah_museum
                        place.name.contains("Hafa", ignoreCase = true) -> R.drawable.img_place_cafe_hafa
                        else -> R.drawable.welcome_to_tangier_image_2
                    }

                    AsyncImage(
                        model = if (place.photos.isNotEmpty()) place.photos[uiState.selectedPhotoIndex] else localImageRes,
                        contentDescription = place.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, StitchBackground.copy(alpha = 0.95f)),
                                    startY = 800f
                                )
                            )
                    )
                    
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(24.dp)
                    ) {
                        Surface(color = StitchSecondary, shape = CircleShape) {
                            val categoryLabel = when (lang) {
                                "fr" -> place.category.labelFr
                                "ar" -> place.category.labelAr
                                else -> place.category.name
                            }
                            Text(
                                text = categoryLabel.uppercase(),
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = place.name,
                            style = MaterialTheme.typography.displayMedium.copy(color = StitchPrimary, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(18.dp))
                            Text(" ${place.rating}", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = StitchOnSurface)
                            Text(" (${place.reviewCount} reviews)", style = MaterialTheme.typography.bodySmall, color = StitchOnSurfaceVariant)
                        }
                    }
                }
            }
            
            val teaser = if (place.teaser[lang].isNullOrBlank()) place.teaser["en"] ?: "" else place.teaser[lang]!!
            val description = if (place.description[lang].isNullOrBlank()) place.description["en"] ?: "" else place.description[lang]!!
            val localTip = if (place.localTips[lang].isNullOrBlank()) place.localTips["en"] ?: "" else place.localTips[lang]!!

            item { 
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {
                    if (teaser.isNotBlank()) {
                        Text(
                            text = teaser,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = StitchPrimary,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                    
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = StitchOnSurfaceVariant,
                        lineHeight = 28.sp
                    )
                    
                    if (localTip.isNotBlank()) {
                        Spacer(Modifier.height(24.dp))
                        Surface(
                            color = StitchTertiaryContainer.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, StitchTertiary.copy(alpha = 0.2f))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(Icons.Outlined.Lightbulb, null, tint = StitchTertiary, modifier = Modifier.size(24.dp))
                                Spacer(Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "LOCAL TIP",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                                        color = StitchTertiary
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = localTip,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                                        color = StitchOnSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item { QuickFactsSection() }

            item { 
                if (place.photos.size > 1) {
                    WhatToSeeSection(photos = place.photos, onPhotoSelected = onPhotoSelected)
                }
            }
            
            item { 
                LocationSection(
                    place = place, 
                    onMapClick = { onMapClick(place.latitude, place.longitude) }
                ) 
            }
            
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                    thickness = 1.dp,
                    color = StitchOutlineVariant.copy(alpha = 0.3f)
                )
            }
            
            item { ReviewsSection(uiState.reviews, uiState.showAllReviews, onToggleReviews) }
            
            item {
                PlanYourVisitSection(onItineraryAdd = { /* Logic */ }, onDirections = openNavigation)
            }
        }

        TangerTopBar(
            title = if (isScrolled) place.name else "",
            onBackClick = onBackClick,
            showProfile = true,
            containerColor = topBarAlpha,
            actions = {
                IconButton(onClick = onFavoriteToggled) {
                    Icon(
                        imageVector = if (place.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (place.isFavorite) Color.Red else if (isScrolled) StitchPrimary else Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        )

        // Bottom Actions
        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).padding(24.dp).fillMaxWidth(),
            color = StitchSurfaceContainerLow.copy(alpha = 0.95f),
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 8.dp,
            border = BorderStroke(1.dp, StitchSurfaceVariant)
        ) {
            Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { onMapClick(place.latitude, place.longitude) },
                    modifier = Modifier.weight(1.1f).height(56.dp),
                    border = BorderStroke(1.2.dp, StitchPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = StitchPrimary),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Icon(Icons.Default.Map, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("MAP", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, maxLines = 1)
                }
                Button(
                    onClick = openNavigation,
                    modifier = Modifier.weight(1f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StitchPrimary),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Icon(Icons.Default.Navigation, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("GO", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, maxLines = 1)
                }
            }
        }
    }
}

@Composable
fun QuickFactsSection() {
    Row(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp).fillMaxWidth(),
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
        color = StitchSurfaceContainerLow,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, StitchSurfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, null, tint = StitchSecondary, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(12.dp))
            Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, color = StitchOnSurfaceVariant, letterSpacing = 0.5.sp)
            Text(value, style = MaterialTheme.typography.labelLarge.copy(fontSize = 15.sp), color = StitchPrimary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun WhatToSeeSection(photos: List<String>, onPhotoSelected: (Int) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 32.dp)) {
        Text(
            "Visual Gallery",
            style = MaterialTheme.typography.headlineSmall,
            color = StitchPrimary,
            modifier = Modifier.padding(horizontal = 24.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            "A glimpse into the soul of this historic landmark.",
            style = MaterialTheme.typography.bodyMedium,
            color = StitchOnSurfaceVariant,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        )
        Spacer(Modifier.height(20.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(photos.size) { index ->
                Card(
                    modifier = Modifier.width(280.dp).height(380.dp),
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
                    }
                }
            }
        }
    }
}

@Composable
fun PlanYourVisitSection(onItineraryAdd: () -> Unit, onDirections: () -> Unit) {
    Surface(
        modifier = Modifier.padding(24.dp).fillMaxWidth(),
        color = StitchPrimary,
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 8.dp
    ) {
        Box {
            Box(modifier = Modifier.matchParentSize().background(Brush.radialGradient(listOf(Color.White.copy(alpha = 0.1f), Color.Transparent))))
            Column(modifier = Modifier.padding(32.dp)) {
                Text(
                    "Plan Your Visit",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Ready to explore? Save this destination to your itinerary and start your journey through the Bride of the North.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f),
                    lineHeight = 26.sp
                )
                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = onItineraryAdd,
                    colors = ButtonDefaults.buttonColors(containerColor = StitchSecondary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                ) {
                    Icon(Icons.Default.AddCircle, null, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("ADD TO ITINERARY", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
