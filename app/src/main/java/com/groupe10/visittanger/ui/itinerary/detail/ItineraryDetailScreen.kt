package com.groupe10.visittanger.ui.itinerary.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.Itinerary
import com.groupe10.visittanger.domain.model.ItineraryStop
import com.groupe10.visittanger.domain.model.ItineraryType
import com.groupe10.visittanger.domain.model.localizedDescription
import com.groupe10.visittanger.domain.model.localizedDifficulty
import com.groupe10.visittanger.domain.model.localizedDuration
import com.groupe10.visittanger.domain.model.localizedName
import com.groupe10.visittanger.domain.model.localizedTips
import com.groupe10.visittanger.domain.model.localizedTitle
import com.groupe10.visittanger.ui.components.TangerTopBar
import com.groupe10.visittanger.ui.language.LanguageViewModel
import com.groupe10.visittanger.ui.theme.*

@Composable
fun ItineraryDetailScreen(
    itineraryId: String,
    onBackClick: () -> Unit,
    onPlaceClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    viewModel: ItineraryDetailViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentLang by languageViewModel.currentLanguage.collectAsStateWithLifecycle()
    val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()

    val scrollState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 400
        }
    }

    val topBarAlpha by animateColorAsState(
        targetValue = if (isScrolled) StitchSurface.copy(alpha = 0.9f) else Color.Transparent,
        label = "topBarColor"
    )

    val topContentColor by animateColorAsState(
        targetValue = if (isScrolled) MaterialTheme.colorScheme.primary else Color.White,
        label = "topContentColor"
    )

    val itinerary = uiState.itinerary
    val title = itinerary?.localizedTitle(currentLang) ?: ""

    Scaffold(
        topBar = {
            TangerTopBar(
                title = if (isScrolled) title else "",
                onBackClick = onBackClick,
                isDarkMode = isDarkMode,
                onToggleDarkMode = themeViewModel::toggleDarkMode,
                showProfile = true,
                onProfileClick = onProfileClick,
                containerColor = topBarAlpha,
                contentColor = topContentColor,
                isTransparent = !isScrolled
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
                state = scrollState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // 1. Header Image with Badge and Title Overlay
                item {
                    val coverPhotoModel = itinerary.coverPhoto.trim().takeIf { it.isNotEmpty() }

                    Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
                        AsyncImage(
                            model = coverPhotoModel,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            fallback = painterResource(R.drawable.img_welcome_bg),
                            error = painterResource(R.drawable.img_welcome_bg)
                        )
                        
                        // Gradient Overlay
                        Box(modifier = Modifier.fillMaxSize().background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        ))

                        // Badge & Title (Bottom Left)
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(24.dp)
                        ) {
                            // Badge Type
                            Surface(
                                color = StitchSecondary,
                                shape = CircleShape
                            ) {
                                Text(
                                    text = itinerary.type.toLocalizedLabel().uppercase(),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                )
                            }
                            
                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = title,
                                style = MaterialTheme.typography.displaySmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                // 2. Stats Card (4 Columns)
                item {
                    StatsCard(itinerary, currentLang)
                }

                // 3. Overview Section
                item {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = stringResource(R.string.itinerary_overview),
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = itinerary.localizedDescription(currentLang),
                            style = MaterialTheme.typography.bodyLarge,
                            color = StitchOnSurfaceVariant,
                            lineHeight = 24.sp
                        )
                    }
                }

                // 4. The Plan Section
                item {
                    Text(
                        text = stringResource(R.string.itinerary_the_plan),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    )
                }

                itemsIndexed(itinerary.places) { index, stop ->
                    TimelineStopItem(
                        stop = stop,
                        lang = currentLang,
                        isFirst = index == 0,
                        isLast = index == itinerary.places.size - 1,
                        isDarkMode = isDarkMode,
                        onPlaceClick = { onPlaceClick(stop.place.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun StatsCard(itinerary: Itinerary, lang: String) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .offset(y = (-20).dp)
            .fillMaxWidth(),
        color = StitchSurfaceContainerLow,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, StitchSurfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem(Icons.Default.Schedule, stringResource(R.string.itinerary_duration), itinerary.localizedDuration(lang))
            StatItem(Icons.Default.Route, stringResource(R.string.itinerary_distance), "${itinerary.totalDistanceKm} km")
            StatItem(Icons.Default.LocationOn, stringResource(R.string.itinerary_stops), "${itinerary.places.size}")
            StatItem(Icons.AutoMirrored.Filled.DirectionsWalk, stringResource(R.string.itinerary_level), itinerary.localizedDifficulty(lang))
        }
    }
}

@Composable
fun StatItem(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, tint = StitchSecondary, modifier = Modifier.size(24.dp))
        Spacer(Modifier.height(8.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = StitchOnSurfaceVariant)
        Text(value, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun TimelineStopItem(
    stop: ItineraryStop,
    lang: String,
    isFirst: Boolean,
    isLast: Boolean,
    isDarkMode: Boolean,
    onPlaceClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .height(IntrinsicSize.Min)
    ) {
        // Left: Arrival Time
        Column(
            modifier = Modifier.width(60.dp).padding(top = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = stop.arrivalTime,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = StitchSecondary,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp
                )
            )
        }

        Spacer(Modifier.width(16.dp))

        // Center: Timeline Dot and Line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(20.dp)
        ) {
            if (!isFirst) {
                Box(
                    modifier = Modifier
                        .width(2.5.dp)
                        .height(16.dp)
                        .background(StitchOutlineVariant.copy(alpha = 0.3f))
                )
            } else {
                Spacer(Modifier.height(16.dp))
            }
            
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .border(3.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), CircleShape)
                    .padding(3.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.5.dp)
                        .background(StitchOutlineVariant.copy(alpha = 0.3f))
                )
            } else {
                Spacer(Modifier.height(24.dp))
            }
        }

        Spacer(Modifier.width(16.dp))

        // Right: Content Card
        Column(modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPlaceClick() },
                color = StitchSurfaceContainerLow,
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, StitchSurfaceVariant)
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Column(modifier = Modifier.fillMaxWidth().padding(end = 64.dp)) {
                        Text(
                            text = stop.place.localizedName(lang),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, null, tint = StitchOutline, modifier = Modifier.size(12.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = stop.place.address,
                                style = MaterialTheme.typography.bodySmall,
                                color = StitchOnSurfaceVariant,
                                maxLines = 1
                            )
                        }
                    }

                    // Duration Badge (Top Right)
                    Surface(
                        modifier = Modifier.align(Alignment.TopEnd),
                        color = StitchSecondary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StitchSecondary.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = stop.localizedDuration(lang),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = StitchSecondary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Tip Box (if exists)
            val tips = stop.localizedTips(lang)
            if (tips.isNotBlank()) {
                Spacer(Modifier.height(12.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = StitchTertiaryContainer.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, StitchTertiary.copy(alpha = 0.15f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Lightbulb,
                            contentDescription = null,
                            tint = StitchTertiary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                text = stringResource(R.string.detail_local_tip).uppercase(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = StitchTertiary
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = tips,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = StitchOnSurfaceVariant,
                                    lineHeight = 18.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
