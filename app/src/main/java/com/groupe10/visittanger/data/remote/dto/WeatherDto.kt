package com.groupe10.visittanger.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.groupe10.visittanger.domain.model.Weather
import com.groupe10.visittanger.domain.model.WeatherCondition

data class WeatherDto(
    @SerializedName("name") val cityName: String,
    @SerializedName("main") val main: MainDto,
    @SerializedName("weather") val weather: List<WeatherDescDto>,
    @SerializedName("wind") val wind: WindDto,
    @SerializedName("visibility") val visibility: Int?,
    @SerializedName("dt") val timestamp: Long
)

data class MainDto(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("pressure") val pressure: Int
)

data class WeatherDescDto(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class WindDto(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val deg: Int?
)

fun WeatherDto.toDomainModel(): Weather {
    val desc = weather.firstOrNull()
    return Weather(
        cityName    = cityName,
        temperature = main.temp.toInt(),
        feelsLike   = main.feelsLike.toInt(),
        tempMin     = main.tempMin.toInt(),
        tempMax     = main.tempMax.toInt(),
        humidity    = main.humidity,
        description = desc?.description?.replaceFirstChar {
            it.uppercase()
        } ?: "",
        icon        = desc?.icon ?: "01d",
        iconUrl     = "https://openweathermap.org/img/wn/"
                    + "${desc?.icon ?: "01d"}@2x.png",
        windSpeed   = wind.speed,
        visibility  = visibility ?: 10000,
        condition   = WeatherCondition.fromOwmMain(
            desc?.main ?: ""
        )
    )
}
