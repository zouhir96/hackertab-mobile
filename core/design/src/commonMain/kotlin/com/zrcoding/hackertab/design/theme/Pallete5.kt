package com.zrcoding.hackertab.design.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val P5Primary = Color(0xFF34D399)
private val P5PrimaryPressed = Color(0xFF10B981)
private val P5OnPrimary = Color(0xFF04221A)

private val P5Neutral0 = Color(0xFFFBFCFC)
private val P5Neutral50 = Color(0xFFF2F6F5)
private val P5Neutral100 = Color(0xFFE1E9E6)
private val P5Neutral200 = Color(0xFFC2D0CC)
private val P5Neutral300 = Color(0xFF95ABA3)
private val P5Neutral400 = Color(0xFF647A72)
private val P5Neutral600 = Color(0xFF3F5247)
private val P5Neutral700 = Color(0xFF293831)
private val P5Neutral800 = Color(0xFF1A2620)
private val P5Neutral900 = Color(0xFF101814)
private val P5Neutral950 = Color(0xFF08110D)
private val P5DarkSurface = Color(0xFF13201B)
private val P5DarkSurfaceVariant = Color(0xFF1A2620)
private val P5DarkOnSurfaceMuted = Color(0xFF9AAFA4)

val Pallete5LightColors: ColorScheme = lightColorScheme(
    primary = P5Primary,
    onPrimary = P5OnPrimary,
    primaryContainer = P5Primary,
    onPrimaryContainer = P5OnPrimary,
    secondary = P5Neutral700,
    onSecondary = P5Neutral50,
    secondaryContainer = P5Neutral100,
    onSecondaryContainer = P5Neutral900,
    tertiary = Info,
    onTertiary = P5Neutral950,
    background = P5Neutral50,
    onBackground = P5Neutral900,
    surface = P5Neutral0,
    onSurface = P5Neutral900,
    surfaceVariant = P5Neutral100,
    onSurfaceVariant = P5Neutral600,
    error = ErrorRed,
    onError = P5Neutral0,
    outline = P5Neutral200,
    outlineVariant = P5Neutral100,
    inverseSurface = P5Neutral900,
    inverseOnSurface = P5Neutral50,
    inversePrimary = P5Primary,
    scrim = P5Neutral950,
)

val Pallete5DarkColors: ColorScheme = darkColorScheme(
    primary = P5Primary,
    onPrimary = P5OnPrimary,
    primaryContainer = P5PrimaryPressed,
    onPrimaryContainer = P5OnPrimary,
    secondary = P5Neutral300,
    onSecondary = P5Neutral950,
    secondaryContainer = P5Neutral800,
    onSecondaryContainer = P5Neutral50,
    tertiary = Info,
    onTertiary = P5Neutral950,
    background = P5Neutral950,
    onBackground = P5Neutral50,
    surface = P5DarkSurface,
    onSurface = P5Neutral50,
    surfaceVariant = P5DarkSurfaceVariant,
    onSurfaceVariant = P5DarkOnSurfaceMuted,
    error = ErrorRed,
    onError = P5Neutral0,
    outline = P5Neutral700,
    outlineVariant = P5DarkSurfaceVariant,
    inverseSurface = P5Neutral50,
    inverseOnSurface = P5Neutral900,
    inversePrimary = P5Primary,
    scrim = P5Neutral950,
)
