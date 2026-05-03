package com.groupe10.visittanger.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.groupe10.visittanger.R
import com.groupe10.visittanger.ui.components.LoadingIndicator
import com.groupe10.visittanger.ui.components.TangerTopBar

@Composable
fun FavoritesScreen(
    onPlaceClick: (String) -> Unit,
    onExploreClick: () -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TangerTopBar(
                title = stringResource(id = R.string.favorites_title),
                actions = {
                    if (uiState.favoritesCount > 0) {
                        Text(
                            text = "${uiState.favoritesCount} ${stringResource(id = R.string.favorites_remove).lowercase()}",
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                uiState.isLoading -> LoadingIndicator()
                
                uiState.favorites.isEmpty() -> EmptyFavoritesView(
                    onExploreClick = onExploreClick
                )

                else -> LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = uiState.favorites,
                        key = { it.id }
                    ) { place ->
                        SwipeToDeleteFavoriteCard(
                            place = place,
                            onPlaceClick = { onPlaceClick(place.id) },
                            onDeleteSwipe = {
                                viewModel.onFavoriteToggled(place)
                            }
                        )
                    }
                }
            }
        }
    }
}
