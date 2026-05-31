package com.zrcoding.hackertab.design.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/*
 * Palette 2 — "Cyber Cyan"
 *
 * Mood: electric cyan over cool slate; technical / "ops dashboard".
 * Vibe: Grafana, Linear-dark, monitoring tools. Cyan reads as informational
 * and high-tech without being aggressive.
 * Trade vs current v4 green: shifts from "terminal prompt" to "instrument panel".
 * Cool neutrals make red/orange source chips pop more by contrast.
 */

// region Brand
private val P2Primary = Color(0xFF22C7DD)
private val P2PrimaryPressed = Color(0xFF0FA5BC)
private val P2OnPrimary = Color(0xFF04212A)
// endregion

// region Neutrals — cool blue-tinted (hue ~220)
private val P2Neutral0 = Color(0xFFFBFCFE)
private val P2Neutral50 = Color(0xFFF2F6FA)
private val P2Neutral100 = Color(0xFFE2EAF2)
private val P2Neutral200 = Color(0xFFC5D2DF)
private val P2Neutral300 = Color(0xFF9AABBE)
private val P2Neutral400 = Color(0xFF6A7C90)
private val P2Neutral600 = Color(0xFF445063)
private val P2Neutral700 = Color(0xFF2C3744)
private val P2Neutral800 = Color(0xFF1E2630)
private val P2Neutral900 = Color(0xFF131820)
private val P2Neutral950 = Color(0xFF0B0F15)
private val P2DarkSurface = Color(0xFF161D27)
private val P2DarkSurfaceVariant = Color(0xFF1E2630)
private val P2DarkOnSurfaceMuted = Color(0xFF9AAABE)
// endregion

val Pallete2LightColors: ColorScheme = lightColorScheme(
    primary = P2Primary,
    onPrimary = P2OnPrimary,
    primaryContainer = P2Primary,
    onPrimaryContainer = P2OnPrimary,
    secondary = P2Neutral700,
    onSecondary = P2Neutral50,
    secondaryContainer = P2Neutral100,
    onSecondaryContainer = P2Neutral900,
    tertiary = Info,
    onTertiary = P2Neutral950,
    background = P2Neutral50,
    onBackground = P2Neutral900,
    surface = P2Neutral0,
    onSurface = P2Neutral900,
    surfaceVariant = P2Neutral100,
    onSurfaceVariant = P2Neutral600,
    error = ErrorRed,
    onError = P2Neutral0,
    outline = P2Neutral200,
    outlineVariant = P2Neutral100,
    inverseSurface = P2Neutral900,
    inverseOnSurface = P2Neutral50,
    inversePrimary = P2Primary,
    scrim = P2Neutral950,
)

val Pallete2DarkColors: ColorScheme = darkColorScheme(
    primary = P2Primary,
    onPrimary = P2OnPrimary,
    primaryContainer = P2PrimaryPressed,
    onPrimaryContainer = P2OnPrimary,
    secondary = P2Neutral300,
    onSecondary = P2Neutral950,
    secondaryContainer = P2Neutral800,
    onSecondaryContainer = P2Neutral50,
    tertiary = Info,
    onTertiary = P2Neutral950,
    background = P2Neutral950,
    onBackground = P2Neutral50,
    surface = P2DarkSurface,
    onSurface = P2Neutral50,
    surfaceVariant = P2DarkSurfaceVariant,
    onSurfaceVariant = P2DarkOnSurfaceMuted,
    error = ErrorRed,
    onError = P2Neutral0,
    outline = P2Neutral700,
    outlineVariant = P2DarkSurfaceVariant,
    inverseSurface = P2Neutral50,
    inverseOnSurface = P2Neutral900,
    inversePrimary = P2Primary,
    scrim = P2Neutral950,
)
