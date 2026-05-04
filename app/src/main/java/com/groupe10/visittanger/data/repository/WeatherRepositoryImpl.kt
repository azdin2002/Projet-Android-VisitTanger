package com.groupe10.visittanger.data.repository

import com.groupe10.visittanger.BuildConfig
import com.groupe10.visittanger.data.remote.api.WeatherApiService
import com.groupe10.visittanger.data.remote.dto.toDomainModel
import com.groupe10.visittanger.domain.model.Weather
import com.groupe10.visittanger.domain.repository.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getWeather(
        lat: Double,
        lon: Double,
        lang: String
    ): Result<Weather> = try {
        val dto = weatherApiService.getCurrentWeather(
            lat    = lat,
            lon    = lon,
            apiKey = BuildConfig.WEATHER_API_KEY,
            units  = "metric",
            lang   = lang
        )
        Result.success(dto.toDomainModel())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
