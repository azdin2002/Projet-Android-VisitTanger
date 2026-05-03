package com.groupe10.visittanger.ui.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groupe10.visittanger.ui.theme.TangerGreen

@Composable
fun ProfileSectionTitle(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = TangerGreen,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(
            start = 16.dp, top = 24.dp,
            bottom = 8.dp, end = 16.dp
        ),
        letterSpacing = 1.sp
    )
}
