package com.zrcoding.hackertab.home.presentation.cards.freecodecamp

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.FreeCodeCamp
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterTemplate
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterUiEvents
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FreeCodeCampCard(
    modifier: Modifier = Modifier,
    viewModel: FreeCodeCampCardViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    CardWithTopicFilterTemplate(
        modifier = modifier,
        cardUiState = viewState,
        sourceName = Source.FREE_CODE_CAMP.label,
        sourceIcon = Source.FREE_CODE_CAMP.icon,
        cardItem = { model ->
            FreeCodeCampItem(post = model)
        },
        onRetryBtnClick = { viewModel.onUiEvent(CardWithTopicFilterUiEvents.Refresh) },
        onTopicClick = { viewModel.onUiEvent(CardWithTopicFilterUiEvents.TopicClick(topic = it)) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FreeCodeCampItem(post: FreeCodeCamp) {
    SourceItemTemplate(
        title = post.title.trim(),
        description = null,
        primaryInfoSection = {
            TextWithStartIcon(
                icon = Res.drawable.ic_time_24,
                text = post.isoDate.timeAgo()
            )
        },
        url = post.url,
        tags = post.categories,
    )
}

@Preview()
@Composable
private fun FreeCodeCampItemPreview() {
    HackertabTheme {
        FreeCodeCampItem(
            post = FreeCodeCamp(
                id = "similique",
                title = "React is the best web framework ever React is the best web framework ever",
                url = "https://www.google.com/#q=propriae",
                isoDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                categories = listOf()
            )
        )
    }
}