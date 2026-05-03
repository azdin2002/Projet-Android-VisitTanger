package com.groupe10.visittanger.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    onDeleteSwipe: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDeleteSwipe()
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE24B4A)),
                contentAlignment = Alignment.CenterEnd
            ) {
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
    ) {
        PlaceCard(
            place = place.copy(isFavorite = true),
            onPlaceClick = { onPlaceClick() },
            onFavoriteClick = { onDeleteSwipe() }
        )
    }
}
