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
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.LocalCoachmarkAnchors
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

private data class CoachmarkScene(
    val title: String,
    val body: String,
    val tooltipBelow: Boolean,
)

private val SCENES = listOf(
    CoachmarkScene(
        title = "Tap a source",
        body = "Switch between GitHub, HN, and more via the rail at the top.",
        tooltipBelow = true,
    ),
    CoachmarkScene(
        title = "Pull down to refresh",
        body = "Drag the feed down. The Hackertab spinner shows while we fetch.",
        tooltipBelow = false,
    ),
    CoachmarkScene(
        title = "Long-press a card",
        body = "Hold any card for Save · Share · Open · Copy link.",
        tooltipBelow = true,
    ),
)

private data class SpotlightBounds(
    val left: Dp,
    val top: Dp,
    val width: Dp,
    val height: Dp,
)

private fun Rect.toSpotlightBounds(
    density: Density,
    origin: Offset,
    inflate: Dp,
): SpotlightBounds = with(density) {
    val inflatePx = inflate.toPx()
    SpotlightBounds(
        left = (left - origin.x - inflatePx).toDp(),
        top = (top - origin.y - inflatePx).toDp(),
        width = (width + 2 * inflatePx).toDp(),
        height = (height + 2 * inflatePx).toDp(),
    )
}

@Composable
fun CoachmarkOverlay(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentStep by remember { mutableIntStateOf(1) }
    val anchors = LocalCoachmarkAnchors.current

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 200)),
        exit = fadeOut(animationSpec = tween(durationMillis = 200)),
        modifier = modifier,
    ) {
        val scene = SCENES.getOrNull(currentStep - 1) ?: return@AnimatedVisibility
        val density = LocalDensity.current
        var overlayOrigin by remember { mutableStateOf(Offset.Zero) }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { overlayOrigin = it.boundsInRoot().topLeft }
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
            val spotlight = when (currentStep) {
                1 -> anchors.sourceRail
                    ?.toSpotlightBounds(density, overlayOrigin, inflate = 4.dp)
                    ?: SpotlightBounds(
                        left = 14.dp,
                        top = 8.dp,
                        width = maxWidth - 28.dp,
                        height = 44.dp,
                    )

                2 -> anchors.feed?.let { feed ->
                    val size = 60.dp
                    with(density) {
                        SpotlightBounds(
                            left = (feed.center.x - overlayOrigin.x).toDp() - size / 2,
                            top = (feed.top - overlayOrigin.y).toDp() +
                                (feed.height.toDp() - size) / 2,
                            width = size,
                            height = size,
                        )
                    }
                } ?: SpotlightBounds(
                    left = (maxWidth - 60.dp) / 2,
                    top = 300.dp,
                    width = 60.dp,
                    height = 60.dp,
                )

                else -> anchors.firstCard
                    ?.toSpotlightBounds(density, overlayOrigin, inflate = 4.dp)
                    ?: SpotlightBounds(
                        left = 14.dp,
                        top = 200.dp,
                        width = maxWidth - 28.dp,
                        height = 120.dp,
                    )
            }

            Box(modifier = Modifier.offset(x = spotlight.left, y = spotlight.top)) {
                Spotlight(
                    width = spotlight.width,
                    height = spotlight.height,
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
                spotlight.top + spotlight.height + MaterialTheme.dimension.space16
            } else {
                (spotlight.top - 140.dp).coerceAtLeast(60.dp)
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
private fun Spotlight(width: Dp, height: Dp) {
    val mod = Modifier
        .width(width)
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
