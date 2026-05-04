package com.groupe10.visittanger.domain.usecase

import com.groupe10.visittanger.domain.model.Weather
import com.groupe10.visittanger.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(
        lat: Double = 35.7595,
        lon: Double = -5.8340,
        lang: String = "fr"
    ): Result<Weather> = repository.getWeather(lat, lon, lang)
}
