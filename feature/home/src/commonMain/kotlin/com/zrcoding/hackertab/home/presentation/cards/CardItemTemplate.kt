package com.zrcoding.hackertab.home.presentation.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
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
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
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
                    horizontal = MaterialTheme.dimension.space16,
                    vertical = MaterialTheme.dimension.space4
                ),
        ) {
            Text(
                text = title,
                color = titleColor,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )
            Spacer(modifier = modifier.height(MaterialTheme.dimension.space4))
            if (description.isNullOrBlank().not()) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                )
                Spacer(modifier = modifier.height(MaterialTheme.dimension.space8))
            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8)
            ) {
                primaryInfoSection()
            }
            Spacer(modifier = modifier.height(MaterialTheme.dimension.space4))
            tags?.let { CardItemTags(modifier = Modifier.fillMaxWidth(), tags = it) }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = MaterialTheme.dimension.space8,
                    bottom = MaterialTheme.dimension.space8
                ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space4)
        ) {
            IconButton(
                onClick = onShareClick,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f), CircleShape)
                    .size(MaterialTheme.dimension.space40)
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimension.space20),
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share article",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(
                onClick = onBookmarkClick,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f), CircleShape)
                    .size(MaterialTheme.dimension.space40)
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimension.space20),
                    imageVector = if (isBookmarked) Icons.Default.BookmarkAdded else Icons.Default.BookmarkBorder,
                    contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
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
            onShareClick = {},
            onClick = {},
            modifier = Modifier,
            tags = listOf("Java", "Kotlin", "JavaScript", "android development"),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardItemTags(
    modifier: Modifier = Modifier,
    tags: List<String>,
) {
    val isTagsBlank = tags.isEmpty() || (tags.size == 1 && tags.first().isBlank())
    if (isTagsBlank) return
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space2)
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
