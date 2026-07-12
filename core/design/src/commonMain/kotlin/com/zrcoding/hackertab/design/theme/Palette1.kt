package com.zrcoding.hackertab.design.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val P1Primary = Color(0xFFF4A02C)
private val P1PrimaryPressed = Color(0xFFD8861A)
private val P1OnPrimary = Color(0xFF1A1006)

private val P1Neutral0 = Color(0xFFFEFBF6)
private val P1Neutral50 = Color(0xFFFAF5EB)
private val P1Neutral100 = Color(0xFFF1E9D8)
private val P1Neutral200 = Color(0xFFE0D3B8)
private val P1Neutral300 = Color(0xFFCABB97)
private val P1Neutral400 = Color(0xFFA08F6A)
private val P1Neutral600 = Color(0xFF544830)
private val P1Neutral700 = Color(0xFF3D3424)
private val P1Neutral800 = Color(0xFF2A2418)
private val P1Neutral900 = Color(0xFF1A1610)
private val P1Neutral950 = Color(0xFF11100B)
private val P1DarkSurface = Color(0xFF1F1B14)
private val P1DarkSurfaceVariant = Color(0xFF2A2418)
private val P1DarkOnSurfaceMuted = Color(0xFFB5A688)

val Palette1LightColors: ColorScheme = lightColorScheme(
    primary = P1Primary,
    onPrimary = P1OnPrimary,
    primaryContainer = P1Primary,
    onPrimaryContainer = P1OnPrimary,
    secondary = P1Neutral700,
    onSecondary = P1Neutral50,
    secondaryContainer = P1Neutral100,
    onSecondaryContainer = P1Neutral900,
    tertiary = Info,
    onTertiary = P1Neutral950,
    background = P1Neutral50,
    onBackground = P1Neutral900,
    surface = P1Neutral0,
    onSurface = P1Neutral900,
    surfaceVariant = P1Neutral100,
    onSurfaceVariant = P1Neutral600,
    error = ErrorRed,
    onError = P1Neutral0,
    outline = P1Neutral200,
    outlineVariant = P1Neutral100,
    inverseSurface = P1Neutral900,
    inverseOnSurface = P1Neutral50,
    inversePrimary = P1Primary,
    scrim = P1Neutral950,
)

val Palette1DarkColors: ColorScheme = darkColorScheme(
    primary = P1Primary,
    onPrimary = P1OnPrimary,
    primaryContainer = P1PrimaryPressed,
    onPrimaryContainer = P1OnPrimary,
    secondary = P1Neutral300,
    onSecondary = P1Neutral950,
    secondaryContainer = P1Neutral800,
    onSecondaryContainer = P1Neutral50,
    tertiary = Info,
    onTertiary = P1Neutral950,
    background = P1Neutral950,
    onBackground = P1Neutral50,
    surface = P1DarkSurface,
    onSurface = P1Neutral50,
    surfaceVariant = P1DarkSurfaceVariant,
    onSurfaceVariant = P1DarkOnSurfaceMuted,
    error = ErrorRed,
    onError = P1Neutral0,
    outline = P1Neutral700,
    outlineVariant = P1DarkSurfaceVariant,
    inverseSurface = P1Neutral50,
    inverseOnSurface = P1Neutral900,
    inversePrimary = P1Primary,
    scrim = P1Neutral950,
)
