package com.groupe10.visittanger.ui.home

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WeatherWidget(
    weatherState: WeatherUiState,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = weatherState != WeatherUiState.Hidden,
        enter = fadeIn() + expandVertically(),
        exit  = fadeOut() + shrinkVertically()
    ) {
        when (weatherState) {
            is WeatherUiState.Loading ->
                WeatherWidgetSkeleton(modifier)

            is WeatherUiState.Success ->
                WeatherWidgetContent(
                    weather   = weatherState.weather,
                    onDismiss = onDismiss,
                    modifier  = modifier
                )

            is WeatherUiState.Error ->
                WeatherWidgetError(
                    message  = weatherState.message,
                    onRetry  = onRetry,
                    modifier = modifier
                )

            WeatherUiState.Hidden -> {}
        }
    }
}
