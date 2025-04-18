package com.zrcoding.hackertab.home.presentation.cards.reddit

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_ellipse
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.resources.score
import com.zrcoding.hackertab.design.resources.subreddit
import com.zrcoding.hackertab.design.theme.Flamingo
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Reddit
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterTemplate
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterUiEvents
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RedditCard(
    modifier: Modifier = Modifier,
    viewModel: RedditCardViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    CardWithTopicFilterTemplate(
        modifier = modifier,
        cardUiState = viewState,
        sourceName = Source.REDDIT.label,
        sourceIcon = Source.REDDIT.icon,
        cardItem = { model ->
            RedditItem(reddit = model)
        },
        onRetryBtnClick = { viewModel.onUiEvent(CardWithTopicFilterUiEvents.Refresh) },
        onTopicClick = { viewModel.onUiEvent(CardWithTopicFilterUiEvents.TopicClick(topic = it)) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RedditItem(reddit: Reddit) {
    SourceItemTemplate(
        title = reddit.title,
        primaryInfoSection = {
            TextWithStartIcon(
                icon = Res.drawable.ic_time_24,
                text = reddit.date.timeAgo()
            )
            TextWithStartIcon(
                text = stringResource( Res.string.score, reddit.score),
                textColor = Flamingo,
                icon = Res.drawable.ic_ellipse,
                tint = Flamingo
            )
            TextWithStartIcon(
                text = stringResource( Res.string.comments, reddit.commentsCount),
                icon = Res.drawable.ic_comment
            )
        },
        url = reddit.url,
        tags = listOf(stringResource( Res.string.subreddit, reddit.subreddit))
    )
}

@Preview()
@Composable
fun RedditItemPreview() {
    HackertabTheme {
        RedditItem(
            reddit = Reddit(
                id = "similique",
                title = "React is the best web framework ever React is the best web framework ever",
                subreddit = "reactDevs",
                url = "https://www.google.com/#q=propriae",
                score = 118,
                commentsCount = 30,
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
    }
}