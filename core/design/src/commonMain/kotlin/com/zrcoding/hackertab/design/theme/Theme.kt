package com.zrcoding.hackertab.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.domain.models.ThemeFont
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.domain.models.ThemePalette

fun ThemePalette.toLightColorScheme(): ColorScheme = when (this) {
    ThemePalette.DEFAULT -> PaletteDefaultLightColors
    ThemePalette.AMBER -> Palette1LightColors
    ThemePalette.CYAN -> Palette2LightColors
    ThemePalette.VIOLET -> Palette3LightColors
    ThemePalette.ORANGE -> Palette4LightColors
    ThemePalette.EMERALD -> Palette5LightColors
}

fun ThemePalette.toDarkColorScheme(): ColorScheme = when (this) {
    ThemePalette.DEFAULT -> PaletteDefaultDarkColors
    ThemePalette.AMBER -> Palette1DarkColors
    ThemePalette.CYAN -> Palette2DarkColors
    ThemePalette.VIOLET -> Palette3DarkColors
    ThemePalette.ORANGE -> Palette4DarkColors
    ThemePalette.EMERALD -> Palette5DarkColors
}

@Composable
fun HackertabTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    palette: ThemePalette = ThemePalette.DEFAULT,
    font: ThemeFont = ThemeFont.GEIST,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val colorScheme = if (darkTheme) palette.toDarkColorScheme() else palette.toLightColorScheme()

    MaterialTheme(
        colorScheme = colorScheme,
        typography = font.toTypography(),
        shapes = Shapes,
        content = content,
    )
}

@Deprecated(
    message = "Wave 1 migration: use the ThemeMode overload.",
    replaceWith = ReplaceWith(
        expression = "HackertabTheme(themeMode = if (darkTheme) ThemeMode.DARK else ThemeMode.LIGHT, content = content)",
        imports = ["com.zrcoding.hackertab.domain.models.ThemeMode"],
    ),
)
@Composable
fun HackertabTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) PaletteDefaultDarkColors else PaletteDefaultLightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}

val MaterialTheme.dimension: Dimens
    get() = Dimens()
