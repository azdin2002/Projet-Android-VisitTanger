package com.groupe10.visittanger.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.R

@Composable
fun ProfileStatsSection(uiState: ProfileUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                value = uiState.favoritesCount.toString(),
                label = stringResource(R.string.profile_favorites_count),
                icon = Icons.Default.Favorite,
                iconColor = Color.Red
            )
            
            VerticalDivider(modifier = Modifier.height(40.dp))
            
            StatItem(
                value = "0",
                label = stringResource(R.string.profile_visited),
                icon = Icons.Default.Place,
                iconColor = Color(0xFF4CAF50)
            )
            
            VerticalDivider(modifier = Modifier.height(40.dp))
            
            StatItem(
                value = "0",
                label = stringResource(R.string.profile_itineraries_done),
                icon = Icons.Default.Directions,
                iconColor = Color(0xFF2196F3)
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}
