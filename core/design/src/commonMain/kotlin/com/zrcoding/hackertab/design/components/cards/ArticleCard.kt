package com.zrcoding.hackertab.design.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import com.zrcoding.hackertab.design.components.SourceTag
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Source
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticleCard(
    article: Article,
    isBookmarked: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onMoreClick: () -> Unit = {},
    metaContent: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val source = requireNotNull(article.source) { "ArticleCard requires Article.source" }
    CardShell(
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = "${article.title}. ${source.label}"
        },
    ) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
        )
        if (article.tags.isNotEmpty()) {
            Spacer(Modifier.height(MaterialTheme.dimension.space8))
            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides MaterialTheme.dimension.none) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space6)) {
                    article.tags.take(6).forEach { tag ->
                        Text(
                            text = "#$tag",
                            style = codeSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .padding(bottom = MaterialTheme.dimension.space4)
                                .clip(RoundedCornerShape(MaterialTheme.dimension.space6))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = MaterialTheme.dimension.space6, vertical = MaterialTheme.dimension.space2),
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
        ) {
            metaContent()
            Spacer(Modifier.weight(1f))
            CardActions(
                isBookmarked = isBookmarked,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onMoreClick,
            )
        }
    }
}

@Preview
@Composable
private fun ArticleCardPreview(){
    HackertabTheme {
        ArticleCard(
            article = Article(
                id = "populo",
                title = "constituto",
                url = "http://www.bing.com/search?q=voluptaria",
                bookmarked = false,
                publishedAt = LocalDateTime(2020, 12,12, 12,12, 12),
                commentsCount = 1602,
                reactions = 7139,
                tags = listOf(),
                canonicalUrl = "https://duckduckgo.com/?q=definitionem",
                imageUrl = "https://search.yahoo.com/search?p=ridiculus",
                source = Source.DEVTO
            ),
            isBookmarked = false,
            onClick = {},
            onLongClick = {},
            onBookmarkClick = {},
            onMoreClick = {},
            metaContent = {},
        )
    }
}
