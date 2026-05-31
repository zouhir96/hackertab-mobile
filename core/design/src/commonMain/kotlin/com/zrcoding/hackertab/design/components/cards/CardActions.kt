package com.zrcoding.hackertab.design.components.cards

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.zrcoding.hackertab.design.theme.HackertabMotion
import com.zrcoding.hackertab.design.theme.dimension

@Composable
fun CardActions(
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    onMoreClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space2),
    ) {
        BookmarkButton(isBookmarked = isBookmarked, onClick = onBookmarkClick)
        IconButton(
            onClick = onMoreClick,
            modifier = Modifier.size(MaterialTheme.dimension.space48),
        ) {
            Icon(
                imageVector = Icons.Outlined.MoreHoriz,
                contentDescription = "More actions",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(MaterialTheme.dimension.space16),
            )
        }
    }
}

@Composable
private fun BookmarkButton(isBookmarked: Boolean, onClick: () -> Unit) {
    val pulse = remember { Animatable(1f) }
    LaunchedEffect(isBookmarked) {
        if (isBookmarked) {
            pulse.animateTo(1.15f, animationSpec = tween(120, easing = HackertabMotion.emphasizedEasing))
            pulse.animateTo(1f, animationSpec = tween(120, easing = HackertabMotion.emphasizedEasing))
        }
    }
    val tint by animateColorAsState(
        targetValue = if (isBookmarked) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "bookmark-tint",
    )

    IconButton(
        onClick = onClick,
        modifier = Modifier.size(MaterialTheme.dimension.space48),
    ) {
        Icon(
            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
            contentDescription = if (isBookmarked) "Remove bookmark" else "Bookmark",
            tint = tint,
            modifier = Modifier
                .size(MaterialTheme.dimension.space16)
                .scale(pulse.value),
        )
    }
}
