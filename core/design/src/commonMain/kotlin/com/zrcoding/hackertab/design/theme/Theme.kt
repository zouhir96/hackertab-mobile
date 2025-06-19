package com.zrcoding.hackertab.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Blue,
    primaryVariant = ChineseBlack,
    secondary = Black900,
    secondaryVariant = Black900,
    background = ChineseBlack,
    error = ChestnutRose
)

private val LightColorPalette = lightColors(
    primary = Blue,
    primaryVariant = HawkesBlue,
    secondary = White600,
    secondaryVariant = White600,
    background = HawkesBlue,
    error = ChestnutRose
)

@Composable
fun HackertabTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

val MaterialTheme.dimension: Dimens
    get() = Dimens()