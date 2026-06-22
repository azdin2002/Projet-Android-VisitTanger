package com.groupe10.visittanger.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// --- Light Palette Constants ---
val StitchPrimaryLight = Color(0xFF003E7A)
val StitchOnPrimaryLight = Color(0xFFFFFFFF)
val StitchPrimaryContainerLight = Color(0xFF0055A4)
val StitchOnPrimaryContainerLight = Color(0xFFAFCCFF)
val StitchInversePrimaryLight = Color(0xFFA8C8FF)

val StitchSecondaryLight = Color(0xFF914C08)
val StitchOnSecondaryLight = Color(0xFFFFFFFF)
val StitchSecondaryContainerLight = Color(0xFFFEA45D)
val StitchOnSecondaryContainerLight = Color(0xFF733A00)

val StitchTertiaryLight = Color(0xFF004912)
val StitchOnTertiaryLight = Color(0xFFFFFFFF)
val StitchTertiaryContainerLight = Color(0xFF00641C)
val StitchOnTertiaryContainerLight = Color(0xFF7CE17E)

// --- Base Palette Colors (Dark - Designer Implementation) ---
val StitchPrimaryDark = Color(0xFFFFB781) // Designer Primary (Amber)
val StitchOnPrimaryDark = Color(0xFF4E2500)
val StitchPrimaryContainerDark = Color(0xFFE38E49)
val StitchOnPrimaryContainerDark = Color(0xFF2F1400)

val StitchSecondaryDark = Color(0xFFFFB4A3)
val StitchOnSecondaryDark = Color(0xFF571E11)
val StitchSecondaryContainerDark = Color(0xFF763627)
val StitchOnSecondaryContainerDark = Color(0xFFFFDCC4)

val StitchTertiaryDark = Color(0xFFD8E2FF)
val StitchOnTertiaryDark = Color(0xFF20304F)
val StitchTertiaryContainerDark = Color(0xFF92A2C7)
val StitchOnTertiaryContainerDark = Color(0xFF283857)

// Backgrounds & Surfaces (Light)
val StitchBackgroundLight = Color(0xFFFEFCCF)
val StitchSurfaceLight = Color(0xFFFEFCCF)
val StitchOnSurfaceLight = Color(0xFF1D1D03)
val StitchSurfaceVariantLight = Color(0xFFE6E5B9)
val StitchOnSurfaceVariantLight = Color(0xFF424751)

val StitchSurfaceContainerLowestLight = Color(0xFFFFFFFF)
val StitchSurfaceContainerLowLight = Color(0xFFF8F6C9)
val StitchSurfaceContainerLight = Color(0xFFF2F0C4)
val StitchSurfaceContainerHighLight = Color(0xFFECEABE)
val StitchSurfaceContainerHighestLight = Color(0xFFE6E5B9)

// Backgrounds & Surfaces (Dark - Designer)
val StitchBackgroundDark = Color(0xFF041329)
val StitchSurfaceDark = Color(0xFF041329)
val StitchOnSurfaceDark = Color(0xFFD6E3FF)
val StitchSurfaceVariantDark = Color(0xFF27354C)
val StitchOnSurfaceVariantDark = Color(0xFFA18D80)

val StitchSurfaceContainerLowestDark = Color(0xFF0D1C32)
val StitchSurfaceContainerLowDark = Color(0xFF0D1C32)
val StitchSurfaceContainerDark = Color(0xFF112036)
val StitchSurfaceContainerHighDark = Color(0xFF1B2A46)
val StitchSurfaceContainerHighestDark = Color(0xFF27354C)

val StitchOutlineLight = Color(0xFF727783)
val StitchOutlineDark = Color(0xFFA18D80)

val StitchOutlineVariantLight = Color(0xFFC2C6D3)
val StitchOutlineVariantDark = Color(0xFF534439)

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
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
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

// --- Dynamic Accessors ---
val StitchPrimary: Color @Composable get() = LocalStitchColors.current.primary
val StitchSecondary: Color @Composable get() = LocalStitchColors.current.secondary
val StitchTertiary: Color @Composable get() = LocalStitchColors.current.tertiary
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

val StitchSecondaryContainer: Color @Composable get() = LocalStitchColors.current.secondaryContainer
val StitchOnSecondaryContainer: Color @Composable get() = LocalStitchColors.current.onSecondaryContainer
val StitchPrimaryContainer: Color @Composable get() = LocalStitchColors.current.primaryContainer
val StitchOnPrimaryContainer: Color @Composable get() = LocalStitchColors.current.onPrimaryContainer
val StitchTertiaryContainer: Color @Composable get() = LocalStitchColors.current.tertiaryContainer
val StitchOnTertiaryContainer: Color @Composable get() = LocalStitchColors.current.onTertiaryContainer
