package com.zrcoding.hackertab.onboarding.coachmarks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

// TODO Wave 6 a11y — wire to LocalAccessibilityManager when CMP stabilises
private const val IS_REDUCED_MOTION = false

/**
 * Spotlight scene definition for [CoachmarkOverlay].
 *
 * Coordinates are dp offsets from the top-left of the overlay. The spotlight
 * is rendered as a transparent rounded rect on top of a scrim; the tooltip is
 * positioned [tooltipBelow] (true) or above the spotlight.
 */
private data class CoachmarkScene(
    val spotlightTop: Dp,
    val spotlightLeftPad: Dp,
    val spotlightRightPad: Dp,
    val spotlightHeight: Dp,
    val spotlightWidth: Dp? = null,
    val centered: Boolean = false,
    val title: String,
    val body: String,
    val tooltipBelow: Boolean,
)

private val SCENES = listOf(
    CoachmarkScene(
        spotlightTop = 100.dp,
        spotlightLeftPad = 14.dp,
        spotlightRightPad = 14.dp,
        spotlightHeight = 38.dp,
        title = "Tap a source",
        body = "Switch from All → GitHub, HN, etc. via the rail under the title.",
        tooltipBelow = true,
    ),
    CoachmarkScene(
        spotlightTop = 300.dp,
        spotlightLeftPad = 0.dp,
        spotlightRightPad = 0.dp,
        spotlightHeight = 60.dp,
        spotlightWidth = 60.dp,
        centered = true,
        title = "Pull down to refresh",
        body = "Drag the feed down. The Hackertab spinner shows while we fetch.",
        tooltipBelow = false,
    ),
    CoachmarkScene(
        spotlightTop = 200.dp,
        spotlightLeftPad = 14.dp,
        spotlightRightPad = 14.dp,
        spotlightHeight = 120.dp,
        title = "Long-press a card",
        body = "Hold any card for Save · Share · Open · Copy link.",
        tooltipBelow = true,
    ),
)

/**
 * Wave 5K — full-screen coachmark overlay shown on first arrival at the Home
 * feed after onboarding. Three scenes guide the user through: source rail,
 * pull-to-refresh, and long-press menu. "Skip tour" and "Got it" both exit.
 *
 * Mirrors `design/project/components/Motion.jsx` — Coachmark + CoachmarkScene.
 */
@Composable
fun CoachmarkOverlay(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentStep by remember { mutableIntStateOf(1) }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = if (IS_REDUCED_MOTION) 0 else 200)),
        exit = fadeOut(animationSpec = tween(durationMillis = if (IS_REDUCED_MOTION) 0 else 200)),
        modifier = modifier,
    ) {
        val scene = SCENES.getOrNull(currentStep - 1) ?: return@AnimatedVisibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                // Swallow all touches; scrim absorbs taps outside the controls.
                .pointerInput(Unit) {
                    detectTapGestures { /* no-op */ }
                }
                .background(Color.Black.copy(alpha = 0.55f))
                .semantics {
                    role = Role.Image
                    contentDescription = "Coachmark step $currentStep of ${SCENES.size}: " +
                        "${scene.title}. ${scene.body}"
                },
        ) {
            // Spotlight cut-out (transparent rounded rect over the scrim).
            // Visually emphasises the area without actually clipping the scrim
            // (a true cut-out would need a Canvas BlendMode pass; this brighter
            // outlined rect is the design-spec approximation).
            val spotlightModifier = Modifier
                .offset(y = scene.spotlightTop)
                .padding(
                    start = scene.spotlightLeftPad,
                    end = scene.spotlightRightPad,
                )
                .then(
                    if (scene.centered && scene.spotlightWidth != null) {
                        Modifier.fillMaxWidth().padding(horizontal = 0.dp)
                    } else {
                        Modifier.fillMaxWidth()
                    },
                )

            Box(
                modifier = spotlightModifier,
                contentAlignment = if (scene.centered) Alignment.TopCenter else Alignment.TopStart,
            ) {
                Spotlight(
                    width = scene.spotlightWidth,
                    height = scene.spotlightHeight,
                )
            }

            // Skip tour — top-right of the scrim.
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 24.dp, end = 12.dp)
                    .semantics {
                        role = Role.Button
                        contentDescription = "Skip tour"
                    },
            ) {
                Text(
                    text = "Skip tour",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                )
            }

            // Tooltip card — positioned above or below the spotlight area.
            val tooltipTop = if (scene.tooltipBelow) {
                scene.spotlightTop + scene.spotlightHeight + 16.dp
            } else {
                (scene.spotlightTop - 140.dp).coerceAtLeast(60.dp)
            }
            Box(
                modifier = Modifier
                    .offset(y = tooltipTop)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.Center,
            ) {
                TooltipCard(
                    stepLabel = "0$currentStep / 0${SCENES.size}",
                    title = scene.title,
                    body = scene.body,
                    onAdvance = {
                        val next = currentStep + 1
                        if (next > SCENES.size) {
                            onDismiss()
                            currentStep = 1
                        } else {
                            currentStep = next
                        }
                    },
                    showPointerAbove = scene.tooltipBelow,
                )
            }
        }
    }
}

@Composable
private fun Spotlight(width: Dp?, height: Dp) {
    val mod = Modifier
        .then(if (width != null) Modifier.width(width) else Modifier.fillMaxWidth())
        .height(height)
        .clip(RoundedCornerShape(10.dp))
        .border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(10.dp),
        )
        .background(
            color = Color.White.copy(alpha = 0.08f),
            shape = RoundedCornerShape(10.dp),
        )
    Box(modifier = mod)
}

@Composable
private fun TooltipCard(
    stepLabel: String,
    title: String,
    body: String,
    onAdvance: () -> Unit,
    showPointerAbove: Boolean,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (showPointerAbove) {
            Pointer(downward = false)
        }
        Box(
            modifier = Modifier
                .width(260.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.inverseSurface)
                .padding(horizontal = 16.dp, vertical = 14.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = stepLabel,
                    style = codeSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W600),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                Text(
                    text = body,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.7f),
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = onAdvance,
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .semantics {
                                role = Role.Button
                                contentDescription = "Got it, next step"
                            },
                    ) {
                        Text(
                            text = "Got it",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
        }
        if (!showPointerAbove) {
            Pointer(downward = true)
        }
    }
}

@Composable
private fun Pointer(downward: Boolean) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .rotate(if (downward) 225f else 45f)
            .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape),
    )
}

// -------------------------------------------------------------------------
// Previews
// -------------------------------------------------------------------------

@Preview
@Composable
private fun CoachmarkOverlayLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            CoachmarkOverlay(visible = true, onDismiss = {})
        }
    }
}

@Preview
@Composable
private fun CoachmarkOverlayDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            CoachmarkOverlay(visible = true, onDismiss = {})
        }
    }
}
