package com.zrcoding.hackertab.home.presentation.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.getTagColor
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_ellipse
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SourceItemTemplate(
    title: String,
    isBookmarked: Boolean,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colors.onBackground,
    description: String? = null,
    primaryInfoSection: @Composable FlowRowScope.() -> Unit,
    tags: List<String>? = null,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.dimension.default,
                    vertical = MaterialTheme.dimension.small
                ),
        ) {
            Text(
                text = title,
                color = titleColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2
            )
            Spacer(modifier = modifier.height(MaterialTheme.dimension.small))
            if (description.isNullOrBlank().not()) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = description,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                )
                Spacer(modifier = modifier.height(MaterialTheme.dimension.medium))
            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium)
            ) {
                primaryInfoSection()
            }
            Spacer(modifier = modifier.height(MaterialTheme.dimension.small))
            tags?.let { CardItemTags(modifier = Modifier.fillMaxWidth(), tags = it) }
        }
        IconButton(
            onClick = onBookmarkClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = MaterialTheme.dimension.medium,
                    bottom = MaterialTheme.dimension.medium
                )
                .background(MaterialTheme.colors.secondary.copy(alpha = 0.5f), CircleShape)
                .size(MaterialTheme.dimension.extraBig)
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimension.big),
                imageVector = if (isBookmarked) Icons.Default.BookmarkAdded else Icons.Default.BookmarkBorder,
                contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark",
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview()
@Composable
fun SourceItemTemplatePreview() {
    HackertabTheme {
        SourceItemTemplate(
            title = "HackerNews",
            description = "this is a lorem ipsum test",
            primaryInfoSection = {
                TextWithStartIcon(text = "1h ago", icon = Res.drawable.ic_time_24)
            },
            isBookmarked = false,
            onBookmarkClick = {},
            onClick = {},
            modifier = Modifier,
            tags = listOf("Java", "Kotlin", "JavaScript", "android development"),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun CardItemTags(
    modifier: Modifier = Modifier,
    tags: List<String>,
) {
    val isTagsBlank = tags.isEmpty() || (tags.size == 1 && tags.first().isBlank())
    if (isTagsBlank) return
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        FlowRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.tiny)
        ) {
            tags.forEach {
                val color = it.getTagColor()
                TextWithStartIcon(
                    text = it,
                    icon = Res.drawable.ic_ellipse,
                    tint = color
                )
            }
        }
    }
}