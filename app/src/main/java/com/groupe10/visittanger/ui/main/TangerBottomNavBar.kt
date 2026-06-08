package com.groupe10.visittanger.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.ui.navigation.Screen
import com.groupe10.visittanger.ui.theme.*

@Composable
fun TangerBottomNavBar(
    navItems: List<NavItem>,
    currentRoute: String?,
    onNavClick: (Screen) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        color = StitchSurfaceContainerLow.copy(alpha = 0.95f),
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val isSelected = currentRoute == item.screen.route
                
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .then(
                            if (isSelected) Modifier
                                .background(StitchTertiaryFixed)
                                .clickable { onNavClick(item.screen) }
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                            else Modifier
                                .clickable { onNavClick(item.screen) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label,
                            tint = if (isSelected) StitchOnTertiaryFixedVariant else StitchOnSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) StitchOnTertiaryFixedVariant else StitchOnSurfaceVariant,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
