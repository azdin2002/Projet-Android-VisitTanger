package com.groupe10.visittanger.ui.itinerary.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.domain.model.ItineraryStop
import com.groupe10.visittanger.ui.theme.*

@Composable
fun ItineraryStopItem(
    stop: ItineraryStop,
    isLast: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .height(IntrinsicSize.Min)
    ) {
        // Vertical Timeline Column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(64.dp)
        ) {
            // Reduced spacer to center better with the top-aligned title of the card
            Spacer(Modifier.height(20.dp))

            // Time Info
            Text(
                text = stop.arrivalTime.split(" ")[0],
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = if (isSelected) StitchSecondary else StitchPrimary
            )
            
            Spacer(Modifier.height(8.dp))

            // Dot
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) StitchSecondary else StitchPrimary)
            )
            
            // Vertical Line
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(2.dp)
                        .background(StitchOutlineVariant.copy(alpha = 0.3f))
                )
            } else {
                Spacer(Modifier.height(24.dp))
            }
        }

        Spacer(Modifier.width(16.dp))

        // Content Card
        Surface(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
                .clickable { onClick() },
            color = if (isSelected) StitchSurfaceContainer else StitchSurfaceContainerLow,
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) StitchSecondary else StitchSurfaceVariant
            )
        ) {
            Column(Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stop.place.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = StitchPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    
                    Surface(
                        color = StitchSecondaryFixed,
                        shape = CircleShape
                    ) {
                        Text(
                            text = stop.duration,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = StitchSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(Modifier.height(8.dp))
                
                Text(
                    text = stop.place.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = StitchOnSurfaceVariant
                )
                
                if (stop.tips.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    Surface(
                        color = StitchTertiaryContainer.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = StitchTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = stop.tips,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = 18.sp
                                ),
                                color = StitchOnTertiaryFixedVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
