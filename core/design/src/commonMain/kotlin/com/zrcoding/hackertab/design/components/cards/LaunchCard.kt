package com.zrcoding.hackertab.design.components.cards

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.SourceTag
import com.zrcoding.hackertab.design.theme.codeMedium
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Source
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun LaunchCard(
    product: ProductHunt,
    timeAgo: String,
    isBookmarked: Boolean,
    isFresh: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onMoreClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    CardShell(
        isFresh = isFresh,
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = "${product.title}. ${product.description}. " +
                "${product.reactions} upvotes, ${product.commentsCount} comments. $timeAgo."
        },
    ) {
        SourceTag(source = Source.PRODUCTHUNT, timeAgo = timeAgo, isFresh = isFresh)
        Spacer(Modifier.height(MaterialTheme.dimension.space12))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(MaterialTheme.dimension.space12))
                    .background(
                        Brush.linearGradient(listOf(Color(0xFFFF7A59), Color(0xFFDA552F)))
                    ),
                contentAlignment = Alignment.Center,
            ) {
                KamelImage(
                    resource = { asyncPainterResource(product.imageUrl) },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    onLoading = {
                    },
                    onFailure = {
                        Text(
                            text = product.title.take(1).uppercase(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.W700,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space2),
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Column(
                modifier = Modifier
                    .width(MaterialTheme.dimension.space48)
                    .clip(RoundedCornerShape(MaterialTheme.dimension.space12))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(MaterialTheme.dimension.space12)
                    )
                    .padding(vertical = MaterialTheme.dimension.space4),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowUpward,
                    contentDescription = "upvotes",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(MaterialTheme.dimension.space16),
                )
                Text(
                    text = "${product.reactions}",
                    style = codeMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.W700,
                )
            }
        }
        Spacer(Modifier.height(MaterialTheme.dimension.space12))
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(Modifier.height(MaterialTheme.dimension.space8))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space4),
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = "comments",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(MaterialTheme.dimension.space12),
                )
                Text(
                    text = "${product.commentsCount}",
                    style = codeSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(Modifier.weight(1f))
            CardActions(
                isBookmarked = isBookmarked,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onMoreClick,
            )
        }
    }
}
