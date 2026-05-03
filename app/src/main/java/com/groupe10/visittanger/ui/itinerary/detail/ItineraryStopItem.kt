package com.groupe10.visittanger.ui.itinerary.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.domain.model.ItineraryStop
import com.groupe10.visittanger.ui.theme.TangerAmber
import com.groupe10.visittanger.ui.theme.TangerGreen
import com.groupe10.visittanger.ui.theme.TangerGreenDark
import com.groupe10.visittanger.ui.theme.TangerGreenLight

@Composable
fun ItineraryStopItem(
    stop: ItineraryStop,
    isLast: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Colonne timeline gauche
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(36.dp)
        ) {
            // Cercle numéroté
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) TangerGreen
                        else TangerGreenLight
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${stop.order}",
                    color = if (isSelected) Color.White
                    else TangerGreenDark,
                    fontWeight = FontWeight.Bold
                )
            }
            // Ligne verticale (sauf dernier)
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(80.dp)
                        .background(TangerGreenLight)
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        // Contenu étape
        Card(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (isLast) 16.dp else 12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected) TangerGreenLight.copy(alpha = 0.3f) 
                                else MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 2.dp else 1.dp)
        ) {
            Column(Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stop.arrivalTime,
                        color = TangerGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "(${stop.duration})",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                
                Spacer(Modifier.height(4.dp))
                
                Text(
                    text = stop.place.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = stop.place.address,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                
                if (stop.tips.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = TangerAmber,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = stop.tips,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}
