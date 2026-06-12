package com.groupe10.visittanger.ui.favorites

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
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
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDeleteSwipe()
                true
            } else false
        }
    )

    // Using derivedStateOf to ensure we react to state changes correctly
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

    Log.d("SwipeDebug", "Place=${place.name} current=${dismissState.currentValue} target=${dismissState.targetValue} alpha=$alpha")

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
