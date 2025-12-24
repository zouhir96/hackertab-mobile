package com.zrcoding.hackertab.home.presentation.cards.producthunt

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ProductHunt
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductHuntItem(
    product: ProductHunt,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.dimension.default,
                vertical = MaterialTheme.dimension.medium
            ),
        contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = MaterialTheme.dimension.default),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.large),
            verticalAlignment = Alignment.Top
        ) {
            with(product) {
                KamelImage(
                    { asyncPainterResource(data = imageUrl) },
                    modifier = Modifier.size(52.dp),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillBounds,
                    contentAlignment = Alignment.Center,
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.small)
                ) {
                    Text(
                        text = title,
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2
                    )
                    Text(
                        text = description,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.body2,
                        maxLines = 2,
                    )
                    TextWithStartIcon(
                        text = stringResource(Res.string.comments, commentsCount),
                        icon = Res.drawable.ic_comment,
                    )
                }
                Column(
                    modifier = Modifier
                        .border(
                            width = 0.3.dp,
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(
                            horizontal = MaterialTheme.dimension.small,
                            vertical = MaterialTheme.dimension.small
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropUp,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = "$reactions",
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium)
        ) {
            IconButton(
                onClick = onShareClick,
                modifier = Modifier
                    .size(MaterialTheme.dimension.extraBig)
                    .background(MaterialTheme.colors.secondary.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimension.big),
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share article",
                    tint = MaterialTheme.colors.onBackground
                )
            }
            IconButton(
                onClick = onBookmarkClick,
                modifier = Modifier
                    .size(MaterialTheme.dimension.extraBig)
                    .background(MaterialTheme.colors.secondary.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimension.big),
                    imageVector = if (product.bookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = if (product.bookmarked) "Remove bookmark" else "Add bookmark",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@Preview()
@Composable
private fun ProductHuntItemPreview() {
    HackertabTheme {
        ProductHuntItem(
            product = ProductHunt(
                id = "elementum",
                title = "Hackertab = All sources in one app",
                description = "Hackertab is the best product ranking",
                imageUrl = "http://www.bing.com/search?q=maecenas",
                commentsCount = 7250,
                reactions = 4827,
                url = "https://www.google.com/#q=vivendo",
                tags = listOf("Kotlin")
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {}
        )
    }
}