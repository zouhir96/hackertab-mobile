package com.zrcoding.hackertab.design.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Hackertab v4 spacing scale. Mirrors `--space-*` tokens in
 * `design/project/styles/tokens.css` (lines 71–83).
 *
 * Old field names (`none`, `tiny`, `small`, ...) are retained as
 * `@Deprecated` aliases that route to the new explicit-unit scale; Wave 1
 * mass-migrates call sites and removes the aliases.
 */
data class Dimens(
    // New explicit-unit scale (matches tokens.css)
    val space2: Dp = 2.dp,
    val space4: Dp = 4.dp,
    val space6: Dp = 6.dp,
    val space8: Dp = 8.dp,
    val space12: Dp = 12.dp,
    val space16: Dp = 16.dp,
    val space20: Dp = 20.dp,
    val space24: Dp = 24.dp,
    val space32: Dp = 32.dp,
    val space40: Dp = 40.dp,
    val space48: Dp = 48.dp,
    val space64: Dp = 64.dp,

    // Screen-level padding shortcut — kept because v4 still uses 20dp gutters.
    val screenPaddingHorizontal: Dp = 20.dp,
) {
    @Deprecated(
        message = "Wave 1 migration: use space0 == 0.dp directly.",
        replaceWith = ReplaceWith("0.dp", "androidx.compose.ui.unit.dp"),
    )
    val none: Dp get() = 0.dp

    @Deprecated(
        message = "Wave 1 migration: use space2.",
        replaceWith = ReplaceWith("space2"),
    )
    val tiny: Dp get() = space2

    @Deprecated(
        message = "Wave 1 migration: use space4.",
        replaceWith = ReplaceWith("space4"),
    )
    val small: Dp get() = space4

    @Deprecated(
        message = "Wave 1 migration: use space8.",
        replaceWith = ReplaceWith("space8"),
    )
    val medium: Dp get() = space8

    @Deprecated(
        message = "Wave 1 migration: use space12.",
        replaceWith = ReplaceWith("space12"),
    )
    val large: Dp get() = space12

    @Deprecated(
        message = "Wave 1 migration: use space16.",
        replaceWith = ReplaceWith("space16"),
    )
    val default: Dp get() = space16

    @Deprecated(
        message = "Wave 1 migration: use space20.",
        replaceWith = ReplaceWith("space20"),
    )
    val big: Dp get() = space20

    @Deprecated(
        message = "Wave 1 migration: use space24.",
        replaceWith = ReplaceWith("space24"),
    )
    val bigger: Dp get() = space24

    @Deprecated(
        message = "Wave 1 migration: use space40.",
        replaceWith = ReplaceWith("space40"),
    )
    val extraBig: Dp get() = space40
}
