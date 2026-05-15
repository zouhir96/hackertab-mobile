package com.zrcoding.hackertab.design.components.cards

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

// TODO Wave 7+: CMP has no cross-platform reduce-motion flag; degrade per-platform via expect/actual.
private const val IS_REDUCED_MOTION = false

/**
 * Hackertab v4 card chrome. Hosts every feed-card variant
 * (Article / Repo / Launch / Conference / Bookmark).
 *
 * Issue 6 (critique, secondary signal): when [isFresh], a 3dp brand-primary
 * vertical bar appears on the left edge as a quiet reinforcement. The
 * primary fresh indicator is the "NEW" pill rendered by [SourceTag] inside
 * the card content.
 *
 * Issue 7 (critique): when [isRead], the card dims to 0.78 alpha — NOT 0.65
 * (which the v3 implementation used and which the brief flagged as too
 * aggressive on muted body text).
 */
@Composable
fun CardShell(
    isFresh: Boolean = false,
    isRead: Boolean = false,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.98f else 1f,
        animationSpec = if (IS_REDUCED_MOTION) snap() else spring(
            stiffness = Spring.StiffnessHigh,
            dampingRatio = Spring.DampingRatioMediumBouncy,
        ),
        label = "card-press-scale",
    )

    Card(
        modifier = modifier
            .padding(horizontal = 14.dp)
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .scale(scale)
            .alpha(if (isRead) 0.78f else 1f)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
                onLongClick = onLongClick,
            ),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box {
            if (isFresh) {
                FreshAccentBar()
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 16.dp, top = 14.dp, bottom = 14.dp),
                content = content,
            )
        }
    }
}

@Composable
private fun FreshAccentBar() {
    Box(
        modifier = Modifier
            .padding(top = 14.dp)
            .width(3.dp)
            .height(16.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topEnd = 3.dp, bottomEnd = 3.dp),
            ),
    )
}
