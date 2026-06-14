package com.groupe10.visittanger.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// --- Base Palette Colors ---
val StitchPrimary = Color(0xFF003E7A)
val StitchOnPrimary = Color(0xFFFFFFFF)
val StitchPrimaryContainer = Color(0xFF0055A4)
val StitchOnPrimaryContainer = Color(0xFFAFCCFF)
val StitchInversePrimary = Color(0xFFA8C8FF)

val StitchSecondary = Color(0xFF914C08)
val StitchOnSecondary = Color(0xFFFFFFFF)
val StitchSecondaryContainer = Color(0xFFFEA45D)
val StitchOnSecondaryContainer = Color(0xFF733A00)

val StitchTertiary = Color(0xFF004912)
val StitchOnTertiary = Color(0xFFFFFFFF)
val StitchTertiaryContainer = Color(0xFF00641C)
val StitchOnTertiaryContainer = Color(0xFF7CE17E)

// Original Backgrounds
val StitchBackgroundLight = Color(0xFFFEFCCF)
val StitchBackgroundDark = Color(0xFF121212)

val StitchSurfaceLight = Color(0xFFFEFCCF)
val StitchSurfaceDark = Color(0xFF1E1E1E)

val StitchOnSurfaceLight = Color(0xFF1D1D03)
val StitchOnSurfaceDark = Color(0xFFE6E1E5)

val StitchSurfaceVariantLight = Color(0xFFE6E5B9)
val StitchSurfaceVariantDark = Color(0xFF49454F)

val StitchOnSurfaceVariantLight = Color(0xFF424751)
val StitchOnSurfaceVariantDark = Color(0xFFCAC4D0)

val StitchSurfaceContainerLowestLight = Color(0xFFFFFFFF)
val StitchSurfaceContainerLowestDark = Color(0xFF0F0F0F)

val StitchSurfaceContainerLowLight = Color(0xFFF8F6C9)
val StitchSurfaceContainerLowDark = Color(0xFF1C1B1F)

val StitchSurfaceContainerLight = Color(0xFFF2F0C4)
val StitchSurfaceContainerDark = Color(0xFF211F26)

val StitchSurfaceContainerHighLight = Color(0xFFECEABE)
val StitchSurfaceContainerHighDark = Color(0xFF2B2930)

val StitchSurfaceContainerHighestLight = Color(0xFFE6E5B9)
val StitchSurfaceContainerHighestDark = Color(0xFF333138)

val StitchOutlineLight = Color(0xFF727783)
val StitchOutlineDark = Color(0xFF938F99)

val StitchOutlineVariantLight = Color(0xFFC2C6D3)
val StitchOutlineVariantDark = Color(0xFF49454F)

val StitchError = Color(0xFFBA1A1A)
val StitchOnError = Color(0xFFFFFFFF)
val StitchErrorContainer = Color(0xFFFFDAD6)
val StitchOnErrorContainer = Color(0xFF93000A)

val StitchTertiaryFixed = Color(0xFF93F993)
val StitchOnTertiaryFixedVariant = Color(0xFF005316)
val StitchSecondaryFixed = Color(0xFFFFDCC4)
val StitchSecondaryFixedDim = Color(0xFFFFB781)
val StitchPrimaryFixed = Color(0xFFD5E3FF)

// Original Project Colors (Deprecated but kept for stability)
val TangerGreen = Color(0xFF009966)
val TangerGreenLight = Color(0xFFE1F5EE)
val TangerGreenDark = Color(0xFF006644)
val TangerAmber = Color(0xFFEF9F27)
val TangerCoral = Color(0xFFD85A30)

// --- Dynamic Color Provider ---
data class StitchColors(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val outline: Color,
    val outlineVariant: Color,
    val surfaceDim: Color,
    val surfaceBright: Color
)

val LocalStitchColors = staticCompositionLocalOf {
    StitchColors(
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

// --- Dynamic Accessors to maintain same names ---
val StitchBackground: Color @Composable get() = LocalStitchColors.current.background
val StitchOnBackground: Color @Composable get() = LocalStitchColors.current.onBackground
val StitchSurface: Color @Composable get() = LocalStitchColors.current.surface
val StitchOnSurface: Color @Composable get() = LocalStitchColors.current.onSurface
val StitchSurfaceVariant: Color @Composable get() = LocalStitchColors.current.surfaceVariant
val StitchOnSurfaceVariant: Color @Composable get() = LocalStitchColors.current.onSurfaceVariant
val StitchSurfaceContainerLowest: Color @Composable get() = LocalStitchColors.current.surfaceContainerLowest
val StitchSurfaceContainerLow: Color @Composable get() = LocalStitchColors.current.surfaceContainerLow
val StitchSurfaceContainer: Color @Composable get() = LocalStitchColors.current.surfaceContainer
val StitchSurfaceContainerHigh: Color @Composable get() = LocalStitchColors.current.surfaceContainerHigh
val StitchSurfaceContainerHighest: Color @Composable get() = LocalStitchColors.current.surfaceContainerHighest
val StitchOutline: Color @Composable get() = LocalStitchColors.current.outline
val StitchOutlineVariant: Color @Composable get() = LocalStitchColors.current.outlineVariant
val StitchSurfaceDim: Color @Composable get() = LocalStitchColors.current.surfaceDim
val StitchSurfaceBright: Color @Composable get() = LocalStitchColors.current.surfaceBright
