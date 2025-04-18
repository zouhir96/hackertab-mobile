package com.zrcoding.hackertab.home.presentation.cards.hashnode

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_like
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.resources.reactions
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Hashnode
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
fun HashnodeCard(
    modifier: Modifier = Modifier,
    viewModel: HashnodeCardViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    CardWithTopicFilterTemplate(
        modifier = modifier,
        cardUiState = viewState,
        sourceName = Source.HASH_NODE.label,
        sourceIcon = Source.HASH_NODE.icon,
        cardItem = { model ->
            HashnodeItem(hashnode = model)
        },
        onRetryBtnClick = { viewModel.onUiEvent(CardWithTopicFilterUiEvents.Refresh) },
        onTopicClick = { viewModel.onUiEvent(CardWithTopicFilterUiEvents.TopicClick(topic = it)) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HashnodeItem(hashnode: Hashnode) {
    with(hashnode) {
        SourceItemTemplate(
            title = title.trim(),
            description = null,
            primaryInfoSection = {
                TextWithStartIcon(
                    text = date.timeAgo(),
                    icon = Res.drawable.ic_time_24,
                )
                TextWithStartIcon(
                    text = stringResource( Res.string.comments, commentsCount),
                    icon = Res.drawable.ic_comment,
                )
                TextWithStartIcon(
                    text = stringResource( Res.string.reactions, reactions),
                    icon = Res.drawable.ic_like
                )
            },
            url = url,
            tags = tags,
        )
    }
}

@Preview()
@Composable
private fun HashnodeItemPreview() {
    HackertabTheme {
        HashnodeItem(
            hashnode = Hashnode(
                id = "reque",
                title = "Just migrate your app to jetpack compose",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 4459,
                reactions = 4022,
                url = "http://www.bing.com/search?q=vocent",
                tags = listOf("Kotlin")
            )
        )
    }
}