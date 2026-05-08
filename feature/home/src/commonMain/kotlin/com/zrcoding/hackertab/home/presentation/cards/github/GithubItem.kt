package com.zrcoding.hackertab.home.presentation.cards.github

import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.cards.RepoCard
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.GithubRepo
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * GitHub trending-repo card.
 * Delegates entirely to [RepoCard] from Wave 2C.
 */
@Composable
fun GithubItem(
    post: GithubRepo,
    isRead: Boolean = false,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onLongClick: () -> Unit = {},
) {
    RepoCard(
        repo = post,
        timeAgo = "trending",
        isBookmarked = post.bookmarked,
        isFresh = false,
        onClick = onClick,
        onLongClick = onLongClick,
        onBookmarkClick = onBookmarkClick,
        onMoreClick = onLongClick,
    )
}

@Preview
@Composable
private fun GithubItemLightPreview() {
    HackertabTheme {
        GithubItem(
            post = GithubRepo(
                id = "gh_1",
                title = "compose-multiplatform",
                description = "Kotlin Multiplatform framework for cross-platform UI using Compose.",
                owner = "JetBrains",
                url = "https://github.com/JetBrains/compose-multiplatform",
                programmingLanguage = "Kotlin",
                stars = 14_800,
                forks = 1_050,
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}

@Preview
@Composable
private fun GithubItemDarkPreview() {
    HackertabTheme {
        GithubItem(
            post = GithubRepo(
                id = "gh_2",
                title = "okhttp",
                description = "Square's meticulous HTTP client for the JVM, Android, and GraalVM.",
                owner = "square",
                url = "https://github.com/square/okhttp",
                programmingLanguage = "Kotlin",
                stars = 45_000,
                forks = 9_300,
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
