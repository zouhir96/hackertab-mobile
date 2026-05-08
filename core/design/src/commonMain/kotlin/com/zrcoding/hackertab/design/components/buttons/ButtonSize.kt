package com.zrcoding.hackertab.design.components.buttons

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Hackertab v4 button size scale. Heights, paddings, and text styles match
 * `design/project/components/Library.jsx` (slug 03.04 Buttons).
 *
 * Size selection:
 *  - [Small]  — inline / dense surfaces (filter rows, secondary calls).
 *  - [Medium] — default app-wide button height.
 *  - [Large]  — primary CTAs at the bottom of full-bleed onboarding screens.
 *
 *  Shape:
 *  - Small / Medium use [MaterialTheme.shapes.medium] (10dp).
 *  - Large uses fully-rounded `CircleShape` per the design reference.
 */
enum class ButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val iconSize: Dp,
) {
    Small(height = 32.dp, horizontalPadding = 14.dp, iconSize = 16.dp),
    Medium(height = 42.dp, horizontalPadding = 18.dp, iconSize = 18.dp),
    Large(height = 54.dp, horizontalPadding = 22.dp, iconSize = 20.dp);

    @Composable
    fun textStyle(): TextStyle = when (this) {
        Small -> MaterialTheme.typography.labelMedium
        Medium -> MaterialTheme.typography.labelLarge
        Large -> MaterialTheme.typography.titleMedium
    }
}
