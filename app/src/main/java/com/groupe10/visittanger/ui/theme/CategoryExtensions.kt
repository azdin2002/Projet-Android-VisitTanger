package com.groupe10.visittanger.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.Category

@Composable
fun Category.toLocalizedName(): String = when (this) {
    Category.HISTORY  -> stringResource(R.string.category_history)
    Category.NATURE   -> stringResource(R.string.category_nature)
    Category.FOOD     -> stringResource(R.string.category_food)
    Category.SHOPPING -> stringResource(R.string.category_shopping)
    Category.BEACH    -> stringResource(R.string.category_beach)
    Category.EVENTS   -> stringResource(R.string.category_events)
}
