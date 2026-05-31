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
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

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

@Composable
fun FeedLoadingSkeleton(
    modifier: Modifier = Modifier,
    itemCount: Int = 4,
) {
    val border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    val cardShape = RoundedCornerShape(MaterialTheme.dimension.space16)

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
                    .padding(horizontal = MaterialTheme.dimension.space12)
                    .padding(bottom = MaterialTheme.dimension.space8)
                    .background(MaterialTheme.colorScheme.surface, cardShape)
                    .border(border = border, shape = cardShape)
                    .padding(horizontal = MaterialTheme.dimension.space12, vertical = MaterialTheme.dimension.space12),
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
                    ) {
                        ShimmerBox(
                            modifier = Modifier.size(MaterialTheme.dimension.space16),
                            shape = RoundedCornerShape(MaterialTheme.dimension.space4),
                        )
                        ShimmerBox(
                            modifier = Modifier
                                .width(90.dp)
                                .height(MaterialTheme.dimension.space8),
                        )
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))

                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(titleFraction)
                            .height(MaterialTheme.dimension.space12),
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimension.space6))
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(subtitleFraction)
                            .height(MaterialTheme.dimension.space12),
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.dimension.space12))

                    Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8)) {
                        ShimmerBox(
                            modifier = Modifier
                                .width(60.dp)
                                .height(MaterialTheme.dimension.space8),
                        )
                        ShimmerBox(
                            modifier = Modifier
                                .width(50.dp)
                                .height(MaterialTheme.dimension.space8),
                        )
                        ShimmerBox(
                            modifier = Modifier
                                .width(MaterialTheme.dimension.space40)
                                .height(MaterialTheme.dimension.space8),
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
