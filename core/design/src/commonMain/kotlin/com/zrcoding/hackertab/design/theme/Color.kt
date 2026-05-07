package com.zrcoding.hackertab.design.theme

import androidx.compose.ui.graphics.Color

/*
 * Hackertab v4 design tokens. Mirrors `design/project/styles/tokens.css`.
 *
 * OKLCH values from the canonical CSS were converted to sRGB hex using the
 * approximations documented in the Wave 0 brief. Exact values to be validated
 * in Wave 6 — TODO: validate exact OKLCH→sRGB color match.
 */

// region Brand & accent
val BrandPrimary = Color(0xFF7BFFAA)            // oklch(78% 0.19 145) — electric terminal green
val BrandPrimaryPressed = Color(0xFF54E893)     // oklch(72% 0.19 145)
val BrandOnPrimary = Color(0xFF161616)          // oklch(15% 0.01 145)
// endregion

// region Neutral ramp (cool-warm hybrid, hue 95 = paper)
val Neutral0 = Color(0xFFFCFCFB)
val Neutral50 = Color(0xFFF7F5F1)
val Neutral100 = Color(0xFFEDEAE3)
val Neutral200 = Color(0xFFDCD8CC)
val Neutral300 = Color(0xFFC1BAA9)
val Neutral400 = Color(0xFF96907F)
val Neutral500 = Color(0xFF6B6555)
val Neutral600 = Color(0xFF4F4A3D)
val Neutral700 = Color(0xFF3A352C)
val Neutral800 = Color(0xFF29251F)
val Neutral900 = Color(0xFF1A1B17)
val Neutral950 = Color(0xFF111210)
// endregion

// region Semantic
val Success = Color(0xFF4FE38B)                 // oklch(72% 0.18 150)
val Warning = Color(0xFFE8B752)                 // oklch(78% 0.15 75)
val ErrorRed = Color(0xFFE6504C)                // oklch(65% 0.20 25)
val Info = Color(0xFF7BB0E5)                    // oklch(70% 0.13 240)
// endregion

// region Surface — light theme
val LightBg = Neutral50
val LightBgElevated = Neutral0
val LightSurface = Neutral0
val LightSurfaceVariant = Neutral100
val LightOnBg = Neutral900
val LightOnSurface = Neutral900
val LightOnSurfaceMuted = Neutral500
val LightOnSurfaceFaint = Neutral400
val LightBorder = Neutral200
val LightBorderSubtle = Neutral100
// endregion

// region Surface — dark theme
val DarkBg = Neutral950
val DarkBgElevated = Neutral900
val DarkSurface = Color(0xFF14171C)             // oklch(13% 0.006 145)
val DarkSurfaceVariant = Color(0xFF1B1F22)      // oklch(17% 0.007 145)
val DarkOnBg = Neutral50
val DarkOnSurface = Neutral50
val DarkOnSurfaceMuted = Neutral400
val DarkOnSurfaceFaint = Neutral500
val DarkBorder = Color(0xFF24272A)              // oklch(22% 0.008 145)
val DarkBorderSubtle = Color(0xFF1B1F22)
// endregion

// region Source brand colors (preserved identity)
val SourceGithub = Color(0xFF181717)
val SourceHackerNews = Color(0xFFFF6600)
val SourceReddit = Color(0xFFFF4500)
val SourceProductHunt = Color(0xFFDA552F)
val SourceDevTo = Color(0xFF0A0A0A)
val SourceLobsters = Color(0xFFAC130D)
val SourceHashnode = Color(0xFF2962FF)
val SourceFreeCodeCamp = Color(0xFF0A0A23)
val SourceIndieHackers = Color(0xFF0E2439)
val SourceMedium = Color(0xFF00AB6C)
val SourceHackerNoon = Color(0xFF00FE00)
val SourceConferences = Color(0xFF6E56CF)
// endregion

// region Language tag colors
val TagJavaScript = Color(0xFFF7DF1E)
val TagTypeScript = Color(0xFF3178C6)
val TagPython = Color(0xFF3572A5)
val TagRust = Color(0xFFDEA584)
val TagGo = Color(0xFF00ADD8)
val TagKotlin = Color(0xFFA97BFF)
val TagJava = Color(0xFFED8B00)
val TagSwift = Color(0xFFF05138)
val TagCpp = Color(0xFFF34B7D)
val TagCSharp = Color(0xFF178600)
val TagRuby = Color(0xFF701516)
val TagPhp = Color(0xFF4F5D95)
val TagDart = Color(0xFF00B4AB)
val TagHtml = Color(0xFFE34C26)
val TagCss = Color(0xFF563D7C)
val TagShell = Color(0xFF89E051)
val TagElixir = Color(0xFF6E4A7E)
val TagHaskell = Color(0xFF5E5086)
val TagScala = Color(0xFFC22D40)
val TagClojure = Color(0xFFDB5855)
val TagVue = Color(0xFF41B883)
val TagSvelte = Color(0xFFFF3E00)
// endregion

// region Legacy aliases — DO NOT REMOVE
// Wave 1 will migrate call-sites to the new token names. Until then these
// aliases keep `feature/*` and `core/design/components` compiling.
@Deprecated(
    message = "Wave 1 migration: use BrandPrimary or a semantic color instead.",
    replaceWith = ReplaceWith("BrandPrimary"),
)
val TextLink = Color(0xFF0366D6)

@Deprecated(
    message = "Wave 1 migration: use the appropriate Source* token (e.g. SourceHackerNews) or Warning.",
    replaceWith = ReplaceWith("SourceHackerNews"),
)
val Flamingo = Color(0xFFF6682F)

@Deprecated(
    message = "Wave 1 migration: use ErrorRed.",
    replaceWith = ReplaceWith("ErrorRed"),
)
val ChestnutRose = Color(0xFFCF6679)

@Deprecated(
    message = "Wave 1 migration: use Neutral950 / DarkBg.",
    replaceWith = ReplaceWith("Neutral950"),
)
val ChineseBlack = Color(0xFF0D1116)

@Deprecated(
    message = "Wave 1 migration: use Neutral50 / LightBg.",
    replaceWith = ReplaceWith("Neutral50"),
)
val HawkesBlue = Color(0xFFEFF6FE)

@Deprecated(
    message = "Wave 1 migration: use BrandPrimary or a semantic color.",
    replaceWith = ReplaceWith("BrandPrimary"),
)
val Blue = Color(0XFF0366D6)

@Deprecated(
    message = "Wave 1 migration: use Neutral900.",
    replaceWith = ReplaceWith("Neutral900"),
)
val Black900 = Color(0xFF272728)

@Deprecated(
    message = "Wave 1 migration: use Neutral700.",
    replaceWith = ReplaceWith("Neutral700"),
)
val Black700 = Color(0xFF575758)

@Deprecated(
    message = "Wave 1 migration: use Neutral400.",
    replaceWith = ReplaceWith("Neutral400"),
)
val Black400 = Color(0xFF9F9F9F)

@Deprecated(
    message = "Wave 1 migration: use Neutral0 or Neutral50.",
    replaceWith = ReplaceWith("Neutral0"),
)
val White600 = Color(0xFFF9F9F9)
// endregion
