package com.zrcoding.hackertab.home.presentation.cards.producthunt

import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.cards.LaunchCard
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.ProductHunt
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Product Hunt launch card.
 * Delegates to [LaunchCard] from Wave 2C: thumbnail + title + tagline +
 * vertical upvote pill + comments. Image loaded via Kamel (Issue 9).
 */
@Composable
fun ProductHuntItem(
    product: ProductHunt,
    isRead: Boolean = false,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onLongClick: () -> Unit = {},
) {
    LaunchCard(
        product = product,
        timeAgo = "today",
        isBookmarked = product.bookmarked,
        isFresh = false,
        onClick = onClick,
        onLongClick = onLongClick,
        onBookmarkClick = onBookmarkClick,
        onMoreClick = onLongClick,
    )
}

@Preview
@Composable
private fun ProductHuntItemLightPreview() {
    HackertabTheme {
        ProductHuntItem(
            product = ProductHunt(
                id = "ph_1",
                title = "Hackertab",
                description = "All developer news sources in one app",
                imageUrl = "https://ph-files.imgix.net/logo.png",
                commentsCount = 42,
                reactions = 512,
                url = "https://www.producthunt.com/posts/hackertab",
                tags = listOf("productivity", "developer-tools"),
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}

@Preview
@Composable
private fun ProductHuntItemDarkPreview() {
    HackertabTheme {
        ProductHuntItem(
            product = ProductHunt(
                id = "ph_2",
                title = "Linear",
                description = "The issue tracker you'll actually enjoy using",
                imageUrl = "",
                commentsCount = 288,
                reactions = 1_200,
                url = "https://www.producthunt.com/posts/linear",
                tags = listOf("project-management"),
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
