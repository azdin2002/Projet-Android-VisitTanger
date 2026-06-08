package com.groupe10.visittanger.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.groupe10.visittanger.R
import com.groupe10.visittanger.domain.model.ItineraryType

@Composable
fun ItineraryType.toLocalizedLabel(): String = when (this) {
    ItineraryType.ONE_DAY -> stringResource(R.string.itinerary_type_one_day)
    ItineraryType.WEEKEND -> stringResource(R.string.itinerary_type_weekend)
    ItineraryType.HISTORICAL -> stringResource(R.string.itinerary_type_historical)
    ItineraryType.NATURE -> stringResource(R.string.itinerary_type_nature)
    ItineraryType.GASTRONOMY -> stringResource(R.string.itinerary_type_gastronomy)
    ItineraryType.CUSTOM -> stringResource(R.string.itinerary_type_custom)
}
