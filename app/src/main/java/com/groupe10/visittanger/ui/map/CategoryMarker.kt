package com.groupe10.visittanger.ui.map

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.ui.theme.TangerAmber
import com.groupe10.visittanger.ui.theme.TangerCoral
import com.groupe10.visittanger.ui.theme.TangerGreen

@Composable
fun CategoryMarker(
    place: Place,
    isSelected: Boolean
) {
    val size by animateDpAsState(targetValue = if (isSelected) 48.dp else 36.dp, label = "markerSize")
    val backgroundColor = when (place.category) {
        Category.HISTORY -> Color(0xFF795548)
        Category.NATURE -> TangerGreen
        Category.FOOD -> TangerAmber
        Category.SHOPPING -> TangerCoral
        Category.BEACH -> Color(0xFF2196F3)
        Category.EVENTS -> Color(0xFF9C27B0)
    }

    val icon = when (place.category) {
        Category.HISTORY -> Icons.Default.AccountBalance
        Category.NATURE -> Icons.Default.Park
        Category.FOOD -> Icons.Default.Restaurant
        Category.SHOPPING -> Icons.Default.ShoppingBag
        Category.BEACH -> Icons.Default.BeachAccess
        Category.EVENTS -> Icons.Default.Event
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(backgroundColor)
                .then(
                    if (isSelected) Modifier.border(2.dp, Color.White, CircleShape)
                    else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(size * 0.6f)
            )
        }
        // Petite pointe en bas
        Box(
            modifier = Modifier
                .offset(y = (-4).dp)
                .size(width = 12.dp, height = 8.dp)
                .background(backgroundColor, shape = TriangleShape)
        )
    }
}

// Simple triangle shape for the marker pointer
private val TriangleShape = object : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width / 2f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}
