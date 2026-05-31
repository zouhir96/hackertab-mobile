package com.zrcoding.hackertab.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.domain.models.ThemeMode

/**
 * Hackertab v4 theme. Wraps Material 3's [MaterialTheme] with the
 * Hackertab token set (Color.kt, Type.kt, Shape.kt, Dimens.kt, Motion.kt).
 *
 * v4 introduces user-controlled [ThemeMode] so the app honours one of
 * three modes (LIGHT / DARK / SYSTEM). The legacy boolean signature is
 * retained as a `@Deprecated` overload for the duration of Wave 1 migration.
 */

private val LightColors: ColorScheme = lightColorScheme(
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

private val DarkColors: ColorScheme = darkColorScheme(
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

/**
 * v4 theme entry-point. Use [themeMode] to honour user preference; defaults
 * to [ThemeMode.SYSTEM] which reads the platform dark-mode flag.
 */
@Composable
fun HackertabTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val colorScheme = if (darkTheme) Pallete2DarkColors else Pallete2LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}

/**
 * Legacy v3 entry-point. Wave 1 migrates call-sites to the [ThemeMode]
 * overload above; this overload is removed in Wave 7.
 */
@Deprecated(
    message = "Wave 1 migration: use the ThemeMode overload.",
    replaceWith = ReplaceWith(
        expression = "HackertabTheme(themeMode = if (darkTheme) ThemeMode.DARK else ThemeMode.LIGHT, content = content)",
        imports = ["com.zrcoding.hackertab.domain.models.ThemeMode"],
    ),
)
@Composable
fun HackertabTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}

/**
 * Convenience accessor parallel to [MaterialTheme.colorScheme].
 * Call sites read `MaterialTheme.dimension.space16` instead of constructing
 * a [Dimens] each time.
 */
val MaterialTheme.dimension: Dimens
    get() = Dimens()

/**
 * M2 parity accessor — kept for the duration of Wave 1 migration so
 * legacy `androidx.compose.material.MaterialTheme.dimension.*` call sites
 * still resolve. Removed in Wave 7.
 */
@Suppress("unused")
@Deprecated(
    message = "Wave 1 migration: switch to androidx.compose.material3.MaterialTheme.",
    level = DeprecationLevel.WARNING,
)
val androidx.compose.material.MaterialTheme.dimension: Dimens
    get() = Dimens()
