package com.groupe10.visittanger.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.ui.components.*
import com.groupe10.visittanger.ui.theme.TangerGreen

@Composable
fun HomeScreen(
    windowSizeClass: WindowSizeClass,
    onPlaceClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()

    val columns = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 1
        WindowWidthSizeClass.Medium -> 2
        WindowWidthSizeClass.Expanded -> 3
        else -> 1
    }

    if (columns == 1) {
        HomePhoneLayout(
            uiState = uiState,
            weatherState = weatherState,
            onPlaceClick = onPlaceClick,
            onCategorySelected = viewModel::onCategorySelected,
            onSearchChanged = viewModel::onSearchQueryChanged,
            onFavoriteToggled = viewModel::onFavoriteToggled,
            onRetry = viewModel::loadPlaces,
            onWeatherRetry = viewModel::loadWeather,
            onWeatherDismiss = viewModel::dismissWeather
        )
    } else {
        HomeTabletLayout(
            uiState = uiState,
            weatherState = weatherState,
            columns = columns,
            onPlaceClick = onPlaceClick,
            onCategorySelected = viewModel::onCategorySelected,
            onSearchChanged = viewModel::onSearchQueryChanged,
            onFavoriteToggled = viewModel::onFavoriteToggled,
            onRetry = viewModel::loadPlaces,
            onWeatherRetry = viewModel::loadWeather,
            onWeatherDismiss = viewModel::dismissWeather
        )
    }
}

@Composable
fun HomePhoneLayout(
    uiState: HomeUiState,
    weatherState: WeatherUiState,
    onPlaceClick: (String) -> Unit,
    onCategorySelected: (Category?) -> Unit,
    onSearchChanged: (String) -> Unit,
    onFavoriteToggled: (String) -> Unit,
    onRetry: () -> Unit,
    onWeatherRetry: () -> Unit,
    onWeatherDismiss: () -> Unit
) {
    Scaffold(
        topBar = {
            TangerTopBar(
                title = stringResource(R.string.app_name),
                actions = {
                    IconButton(onClick = { /* Action info ou profil */ }) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading && uiState.places.isEmpty()) {
            LoadingIndicator()
        } else if (uiState.error != null && uiState.places.isEmpty()) {
            ErrorView(
                message = uiState.error ?: stringResource(R.string.error_generic),
                onRetry = onRetry
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // Header Banner
                item { BannerHeader(TangerGreen) }

                // Weather Widget
                item {
                    WeatherWidget(
                        weatherState = weatherState,
                        onRetry = onWeatherRetry,
                        onDismiss = onWeatherDismiss
                    )
                }

                // Search Bar
                item {
                    TangerSearchBar(
                        query = uiState.searchQuery,
                        onQueryChange = onSearchChanged,
                        placeholder = stringResource(R.string.home_search_hint),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Categories Chips
                item {
                    CategoriesSection(
                        selectedCategory = uiState.selectedCategory,
                        onCategorySelected = onCategorySelected
                    )
                }

                // Featured Section
                if (uiState.searchQuery.isEmpty() && uiState.selectedCategory == null) {
                    item { SectionHeader(title = stringResource(R.string.home_popular), onSeeAllClick = {}) }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(uiState.featuredPlaces) { place ->
                                PlaceCard(
                                    place = place,
                                    onPlaceClick = onPlaceClick,
                                    onFavoriteClick = onFavoriteToggled,
                                    modifier = Modifier.width(280.dp)
                                )
                            }
                        }
                    }
                }

                item {
                    val title = if (uiState.searchQuery.isNotEmpty() || uiState.selectedCategory != null) {
                        stringResource(R.string.home_no_results)
                    } else {
                        stringResource(R.string.home_nearby)
                    }
                    SectionHeader(title = title, onSeeAllClick = {})
                }

                if (uiState.filteredPlaces.isEmpty()) {
                    item {
                        EmptyView(modifier = Modifier.fillMaxWidth().height(200.dp))
                    }
                } else {
                    items(uiState.filteredPlaces) { place ->
                        PlaceCard(
                            place = place,
                            onPlaceClick = onPlaceClick,
                            onFavoriteClick = onFavoriteToggled,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeTabletLayout(
    uiState: HomeUiState,
    weatherState: WeatherUiState,
    columns: Int,
    onPlaceClick: (String) -> Unit,
    onCategorySelected: (Category?) -> Unit,
    onSearchChanged: (String) -> Unit,
    onFavoriteToggled: (String) -> Unit,
    onRetry: () -> Unit,
    onWeatherRetry: () -> Unit,
    onWeatherDismiss: () -> Unit
) {
    Scaffold(
        topBar = {
            TangerTopBar(title = stringResource(R.string.app_name))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            WeatherWidget(
                weatherState = weatherState,
                onRetry = onWeatherRetry,
                onDismiss = onWeatherDismiss
            )

            TangerSearchBar(
                query = uiState.searchQuery,
                onQueryChange = onSearchChanged,
                placeholder = stringResource(R.string.home_search_hint),
                modifier = Modifier.padding(16.dp)
            )
            
            CategoriesSection(
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = onCategorySelected
            )

            if (uiState.isLoading && uiState.places.isEmpty()) {
                LoadingIndicator()
            } else if (uiState.error != null && uiState.places.isEmpty()) {
                ErrorView(
                    message = uiState.error ?: stringResource(R.string.error_generic),
                    onRetry = onRetry
                )
            } else if (uiState.filteredPlaces.isEmpty()) {
                EmptyView(modifier = Modifier.fillMaxSize())
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.filteredPlaces, key = { it.id }) { place ->
                        PlaceCard(
                            place = place,
                            onPlaceClick = onPlaceClick,
                            onFavoriteClick = onFavoriteToggled
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BannerHeader(backgroundColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(backgroundColor)
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(
                text = stringResource(R.string.home_title),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.home_subtitle),
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        }
        Icon(
            imageVector = Icons.Default.WbSunny,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.2f),
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.TopEnd)
                .offset(x = 20.dp, y = (-20).dp)
        )
    }
}

@Composable
fun CategoriesSection(
    selectedCategory: Category?,
    onCategorySelected: (Category?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
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
                isSelected = selectedCategory == category,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.home_see_all),
            color = TangerGreen,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onSeeAllClick() }
        )
    }
}
