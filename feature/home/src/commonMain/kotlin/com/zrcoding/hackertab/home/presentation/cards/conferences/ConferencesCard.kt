package com.zrcoding.hackertab.home.presentation.cards.conferences

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_location
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.usecases.BuildConferenceDisplayedDateUseCase
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterTemplate
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterUiEvents
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConferencesCard(
    modifier: Modifier = Modifier,
    viewModel: ConferencesCardViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    CardWithTopicFilterTemplate(
        modifier = modifier,
        cardUiState = viewState,
        sourceName = Source.CONFERENCES.label,
        sourceIcon = Source.CONFERENCES.icon,
        cardItem = { model ->
            ConferenceItem(conf = model)
        },
        onRetryBtnClick = { viewModel.onUiEvent(CardWithTopicFilterUiEvents.Refresh) },
        onTopicClick = { viewModel.onUiEvent(CardWithTopicFilterUiEvents.TopicClick(topic = it)) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConferenceItem(conf: Conference) {
    val date = BuildConferenceDisplayedDateUseCase(conf)
    val location = if (conf.online) {
        "\uD83C\uDF10 Online"
    } else {
        "${conf.country} ${conf.city.orEmpty()}"
    }
    SourceItemTemplate(
        title = conf.title.trim(),
        description = null,
        primaryInfoSection = {
            TextWithStartIcon(
                icon = Res.drawable.ic_location,
                text = location
            )
            TextWithStartIcon(
                icon = Res.drawable.ic_time_24,
                text = date
            )
        },
        url = conf.url,
        tags = listOf(conf.tag),
    )
}

@Preview()
@Composable
private fun ConferenceItemPreview() {
    HackertabTheme {
        ConferenceItem(
            conf = Conference(
                id = "aliquam",
                url = "https://www.google.com/#q=metus",
                title = "KotlinConf",
                startDate = LocalDateTime(2025, 10, 13, 0, 0, 0),
                endDate = LocalDateTime(2025, 10, 15, 0, 0, 0),
                tag = "Kotlin",
                online = true,
                city = "Berlin",
                country = "Germany"
            )
        )
    }
}