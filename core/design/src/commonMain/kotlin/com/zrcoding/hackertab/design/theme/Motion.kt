package com.zrcoding.hackertab.design.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object HackertabMotion {

    const val instant: Int = 100

    const val fast: Int = 200

    const val standard: Int = 300

    const val slow: Int = 500

    val standardEasing: Easing = CubicBezierEasing(0.2f, 0f, 0f, 1f)

    val emphasizedEasing: Easing = CubicBezierEasing(0.3f, 0f, 0f, 1f)

    val deceleratedEasing: Easing = CubicBezierEasing(0f, 0f, 0.2f, 1f)

    val springStandard: SpringSpec<Float> = spring(
        stiffness = Spring.StiffnessMedium,
        dampingRatio = Spring.DampingRatioNoBouncy,
    )

    val springEmphasized: SpringSpec<Float> = spring(
        stiffness = 380f,
        dampingRatio = 0.5f,
    )
}

val MaterialTheme.motion: HackertabMotion
    @Composable
    @ReadOnlyComposable
    get() = HackertabMotion
