package com.groupe10.visittanger.ui.welcome

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.groupe10.visittanger.R
import com.groupe10.visittanger.ui.theme.*

@Composable
fun WelcomeScreen(
    onGetStartedClick: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 300, easing = LinearOutSlowInEasing),
        label = "alpha"
    )
    val translateYAnim by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 40.dp,
        animationSpec = tween(durationMillis = 1000, delayMillis = 300, easing = LinearOutSlowInEasing),
        label = "translateY"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Hero Background Image
        Image(
            painter = painterResource(id = R.drawable.welcome_to_tangier_image_2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Hero Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Black.copy(alpha = 0.4f),
                        0.5f to Color.Black.copy(alpha = 0.1f),
                        1f to Color.Black.copy(alpha = 0.8f)
                    )
                )
        )

        // Top Navigation Bar (Higher)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp), // Reduced vertical padding
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /* Menu Action */ }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(
                    text = "Tangier",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = (-0.5).sp
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
            ) {
                AsyncImage(
                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuC3KvkzvmStJfJUniNjS_LgDyt9pNXg46qzS8vxsBI4AN8d2-0UD41EDolt9hqqOmTveNAM9-smAiFTy-kSjQ9-trsZL2x1J1SC_Jnaz-rlEvt8N8VVu8FNVoPVbTm88y24IjzD7duqNJRntw6xc9V6n8NeOiIi1EaMLXomH8Puj292EWfAvMUi7clcGkBBCprvl58BNYLfeAhjgavL702Lya8JuhqsRqCLKWU7dVA7VA9LRz7g42NB-gbajtpj9QO27eh9kwzBXI0",
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Center Content Section
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align to BottomCenter instead of BottomStart
                .padding(horizontal = 20.dp)
                .padding(bottom = 120.dp)
                .alpha(alphaAnim)
                .offset(y = translateYAnim),
            horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
        ) {
            // Chip/Badge
            Surface(
                color = StitchTertiaryFixed,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text(
                    text = "THE BRIDE OF THE NORTH",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = StitchOnTertiaryFixedVariant,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 2.sp
                    )
                )
            }

            // Headline with adaptive sizing
            var fontSize by remember { mutableStateOf(44.sp) }
            Text(
                text = "Where the Mediterranean meets the Atlantic.",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = Color.White,
                    lineHeight = fontSize * 1.1f,
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize
                ),
                textAlign = TextAlign.Center,
                maxLines = 3,
                onTextLayout = { textLayoutResult ->
                    if (textLayoutResult.didOverflowHeight || textLayoutResult.lineCount > 3) {
                        fontSize = fontSize * 0.9f
                    }
                },
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()
            )

            // Actions (Stacked)
            Column(
                modifier = Modifier.fillMaxWidth(0.9f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onGetStartedClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = StitchPrimary,
                        contentColor = Color.White
                    ),
                    shape = CircleShape
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Explore Tangier",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                }

                // Plan Button with Glass effect
                Button(
                    onClick = onGetStartedClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.15f),
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Plan Your Visit",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }

        // Atmospheric Scroll Indicator
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .alpha(0.6f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val infiniteTransition = rememberInfiniteTransition(label = "bounce")
            val bounceAnim by infiniteTransition.animateValue(
                initialValue = 0.dp,
                targetValue = 8.dp,
                typeConverter = Dp.VectorConverter,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "bounce"
            )

            Text(
                text = "DISCOVER",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White,
                    letterSpacing = 2.sp
                )
            )
            Icon(
                imageVector = Icons.Default.KeyboardDoubleArrowDown,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .offset(y = bounceAnim)
            )
        }

        // Decorative Zellige Accent
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(4.dp)
                .alpha(0.3f)
                .background(
                    Brush.linearGradient(
                        colors = listOf(StitchSecondary, Color.Transparent),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(24f, 24f)
                    )
                )
        )
    }
}
