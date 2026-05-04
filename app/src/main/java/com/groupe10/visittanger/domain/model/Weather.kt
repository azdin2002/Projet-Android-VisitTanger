package com.groupe10.visittanger.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.groupe10.visittanger.ui.theme.TangerGreen

data class Weather(
    val cityName: String,
    val temperature: Int,           // arrondi en °C
    val feelsLike: Int,
    val tempMin: Int,
    val tempMax: Int,
    val humidity: Int,
    val description: String,        // ex: "ciel dégagé"
    val icon: String,               // code icon OWM ex: "01d"
    val iconUrl: String,            // URL complète
    val windSpeed: Double,          // m/s
    val visibility: Int,            // mètres
    val condition: WeatherCondition
)

enum class WeatherCondition {
    CLEAR, CLOUDS, RAIN, DRIZZLE,
    THUNDERSTORM, SNOW, MIST, UNKNOWN;

    companion object {
        fun fromOwmMain(main: String): WeatherCondition =
            when (main.uppercase()) {
                "CLEAR"        -> CLEAR
                "CLOUDS"       -> CLOUDS
                "RAIN"         -> RAIN
                "DRIZZLE"      -> DRIZZLE
                "THUNDERSTORM" -> THUNDERSTORM
                "SNOW"         -> SNOW
                "MIST", "FOG",
                "HAZE", "SMOKE" -> MIST
                else            -> UNKNOWN
            }
    }
}

@Composable
fun WeatherCondition.toBackgroundColor(): Color = when (this) {
    WeatherCondition.CLEAR        -> Color(0xFF2196F3) // bleu ciel
    WeatherCondition.CLOUDS       -> Color(0xFF607D8B) // gris bleu
    WeatherCondition.RAIN         -> Color(0xFF455A64) // gris foncé
    WeatherCondition.DRIZZLE      -> Color(0xFF546E7A) // gris
    WeatherCondition.THUNDERSTORM -> Color(0xFF37474F) // très foncé
    WeatherCondition.SNOW         -> Color(0xFF90A4AE) // gris clair
    WeatherCondition.MIST         -> Color(0xFF78909C) // gris brumeux
    WeatherCondition.UNKNOWN      -> TangerGreen
}
