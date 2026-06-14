package com.groupe10.visittanger.ui.favorites

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.ui.components.PlaceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteFavoriteCard(
    place: Place,
    onPlaceClick: () -> Unit,
    onDeleteSwipe: () -> Unit,
    lang: String = "fr",
) {
    // Stable state for each item
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDeleteSwipe()
                true
            } else false
        },
        positionalThreshold = { totalDistance -> totalDistance * 0.5f }
    )

    // Ensure state is reset if the item ID changes (prevents state inheritance)
    LaunchedEffect(place.id) {
        if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    // Reset if it somehow gets stuck in a non-settled state but is still in the list
    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
            // Give time for potential removal, then snap back if still present
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    // React to state changes for icon alpha
    val isSettled by remember {
        derivedStateOf {
            dismissState.targetValue == SwipeToDismissBoxValue.Settled &&
                    dismissState.currentValue == SwipeToDismissBoxValue.Settled
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (!isSettled) 1f else 0f,
        label = "alpha"
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val color = Color(0xFFE24B4A).copy(alpha = alpha)
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = color,
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (alpha > 0.5f) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(end = 24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            tint = Color.White,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.favorites_remove),
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    ) {
        PlaceCard(
            place = place.copy(isFavorite = true),
            onPlaceClick = { onPlaceClick() },
            onFavoriteClick = { onDeleteSwipe() },
            lang = lang,
        )
    }
}
