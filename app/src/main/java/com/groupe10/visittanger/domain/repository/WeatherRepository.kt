package com.groupe10.visittanger.domain.repository

import com.groupe10.visittanger.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository     {
    suspend fun getWeather(
        lat: Double,
        lon: Double,
        lang: String = "fr"
    ): Result<Weather>
}
