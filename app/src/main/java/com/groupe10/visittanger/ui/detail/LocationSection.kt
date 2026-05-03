package com.groupe10.visittanger.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.ui.theme.TangerGreen

@Composable
fun LocationSection(
    place: Place,
    onMapClick: () -> Unit
) {
    val context = LocalContext.current
    val placeLatLng = LatLng(place.latitude, place.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(placeLatLng, 15f)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Localisation",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            onClick = onMapClick,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    uiSettings = MapUiSettings(
                        scrollGesturesEnabled = false,
                        zoomGesturesEnabled = false,
                        rotationGesturesEnabled = false,
                        tiltGesturesEnabled = false,
                        zoomControlsEnabled = false,
                        myLocationButtonEnabled = false
                    ),
                    properties = MapProperties(
                        isMyLocationEnabled = false
                    )
                ) {
                    Marker(
                        state = MarkerState(position = placeLatLng),
                        title = place.name
                    )
                }
                
                // Overlay adresse
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(8.dp)
                ) {
                    Text(
                        text = place.address,
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        TextButton(
            onClick = {
                val navigationUri = Uri.parse(
                    "google.navigation:q=${place.latitude},${place.longitude}&mode=d"
                )
                val mapsIntent = Intent(Intent.ACTION_VIEW, navigationUri).apply {
                    setPackage("com.google.android.apps.maps")
                }
                if (mapsIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(mapsIntent)
                } else {
                    // Fallback: ouvrir dans navigateur
                    val browserUri = Uri.parse(
                        "https://www.google.com/maps/dir/?api=1" +
                        "&destination=${place.latitude},${place.longitude}" +
                        "&travelmode=driving"
                    )
                    context.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = TangerGreen
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ouvrir dans Google Maps",
                color = TangerGreen,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
