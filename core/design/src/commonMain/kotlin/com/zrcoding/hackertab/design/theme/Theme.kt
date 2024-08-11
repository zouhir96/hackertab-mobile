package com.zrcoding.hackertab.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = ChineseBlack,
    primaryVariant = ChineseBlack,
    secondaryVariant = Color.Black,
    background = ChineseBlack,
    error = ChestnutRose,
)

private val LightColorPalette = lightColors(
    primary = HawkesBlue,
    primaryVariant = HawkesBlue,
    secondaryVariant = Color.White,
    background = HawkesBlue,
    error = ChestnutRose,
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