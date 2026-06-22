package com.groupe10.visittanger.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = StitchPrimaryDark,
    secondary = StitchSecondaryDark,
    tertiary = StitchTertiaryDark,
    background = StitchBackgroundDark,
    surface = StitchSurfaceDark,
    onPrimary = StitchOnPrimaryDark,
    onSecondary = StitchOnSecondaryDark,
    onTertiary = StitchOnTertiaryDark,
    onBackground = StitchOnSurfaceDark,
    onSurface = StitchOnSurfaceDark,
    surfaceVariant = StitchSurfaceVariantDark,
    onSurfaceVariant = StitchOnSurfaceVariantDark,
    outline = StitchOutlineDark,
    primaryContainer = StitchPrimaryContainerDark,
    onPrimaryContainer = StitchOnPrimaryContainerDark,
    secondaryContainer = StitchSecondaryContainerDark,
    onSecondaryContainer = StitchOnSecondaryContainerDark,
    tertiaryContainer = StitchTertiaryContainerDark,
    onTertiaryContainer = StitchOnTertiaryContainerDark,
)

private val LightColorScheme = lightColorScheme(
    primary = StitchPrimaryLight,
    secondary = StitchSecondaryLight,
    tertiary = StitchTertiaryLight,
    background = StitchBackgroundLight,
    surface = StitchSurfaceLight,
    onPrimary = StitchOnPrimaryLight,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = StitchOnSurfaceLight,
    onSurface = StitchOnSurfaceLight,
    surfaceVariant = StitchSurfaceVariantLight,
    onSurfaceVariant = StitchOnSurfaceVariantLight,
    outline = StitchOutlineLight,
    primaryContainer = StitchPrimaryContainerLight,
    onPrimaryContainer = StitchOnPrimaryContainerLight,
    secondaryContainer = StitchSecondaryContainerLight,
    onSecondaryContainer = StitchOnSecondaryContainerLight,
)

@Composable
fun VisitTangerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val stitchColors = if (darkTheme) {
        StitchColors(
            primary = StitchPrimaryDark,
            onPrimary = StitchOnPrimaryDark,
            secondary = StitchSecondaryDark,
            onSecondary = StitchOnSecondaryDark,
            tertiary = StitchTertiaryDark,
            onTertiary = StitchOnTertiaryDark,
            primaryContainer = StitchPrimaryContainerDark,
            onPrimaryContainer = StitchOnPrimaryContainerDark,
            secondaryContainer = StitchSecondaryContainerDark,
            onSecondaryContainer = StitchOnSecondaryContainerDark,
            tertiaryContainer = StitchTertiaryContainerDark,
            onTertiaryContainer = StitchOnTertiaryContainerDark,
            background = StitchBackgroundDark,
            onBackground = StitchOnSurfaceDark,
            surface = StitchSurfaceDark,
            onSurface = StitchOnSurfaceDark,
            surfaceVariant = StitchSurfaceVariantDark,
            onSurfaceVariant = StitchOnSurfaceVariantDark,
            surfaceContainerLowest = StitchSurfaceContainerLowestDark,
            surfaceContainerLow = StitchSurfaceContainerLowDark,
            surfaceContainer = StitchSurfaceContainerDark,
            surfaceContainerHigh = StitchSurfaceContainerHighDark,
            surfaceContainerHighest = StitchSurfaceContainerHighestDark,
            outline = StitchOutlineDark,
            outlineVariant = StitchOutlineVariantDark,
            surfaceDim = Color(0xFF041329),
            surfaceBright = Color(0xFF1B2A46)
        )
    } else {
        StitchColors(
            primary = StitchPrimaryLight,
            onPrimary = StitchOnPrimaryLight,
            secondary = StitchSecondaryLight,
            onSecondary = StitchOnSecondaryLight,
            tertiary = StitchTertiaryLight,
            onTertiary = StitchOnTertiaryLight,
            primaryContainer = StitchPrimaryContainerLight,
            onPrimaryContainer = StitchOnPrimaryContainerLight,
            secondaryContainer = StitchSecondaryContainerLight,
            onSecondaryContainer = StitchOnSecondaryContainerLight,
            tertiaryContainer = StitchTertiaryContainerLight,
            onTertiaryContainer = StitchOnTertiaryContainerLight,
            background = StitchBackgroundLight,
            onBackground = StitchOnSurfaceLight,
            surface = StitchSurfaceLight,
            onSurface = StitchOnSurfaceLight,
            surfaceVariant = StitchSurfaceVariantLight,
            onSurfaceVariant = StitchOnSurfaceVariantLight,
            surfaceContainerLowest = StitchSurfaceContainerLowestLight,
            surfaceContainerLow = StitchSurfaceContainerLowLight,
            surfaceContainer = StitchSurfaceContainerLight,
            surfaceContainerHigh = StitchSurfaceContainerHighLight,
            surfaceContainerHighest = StitchSurfaceContainerHighestLight,
            outline = StitchOutlineLight,
            outlineVariant = StitchOutlineVariantLight,
            surfaceDim = Color(0xFFDEDCB1),
            surfaceBright = Color(0xFFFEFCCF)
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            @Suppress("DEPRECATION")
            window.statusBarColor = colorScheme.primary.toArgb()
            // Light theme → icônes status bar foncées ; dark theme → icônes claires
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalStitchColors provides stitchColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
