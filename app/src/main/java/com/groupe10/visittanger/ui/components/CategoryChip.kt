package com.groupe10.visittanger.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.groupe10.visittanger.domain.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColors = getCategoryColors(category)
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) categoryColors.first else Color.Transparent,
        label = "backgroundColor"
    )
    
    val textColor by animateColorAsState(
        targetValue = if (isSelected) categoryColors.second else categoryColors.second.copy(alpha = 0.7f),
        label = "textColor"
    )

    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = category.labelFr,
                color = textColor
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.Transparent,
            selectedContainerColor = backgroundColor,
            labelColor = categoryColors.second,
            selectedLabelColor = textColor
        ),
        border = if (!isSelected) {
            FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = false,
                borderColor = categoryColors.second.copy(alpha = 0.3f)
            )
        } else null,
        modifier = modifier
    )
}

fun getCategoryColors(category: Category): Pair<Color, Color> {
    return when (category) {
        Category.HISTORY -> Color(0xFFE6F1FB) to Color(0xFF185FA5)
        Category.NATURE -> Color(0xFFEAF3DE) to Color(0xFF3B6D11)
        Category.FOOD -> Color(0xFFFAEEDA) to Color(0xFF854F0B)
        Category.BEACH -> Color(0xFFE1F5EE) to Color(0xFF0F6E56)
        Category.SHOPPING -> Color(0xFFEEEDFE) to Color(0xFF534AB7)
        Category.EVENTS -> Color(0xFFFAECE7) to Color(0xFF993C1D)
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryChipPreview() {
    CategoryChip(
        category = Category.HISTORY,
        isSelected = true,
        onClick = {}
    )
}
