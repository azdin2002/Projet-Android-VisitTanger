package com.groupe10.visittanger.ui.home

import com.groupe10.visittanger.domain.model.Weather

sealed class WeatherUiState {
    object Loading  : WeatherUiState()
    object Hidden   : WeatherUiState()
    data class Success(val weather: Weather) : WeatherUiState()
    data class Error(val message: String)    : WeatherUiState()
}
