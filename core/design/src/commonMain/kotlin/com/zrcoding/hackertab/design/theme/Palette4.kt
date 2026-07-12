package com.zrcoding.hackertab.design.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val P4Primary = Color(0xFFFF6B35)
private val P4PrimaryPressed = Color(0xFFE5511E)
private val P4OnPrimary = Color(0xFF1A0904)

private val P4Neutral0 = Color(0xFFFEFBF7)
private val P4Neutral50 = Color(0xFFFAF4ED)
private val P4Neutral100 = Color(0xFFF0E5D5)
private val P4Neutral200 = Color(0xFFDCC9AB)
private val P4Neutral300 = Color(0xFFC3AB87)
private val P4Neutral400 = Color(0xFF98835F)
private val P4Neutral600 = Color(0xFF574630)
private val P4Neutral700 = Color(0xFF3A2E1F)
private val P4Neutral800 = Color(0xFF291F13)
private val P4Neutral900 = Color(0xFF19130A)
private val P4Neutral950 = Color(0xFF0F0B06)
private val P4DarkSurface = Color(0xFF1F1812)
private val P4DarkSurfaceVariant = Color(0xFF291F13)
private val P4DarkOnSurfaceMuted = Color(0xFFB59C7A)

val Palette4LightColors: ColorScheme = lightColorScheme(
    primary = P4Primary,
    onPrimary = P4OnPrimary,
    primaryContainer = P4Primary,
    onPrimaryContainer = P4OnPrimary,
    secondary = P4Neutral700,
    onSecondary = P4Neutral50,
    secondaryContainer = P4Neutral100,
    onSecondaryContainer = P4Neutral900,
    tertiary = Info,
    onTertiary = P4Neutral950,
    background = P4Neutral50,
    onBackground = P4Neutral900,
    surface = P4Neutral0,
    onSurface = P4Neutral900,
    surfaceVariant = P4Neutral100,
    onSurfaceVariant = P4Neutral600,
    error = ErrorRed,
    onError = P4Neutral0,
    outline = P4Neutral200,
    outlineVariant = P4Neutral100,
    inverseSurface = P4Neutral900,
    inverseOnSurface = P4Neutral50,
    inversePrimary = P4Primary,
    scrim = P4Neutral950,
)

val Palette4DarkColors: ColorScheme = darkColorScheme(
    primary = P4Primary,
    onPrimary = P4OnPrimary,
    primaryContainer = P4PrimaryPressed,
    onPrimaryContainer = P4OnPrimary,
    secondary = P4Neutral300,
    onSecondary = P4Neutral950,
    secondaryContainer = P4Neutral800,
    onSecondaryContainer = P4Neutral50,
    tertiary = Info,
    onTertiary = P4Neutral950,
    background = P4Neutral950,
    onBackground = P4Neutral50,
    surface = P4DarkSurface,
    onSurface = P4Neutral50,
    surfaceVariant = P4DarkSurfaceVariant,
    onSurfaceVariant = P4DarkOnSurfaceMuted,
    error = ErrorRed,
    onError = P4Neutral0,
    outline = P4Neutral700,
    outlineVariant = P4DarkSurfaceVariant,
    inverseSurface = P4Neutral50,
    inverseOnSurface = P4Neutral900,
    inversePrimary = P4Primary,
    scrim = P4Neutral950,
)
