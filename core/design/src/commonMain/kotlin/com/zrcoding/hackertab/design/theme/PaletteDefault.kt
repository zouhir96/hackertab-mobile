package com.zrcoding.hackertab.design.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val PaletteDefaultLightColors: ColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = BrandOnPrimary,
    primaryContainer = BrandPrimary,
    onPrimaryContainer = BrandOnPrimary,
    secondary = Neutral700,
    onSecondary = Neutral50,
    secondaryContainer = Neutral100,
    onSecondaryContainer = Neutral900,
    tertiary = Info,
    onTertiary = Neutral950,
    background = LightBg,
    onBackground = LightOnBg,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceMuted,
    error = ErrorRed,
    onError = Neutral0,
    outline = LightBorder,
    outlineVariant = LightBorderSubtle,
    inverseSurface = Neutral900,
    inverseOnSurface = Neutral50,
    inversePrimary = BrandPrimary,
    scrim = Neutral950,
)

val PaletteDefaultDarkColors: ColorScheme = darkColorScheme(
    primary = BrandPrimary,
    onPrimary = BrandOnPrimary,
    primaryContainer = BrandPrimaryPressed,
    onPrimaryContainer = BrandOnPrimary,
    secondary = Neutral300,
    onSecondary = Neutral950,
    secondaryContainer = Neutral800,
    onSecondaryContainer = Neutral50,
    tertiary = Info,
    onTertiary = Neutral950,
    background = DarkBg,
    onBackground = DarkOnBg,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceMuted,
    error = ErrorRed,
    onError = Neutral0,
    outline = DarkBorder,
    outlineVariant = DarkBorderSubtle,
    inverseSurface = Neutral50,
    inverseOnSurface = Neutral900,
    inversePrimary = BrandPrimary,
    scrim = Neutral950,
)
