package com.zrcoding.hackertab.design.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Hackertab v4 shape scale. Matches `--radius-*` tokens in
 * `design/project/styles/tokens.css` (lines 86–90).
 *  - extraSmall: 4dp (chips, ticks)
 *  - small:      6dp (buttons, small chips — `--radius-sm`)
 *  - medium:    10dp (cards, input fields — `--radius-md`)
 *  - large:     14dp (large cards, sheets — `--radius-lg`)
 *  - extraLarge: 20dp (modals, hero surfaces — `--radius-xl`)
 *
 *  `--radius-full: 999px` is intentionally not modeled here: callers use
 *  `CircleShape` directly for fully-rounded surfaces.
 */
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(6.dp),
    medium = RoundedCornerShape(10.dp),
    large = RoundedCornerShape(14.dp),
    extraLarge = RoundedCornerShape(20.dp),
)
