package com.groupe10.visittanger.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.domain.model.Category
import com.groupe10.visittanger.ui.theme.StitchPrimary
import com.groupe10.visittanger.ui.theme.StitchSecondary
import com.groupe10.visittanger.ui.theme.StitchTertiary
import com.groupe10.visittanger.ui.theme.toLocalizedName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColors = getCategoryColors(category)
    
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = category.toLocalizedName().uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = categoryColors.first,
            selectedLabelColor = Color.White,
            labelColor = categoryColors.second
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    )
}

fun getCategoryColors(category: Category): Pair<Color, Color> {
    return when (category) {
        Category.HISTORY -> StitchPrimary to StitchPrimary
        Category.NATURE -> StitchTertiary to StitchTertiary
        Category.FOOD -> StitchSecondary to StitchSecondary
        Category.BEACH -> Color(0xFF0F6E56) to Color(0xFF0F6E56)
        Category.SHOPPING -> Color(0xFF534AB7) to Color(0xFF534AB7)
        Category.EVENTS -> Color(0xFF993C1D) to Color(0xFF993C1D)
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
