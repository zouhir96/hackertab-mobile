package com.zrcoding.hackertab.home.presentation.cards.mediun

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.claps
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.ic_claps
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Medium
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
fun MediumCard(
    modifier: Modifier = Modifier,
    viewModel: MediumCardViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    CardWithTopicFilterTemplate(
        modifier = modifier,
        cardUiState = viewState,
        sourceName = Source.MEDIUM.label,
        sourceIcon = Source.MEDIUM.icon,
        cardItem = { model ->
            MediumItem(medium = model)
        },
        onRetryBtnClick = { viewModel.onUiEvent(CardWithTopicFilterUiEvents.Refresh) },
        onTopicClick = { viewModel.onUiEvent(CardWithTopicFilterUiEvents.TopicClick(topic = it)) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MediumItem(medium: Medium) {
    with(medium) {
        SourceItemTemplate(
            title = title,
            url = url,
            primaryInfoSection = {
                TextWithStartIcon(
                    text = stringResource( Res.string.claps, claps),
                    icon = Res.drawable.ic_claps,
                    tint = MaterialTheme.colors.onBackground
                )
                TextWithStartIcon(
                    text = stringResource( Res.string.comments, commentsCount),
                    icon = Res.drawable.ic_comment,
                )
                TextWithStartIcon(
                    text = date.timeAgo(),
                    icon = Res.drawable.ic_time_24,
                )
            }
        )
    }
}

@Preview()
@Composable
private fun MediumItemPreview() {
    HackertabTheme {
        MediumItem(
            medium = Medium(
                id = "porttitor",
                title = "Coroutines explained in a simple way",
                date =Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 9783,
                claps = 9145,
                url = "https://duckduckgo.com/?q=minim"
            )
        )
    }
}