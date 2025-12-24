package com.zrcoding.hackertab.home.presentation.cards.github

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.getTagColor
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.forks
import com.zrcoding.hackertab.design.resources.ic_baseline_fork
import com.zrcoding.hackertab.design.resources.ic_baseline_star
import com.zrcoding.hackertab.design.resources.ic_ellipse
import com.zrcoding.hackertab.design.resources.stars
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GithubItem(
    post: GithubRepo,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    SourceItemTemplate(
        title = "${post.owner}/${post.title}",
        description = post.description.trim().ifEmpty { null },
        primaryInfoSection = {
            TextWithStartIcon(
                icon = Res.drawable.ic_ellipse,
                tint = post.programmingLanguage.getTagColor(),
                text = post.programmingLanguage
            )
            TextWithStartIcon(
                icon = Res.drawable.ic_baseline_star,
                text = stringResource(Res.string.stars, post.stars)
            )

            TextWithStartIcon(
                icon = Res.drawable.ic_baseline_fork,
                text = stringResource(Res.string.forks, post.forks)
            )
        },
        titleColor = MaterialTheme.colors.primary,
        isBookmarked = post.bookmarked,
        onBookmarkClick = onBookmarkClick,
        onShareClick = onShareClick,
        onClick = onClick
    )
}

@Preview()
@Composable
private fun GithubItemPreview() {
    HackertabTheme {
        GithubItem(
            post = GithubRepo(
                id = "habeo",
                title = "Jetpack compose",
                description = "This is a fake repo for preview",
                owner = "Celina Wells",
                url = "https://www.google.com/#q=propriae",
                programmingLanguage = "Kotlin",
                stars = 20,
                forks = 15
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {}
        )
    }
}