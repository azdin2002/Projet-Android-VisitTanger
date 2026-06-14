package com.groupe10.visittanger.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.ui.components.*
import com.groupe10.visittanger.ui.language.LanguageViewModel
import com.groupe10.visittanger.ui.theme.*
import com.groupe10.visittanger.ui.theme.toLocalizedName

@Composable
fun HomeScreen(
    windowSizeClass: WindowSizeClass,
    onPlaceClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()
    val currentLang by languageViewModel.currentLanguage.collectAsStateWithLifecycle()
    val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TangerTopBar(
                title = stringResource(R.string.home_title),
                isTransparent = false,
                isDarkMode = isDarkMode,
                onToggleDarkMode = themeViewModel::toggleDarkMode,
                onProfileClick = onProfileClick
            )
        },
        containerColor = StitchBackground,
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            )
        ) {
            // Search Bar
            item {
                TangerSearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = viewModel::onSearchQueryChanged,
                    placeholder = stringResource(R.string.home_search_hint),
                    modifier = Modifier.padding(20.dp)
                )
            }

            // Weather Hero Section
            item {
                WeatherHeroSection(weatherState = weatherState)
            }

            // Explore by Category
            item {
                Column(modifier = Modifier.padding(bottom = 24.dp)) {
                    Text(
                        text = stringResource(R.string.home_categories),
                        style = MaterialTheme.typography.headlineSmall,
                        color = StitchOnSurface,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    )
                    HomeCategoriesRow(
                        selectedCategory = uiState.selectedCategory,
                        onCategorySelected = viewModel::onCategorySelected
                    )
                }
            }

            if (uiState.recommendedPlaces.isNotEmpty()) {
                item {
                    RecommendedPlacesSection(
                        places = uiState.recommendedPlaces,
                        onPlaceClick = onPlaceClick,
                        onFavoriteClick = viewModel::onFavoriteToggled,
                        lang = currentLang
                    )
                }
            }

            // Trending Spots
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.home_popular),
                        style = MaterialTheme.typography.headlineSmall,
                        color = StitchOnSurface
                    )
                    Text(
                        text = stringResource(R.string.home_see_all),
                        style = MaterialTheme.typography.labelLarge,
                        color = StitchPrimary,
                        modifier = Modifier.clickable { /* See All */ }
                    )
                }
            }

            items(uiState.filteredPlaces) { place ->
                PlaceCard(
                    place = place,
                    onPlaceClick = onPlaceClick,
                    onFavoriteClick = viewModel::onFavoriteToggled,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    lang = currentLang
                )
            }

            // Did You Know?
            item {
                DidYouKnowSection()
            }
        }
    }
}

@Composable
fun RecommendedPlacesSection(
    places: List<Place>,
    onPlaceClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    lang: String,
) {
    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Text(
            text = stringResource(R.string.recommended_for_you),
            style = MaterialTheme.typography.headlineSmall,
            color = StitchOnSurface,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(places) { place ->
                PlaceCard(
                    place = place,
                    onPlaceClick = onPlaceClick,
                    onFavoriteClick = onFavoriteClick,
                    modifier = Modifier.width(280.dp),
                    lang = lang
                )
            }
        }
    }
}

@Composable
fun HomeCategoriesRow(
    selectedCategory: Category?,
    onCategorySelected: (Category?) -> Unit
) {
    val categories = listOf(
        Triple(null, stringResource(R.string.category_all), Icons.Default.GridView),
        Triple(Category.HISTORY, Category.HISTORY.toLocalizedName(), Icons.Default.Castle),
        Triple(Category.NATURE, Category.NATURE.toLocalizedName(), Icons.Default.Park),
        Triple(Category.BEACH, Category.BEACH.toLocalizedName(), Icons.Default.BeachAccess),
        Triple(Category.SHOPPING, Category.SHOPPING.toLocalizedName(), Icons.Default.Storefront),
        Triple(Category.FOOD, Category.FOOD.toLocalizedName(), Icons.Default.Restaurant),
        Triple(Category.EVENTS, Category.EVENTS.toLocalizedName(), Icons.Default.ArtTrack),
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { item ->
            val (category, label, icon) = item
            val isSelected = (category == null && selectedCategory == null) || (category != null && selectedCategory == category)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(72.dp)
                    .clickable { onCategorySelected(category) }
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) StitchSecondaryContainer else StitchSurfaceContainerHighest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (isSelected) StitchOnSecondaryContainer else StitchPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = StitchOnSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun WeatherHeroSection(weatherState: WeatherUiState) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(350.dp)
            .clip(RoundedCornerShape(32.dp))
    ) {
        AsyncImage(
            model = R.drawable.img_home_hero_sunset,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 300f
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            when (weatherState) {
                is WeatherUiState.Success -> {
                    val weather = weatherState.weather
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Tanger-Medina",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${weather.temperature}°C",
                            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                text = weather.description.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                            Text(
                                text = "H: ${weather.tempMax}° L: ${weather.tempMin}°",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        WeatherHeroDetail(
                            icon = Icons.Default.WaterDrop,
                            label = "${weather.humidity}%",
                            sub = stringResource(R.string.weather_humidity)
                        )
                        WeatherHeroDetail(
                            icon = Icons.Default.Air,
                            label = "${weather.windSpeed} m/s",
                            sub = stringResource(R.string.weather_wind)
                        )
                        WeatherHeroDetail(
                            icon = Icons.Default.Thermostat,
                            label = "${weather.feelsLike}°C",
                            sub = stringResource(R.string.weather_feels_like)
                        )
                    }
                }
                is WeatherUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is WeatherUiState.Error -> {
                    Text(
                        text = stringResource(R.string.home_no_results), // Re-using a generic error string
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun WeatherHeroDetail(
    icon: ImageVector,
    label: String,
    sub: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color.White, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Column {
            Text(label, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(sub, color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun DidYouKnowSection() {
    Surface(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        color = StitchSurfaceContainer.copy(alpha = 0.5f),
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(1.dp, StitchSurfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(StitchPrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Lightbulb, null, tint = Color.White, modifier = Modifier.size(32.dp))
            }
            Column {
                Text(
                    text = stringResource(R.string.home_did_you_know_title),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = StitchPrimary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.home_did_you_know_body),
                    style = MaterialTheme.typography.bodyMedium,
                    color = StitchOnSurfaceVariant
                )
            }
        }
    }
}
