package com.groupe10.visittanger.ui.itinerary.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.model.localizedName
import com.groupe10.visittanger.domain.model.localizedTitle
import com.groupe10.visittanger.ui.components.TangerTopBar
import com.groupe10.visittanger.ui.language.LanguageViewModel
import com.groupe10.visittanger.ui.theme.*

@Composable
fun ItineraryDetailScreen(
    itineraryId: String,
    onBackClick: () -> Unit,
    onPlaceClick: (String) -> Unit,
    viewModel: ItineraryDetailViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentLang by languageViewModel.currentLanguage.collectAsStateWithLifecycle()
    val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()

    val itinerary = uiState.itinerary
    val title = itinerary?.localizedTitle(currentLang) ?: ""

    Scaffold(
        topBar = {
            TangerTopBar(
                title = title,
                onBackClick = onBackClick,
                isDarkMode = isDarkMode,
                onToggleDarkMode = themeViewModel::toggleDarkMode,
                showProfile = false
            )
        },
        containerColor = StitchBackground,
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = StitchPrimary)
            }
        } else if (itinerary != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding() + 32.dp
                )
            ) {
                // Header Image
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                        AsyncImage(
                            model = R.drawable.img_itinerary_header,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(modifier = Modifier.fillMaxSize().background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, StitchBackground.copy(alpha = 0.9f)),
                                startY = 400f
                            )
                        ))
                    }
                }

                // Info Section
                item {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            title,
                            style = MaterialTheme.typography.displaySmall,
                            color = StitchPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Schedule, null, tint = StitchSecondary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("4 hours • 3.2 km", style = MaterialTheme.typography.bodyMedium, color = StitchOnSurfaceVariant)
                        }
                    }
                }

                // Stops
                itemsIndexed(itinerary.places) { index, stop ->
                    StopItem(
                        index = index + 1,
                        place = stop.place,
                        lang = currentLang,
                        isLast = index == itinerary.places.size - 1,
                        onPlaceClick = { onPlaceClick(stop.place.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun StopItem(
    index: Int,
    place: Place,
    lang: String,
    isLast: Boolean,
    onPlaceClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .height(IntrinsicSize.Min)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(32.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(StitchPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text("$index", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(2.dp)
                        .background(StitchOutlineVariant.copy(alpha = 0.3f))
                )
            }
        }

        Spacer(Modifier.width(20.dp))

        Column(modifier = Modifier.padding(bottom = 32.dp).clickable { onPlaceClick() }) {
            Surface(
                color = StitchSurfaceContainerLow,
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, StitchSurfaceVariant)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    val localImageRes = when {
                        place.name.contains("Kasbah", ignoreCase = true) -> R.drawable.img_home_hero_kasbah
                        place.name.contains("Medina", ignoreCase = true) -> R.drawable.img_place_medina
                        place.name.contains("Socco", ignoreCase = true) -> R.drawable.img_place_grand_socco
                        else -> R.drawable.img_place_restaurant
                    }

                    AsyncImage(
                        model = if (place.photos.isNotEmpty()) place.photos.first() else localImageRes,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp).clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            place.localizedName(lang),
                            style = MaterialTheme.typography.titleMedium,
                            color = StitchPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(14.dp))
                            Text(" ${place.rating}", style = MaterialTheme.typography.labelSmall, color = StitchOnSurface)
                        }
                    }
                }
            }
            
            if (!isLast) {
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.DirectionsRun, null, tint = StitchSecondary, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        stringResource(R.string.itinerary_next_stop),
                        style = MaterialTheme.typography.labelMedium,
                        color = StitchSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
