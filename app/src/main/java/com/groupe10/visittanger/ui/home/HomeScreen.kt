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
import com.groupe10.visittanger.ui.components.*
import com.groupe10.visittanger.ui.language.LanguageViewModel
import com.groupe10.visittanger.ui.theme.*
import com.groupe10.visittanger.ui.theme.toLocalizedName

@Composable
fun HomeScreen(
    windowSizeClass: WindowSizeClass,
    onPlaceClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()
    val currentLang by languageViewModel.currentLanguage.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TangerTopBar(
                title = stringResource(R.string.home_title)
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

            // Featured Today
            item {
                FeaturedHeroSection(onExploreClick = { /* Navigate to featured */ })
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
fun HomeCategoriesRow(
    selectedCategory: Category?,
    onCategorySelected: (Category?) -> Unit
) {
    val categories = listOf(
        Triple(Category.BEACH, Category.BEACH.toLocalizedName(), Icons.Default.BeachAccess),
        Triple(Category.SHOPPING, Category.SHOPPING.toLocalizedName(), Icons.Default.Storefront),
        Triple(Category.HISTORY, Category.HISTORY.toLocalizedName(), Icons.Default.Castle),
        Triple(Category.FOOD, Category.FOOD.toLocalizedName(), Icons.Default.Restaurant),
        Triple(Category.EVENTS, Category.EVENTS.toLocalizedName(), Icons.Default.ArtTrack),
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { (category, label, icon) ->
            val isSelected = selectedCategory == category
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(72.dp)
                    .clickable { onCategorySelected(if (isSelected) null else category) }
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
fun FeaturedHeroSection(onExploreClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(550.dp)
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
                        colors = listOf(Color.Transparent, StitchPrimary.copy(alpha = 0.9f)),
                        startY = 600f
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(32.dp)
        ) {
            Surface(
                color = StitchSecondary,
                shape = CircleShape
            ) {
                Text(
                    text = stringResource(R.string.home_featured_badge),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.home_featured_title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 42.sp
                ),
                color = Color.White
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.home_featured_body),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f)
            )
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = onExploreClick,
                colors = ButtonDefaults.buttonColors(containerColor = StitchPrimary),
                shape = CircleShape,
                modifier = Modifier.height(56.dp),
                contentPadding = PaddingValues(horizontal = 32.dp)
            ) {
                Text(stringResource(R.string.home_explore_now), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(20.dp))
            }
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
