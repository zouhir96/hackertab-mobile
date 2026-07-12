package com.zrcoding.hackertab.design.components

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect

/**
 * On-screen bounds (in root coordinates) of the Home feed elements spotlighted by the coachmark
 * tour. HomeScreen reports the rects; CoachmarkOverlay reads them so the spotlights follow the
 * real layout instead of hardcoded offsets.
 */
@Stable
class CoachmarkAnchors {
    var sourceRail: Rect? by mutableStateOf(null)
    var feed: Rect? by mutableStateOf(null)
    var firstCard: Rect? by mutableStateOf(null)
}

val LocalCoachmarkAnchors = compositionLocalOf { CoachmarkAnchors() }
