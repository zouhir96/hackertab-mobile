package com.zrcoding.hackertab.design.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Hackertab v4 motion tokens. Mirrors the durations, easings, and springs in
 * `design/project/styles/tokens.css` (lines 92–99) and
 * `design/project/components/Motion.jsx`.
 *
 * Durations are exposed as `Int` ms because that is what `tween()` accepts.
 *
 * Access from a composable via `MaterialTheme.motion.standard` etc.
 */
object HackertabMotion {

    // region Durations (ms) — match --duration-* in tokens.css
    /** 100 ms — instant feedback (chip toggle, ripple). */
    const val instant: Int = 100

    /** 200 ms — fast UI reaction (hover, focus, small surface change). */
    const val fast: Int = 200

    /** 300 ms — Material standard duration (sheet, dialog, page transition). */
    const val standard: Int = 300

    /** 500 ms — slow, hero / first-impression motion. */
    const val slow: Int = 500
    // endregion

    // region Easings — match --easing-* in tokens.css
    /** `cubic-bezier(.2, 0, 0, 1)` — default ease for in-and-out motion. */
    val standardEasing: Easing = CubicBezierEasing(0.2f, 0f, 0f, 1f)

    /** `cubic-bezier(.3, 0, 0, 1)` — emphasised ease for marketing surfaces. */
    val emphasizedEasing: Easing = CubicBezierEasing(0.3f, 0f, 0f, 1f)

    /** `cubic-bezier(0, 0, .2, 1)` — decelerated ease for entering motion. */
    val deceleratedEasing: Easing = CubicBezierEasing(0f, 0f, 0.2f, 1f)
    // endregion

    // region Springs — derived from design/project/components/Motion.jsx
    /** Smooth, no-bounce spring for steady-state UI transitions. */
    val springStandard: SpringSpec<Float> = spring(
        stiffness = Spring.StiffnessMedium,
        dampingRatio = Spring.DampingRatioNoBouncy,
    )

    /**
     * Emphasised spring for hero motion — slightly under-damped so a small
     * overshoot reads on screen. Approximates `spring(380, 29)` from the
     * Motion reference.
     */
    val springEmphasized: SpringSpec<Float> = spring(
        stiffness = 380f,
        dampingRatio = 0.5f,
    )
    // endregion
}

/** Convenience accessor parallel to `MaterialTheme.dimension`. */
val MaterialTheme.motion: HackertabMotion
    @Composable
    @ReadOnlyComposable
    get() = HackertabMotion
