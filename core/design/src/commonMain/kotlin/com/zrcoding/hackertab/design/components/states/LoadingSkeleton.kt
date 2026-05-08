package com.zrcoding.hackertab.design.components.states

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Hackertab v4 ShimmerBox — primitive shimmering rectangle used by skeleton
 * placeholders.
 *
 * Animates a horizontal gradient sweep across `surfaceVariant` → `surface`
 * → `surfaceVariant` over 1500ms.
 *
 * NOTE: Compose Multiplatform does not yet expose a stable cross-platform
 * `LocalAccessibilityManager.isReduceMotionEnabled` reading. Reduced-motion
 * is honoured best-effort on platforms that pause infinite animations when
 * the OS-level setting is on. We document this limitation here so call-sites
 * can guard the skeleton with their own preference where required.
 */
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translate by transition.animateFloat(
        initialValue = -2 * SHIMMER_WIDTH_PX,
        targetValue = 2 * SHIMMER_WIDTH_PX,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerTranslate",
    )

    val base = MaterialTheme.colorScheme.surfaceVariant
    val highlight = MaterialTheme.colorScheme.surface
    val brush = Brush.linearGradient(
        colors = listOf(base, highlight, base),
        start = Offset(translate, 0f),
        end = Offset(translate + SHIMMER_WIDTH_PX, 0f),
    )

    Box(modifier = modifier.background(brush = brush, shape = shape))
}

private const val SHIMMER_WIDTH_PX: Float = 320f

/**
 * Hackertab v4 FeedLoadingSkeleton — renders [itemCount] card placeholders that
 * mimic the rhythm of the real `ArticleCard`.
 *
 * Visual reference: `design/project/components/Screens.jsx` lines 727-752
 * (`LoadingState`).
 */
@Composable
fun FeedLoadingSkeleton(
    modifier: Modifier = Modifier,
    itemCount: Int = 4,
) {
    val border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    val cardShape = RoundedCornerShape(16.dp)

    // Match Screens.jsx LoadingState — 90/75% widths varied per item.
    val titleWidthFractions = listOf(0.90f, 0.85f, 0.80f, 0.75f, 0.70f, 0.65f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                liveRegion = LiveRegionMode.Polite
                contentDescription = "Loading feed"
            },
    ) {
        for (i in 0 until itemCount) {
            val titleFraction = titleWidthFractions[i % titleWidthFractions.size]
            val subtitleFraction = (titleFraction - 0.10f).coerceAtLeast(0.50f)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .padding(bottom = 8.dp)
                    .background(MaterialTheme.colorScheme.surface, cardShape)
                    .border(border = border, shape = cardShape)
                    .padding(horizontal = 14.dp, vertical = 14.dp),
            ) {
                Column {
                    // Source-tag rhythm: 18dp icon shimmer + 90dp label shimmer.
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        ShimmerBox(
                            modifier = Modifier.size(18.dp),
                            shape = RoundedCornerShape(5.dp),
                        )
                        ShimmerBox(
                            modifier = Modifier
                                .width(90.dp)
                                .height(10.dp),
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Two title lines.
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(titleFraction)
                            .height(14.dp),
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(subtitleFraction)
                            .height(14.dp),
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Meta row: 2-3 small shimmer rectangles.
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ShimmerBox(
                            modifier = Modifier
                                .width(60.dp)
                                .height(10.dp),
                        )
                        ShimmerBox(
                            modifier = Modifier
                                .width(50.dp)
                                .height(10.dp),
                        )
                        ShimmerBox(
                            modifier = Modifier
                                .width(40.dp)
                                .height(10.dp),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FeedLoadingSkeletonLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        FeedLoadingSkeleton(itemCount = 3)
    }
}

@Preview
@Composable
private fun FeedLoadingSkeletonDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        FeedLoadingSkeleton(itemCount = 3)
    }
}
