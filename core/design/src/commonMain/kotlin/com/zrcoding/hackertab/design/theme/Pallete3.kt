package com.zrcoding.hackertab.design.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val P3Primary = Color(0xFFA78BFA)
private val P3PrimaryPressed = Color(0xFF8B6FE5)
private val P3OnPrimary = Color(0xFF15071A)

private val P3Neutral0 = Color(0xFFFCFBFE)
private val P3Neutral50 = Color(0xFFF6F4FB)
private val P3Neutral100 = Color(0xFFEAE5F4)
private val P3Neutral200 = Color(0xFFD5CDE8)
private val P3Neutral300 = Color(0xFFB0A4CC)
private val P3Neutral400 = Color(0xFF7E739A)
private val P3Neutral600 = Color(0xFF4D4366)
private val P3Neutral700 = Color(0xFF332B45)
private val P3Neutral800 = Color(0xFF221C30)
private val P3Neutral900 = Color(0xFF171221)
private val P3Neutral950 = Color(0xFF0E0B16)
private val P3DarkSurface = Color(0xFF1B162A)
private val P3DarkSurfaceVariant = Color(0xFF221C30)
private val P3DarkOnSurfaceMuted = Color(0xFFA99CC2)

val Pallete3LightColors: ColorScheme = lightColorScheme(
    primary = P3Primary,
    onPrimary = P3OnPrimary,
    primaryContainer = P3Primary,
    onPrimaryContainer = P3OnPrimary,
    secondary = P3Neutral700,
    onSecondary = P3Neutral50,
    secondaryContainer = P3Neutral100,
    onSecondaryContainer = P3Neutral900,
    tertiary = Info,
    onTertiary = P3Neutral950,
    background = P3Neutral50,
    onBackground = P3Neutral900,
    surface = P3Neutral0,
    onSurface = P3Neutral900,
    surfaceVariant = P3Neutral100,
    onSurfaceVariant = P3Neutral600,
    error = ErrorRed,
    onError = P3Neutral0,
    outline = P3Neutral200,
    outlineVariant = P3Neutral100,
    inverseSurface = P3Neutral900,
    inverseOnSurface = P3Neutral50,
    inversePrimary = P3Primary,
    scrim = P3Neutral950,
)

val Pallete3DarkColors: ColorScheme = darkColorScheme(
    primary = P3Primary,
    onPrimary = P3OnPrimary,
    primaryContainer = P3PrimaryPressed,
    onPrimaryContainer = P3OnPrimary,
    secondary = P3Neutral300,
    onSecondary = P3Neutral950,
    secondaryContainer = P3Neutral800,
    onSecondaryContainer = P3Neutral50,
    tertiary = Info,
    onTertiary = P3Neutral950,
    background = P3Neutral950,
    onBackground = P3Neutral50,
    surface = P3DarkSurface,
    onSurface = P3Neutral50,
    surfaceVariant = P3DarkSurfaceVariant,
    onSurfaceVariant = P3DarkOnSurfaceMuted,
    error = ErrorRed,
    onError = P3Neutral0,
    outline = P3Neutral700,
    outlineVariant = P3DarkSurfaceVariant,
    inverseSurface = P3Neutral50,
    inverseOnSurface = P3Neutral900,
    inversePrimary = P3Primary,
    scrim = P3Neutral950,
)
