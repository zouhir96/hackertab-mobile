package com.zrcoding.hackertab.home.presentation.cards.github

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.getTagColor
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.forks
import com.zrcoding.hackertab.design.resources.ic_baseline_fork
import com.zrcoding.hackertab.design.resources.ic_baseline_star
import com.zrcoding.hackertab.design.resources.ic_ellipse
import com.zrcoding.hackertab.design.resources.stars
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.TextLink
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.CardTemplate
import com.zrcoding.hackertab.home.presentation.cards.CardUiEvents
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GithubCard(
    modifier: Modifier = Modifier,
    viewModel: GithubCardViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    CardTemplate(
        modifier = modifier,
        cardUiState = viewState,
        sourceName = Source.GITHUB.label,
        sourceIcon = Source.GITHUB.icon,
        cardItem = { model ->
            GithubItem(post = model)
        },
        onRetryBtnClick = { viewModel.onUiEvent(CardUiEvents.Refresh) },
        onTopicClick = { viewModel.onUiEvent(CardUiEvents.TopicClick(topic = it)) }
    )
}

@Composable
fun GithubItem(post: GithubRepo) {
    SourceItemTemplate(
        title = "${post.owner}/${post.name}",
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
        url = post.url,
        titleColor = TextLink,
    )
}

@Preview()
@Composable
private fun GithubItemPreview() {
    HackertabTheme {
        GithubItem(
            post = GithubRepo(
                id = "habeo",
                name = "Jetpack compose",
                description = "This is a fake repo for preview",
                owner = "Celina Wells",
                url = "https://www.google.com/#q=propriae",
                programmingLanguage = "Kotlin",
                stars = 20,
                forks = 15
            )
        )
    }
}