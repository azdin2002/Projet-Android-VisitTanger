package com.groupe10.visittanger.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.groupe10.visittanger.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.groupe10.visittanger.domain.model.Weather
import com.groupe10.visittanger.domain.model.toBackgroundColor

@Composable
fun WeatherWidgetContent(
    weather: Weather,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = weather.condition.toBackgroundColor()
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box {
            // Bouton fermer
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(R.string.cd_close),
                    tint = Color.White.copy(alpha = 0.8f)
                )
            }

            Column(Modifier.padding(16.dp)) {

                // Ligne 1: ville + date
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        weather.cityName,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Ligne 2: icône + température + description
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = weather.iconUrl,
                        contentDescription = weather.description,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            "${weather.temperature}°C",
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            weather.description,
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    // Min / Max
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            "↑ ${weather.tempMax}°",
                            color = Color.White,
                            fontSize = 13.sp
                        )
                        Text(
                            "↓ ${weather.tempMin}°",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.3f))
                Spacer(Modifier.height(12.dp))

                // Ligne 3: détails (humidité, vent, ressenti)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherDetail(
                        icon  = Icons.Default.WaterDrop,
                        label = "${weather.humidity}%",
                        sub   = stringResource(R.string.weather_humidity)
                    )
                    WeatherDetail(
                        icon  = Icons.Default.Air,
                        label = "${weather.windSpeed} m/s",
                        sub   = stringResource(R.string.weather_wind)
                    )
                    WeatherDetail(
                        icon  = Icons.Default.Thermostat,
                        label = "${weather.feelsLike}°C",
                        sub   = stringResource(R.string.weather_feels_like)
                    )
                }
            }
        }
    }
}

@Composable
private fun WeatherDetail(
    icon: ImageVector,
    label: String,
    sub: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null,
             tint = Color.White, modifier = Modifier.size(18.dp))
        Spacer(Modifier.height(2.dp))
        Text(label, color = Color.White,
             fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Text(sub, color = Color.White.copy(alpha = 0.7f),
             fontSize = 11.sp)
    }
}
