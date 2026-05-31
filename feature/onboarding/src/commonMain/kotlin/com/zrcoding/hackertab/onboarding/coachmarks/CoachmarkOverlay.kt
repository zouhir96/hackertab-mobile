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
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

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

@Composable
fun CoachmarkOverlay(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentStep by remember { mutableIntStateOf(1) }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 200)),
        exit = fadeOut(animationSpec = tween(durationMillis = 200)),
        modifier = modifier,
    ) {
        val scene = SCENES.getOrNull(currentStep - 1) ?: return@AnimatedVisibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { }
                }
                .background(Color.Black.copy(alpha = 0.55f))
                .semantics {
                    role = Role.Image
                    contentDescription = "Coachmark step $currentStep of ${SCENES.size}: " +
                        "${scene.title}. ${scene.body}"
                },
        ) {
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

            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = MaterialTheme.dimension.space24, end = MaterialTheme.dimension.space12)
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

            val tooltipTop = if (scene.tooltipBelow) {
                scene.spotlightTop + scene.spotlightHeight + MaterialTheme.dimension.space16
            } else {
                (scene.spotlightTop - 140.dp).coerceAtLeast(60.dp)
            }
            Box(
                modifier = Modifier
                    .offset(y = tooltipTop)
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimension.space12),
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
        .clip(RoundedCornerShape(MaterialTheme.dimension.space8))
        .border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(MaterialTheme.dimension.space8),
        )
        .background(
            color = Color.White.copy(alpha = 0.08f),
            shape = RoundedCornerShape(MaterialTheme.dimension.space8),
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
                .clip(RoundedCornerShape(MaterialTheme.dimension.space12))
                .background(MaterialTheme.colorScheme.inverseSurface)
                .padding(horizontal = MaterialTheme.dimension.space16, vertical = MaterialTheme.dimension.space12),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space6)) {
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
                Spacer(Modifier.height(MaterialTheme.dimension.space4))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(MaterialTheme.dimension.space8))
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = onAdvance,
                            )
                            .padding(horizontal = MaterialTheme.dimension.space12, vertical = MaterialTheme.dimension.space8)
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
            .size(MaterialTheme.dimension.space12)
            .rotate(if (downward) 225f else 45f)
            .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape),
    )
}

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
