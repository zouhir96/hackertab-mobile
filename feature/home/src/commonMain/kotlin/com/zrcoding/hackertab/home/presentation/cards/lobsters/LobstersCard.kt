package com.zrcoding.hackertab.home.presentation.cards.lobsters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.ic_arrow_drop_up
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.resources.score
import com.zrcoding.hackertab.design.theme.Flamingo
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Lobster
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.CardWithoutTopicFilterTemplate
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LobstersCard(
    modifier: Modifier = Modifier,
    viewModel: LobstersViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    CardWithoutTopicFilterTemplate(
        modifier = modifier,
        cardUiState = viewState,
        sourceName = Source.LOBSTERS.label,
        sourceIcon = Source.LOBSTERS.icon,
        cardItem = { model ->
            LobstersItem(lobster = model)
        },
        onRetryBtnClick = viewModel::onRefresh
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LobstersItem(lobster: Lobster) {
    with(lobster) {
        SourceItemTemplate(
            title = title,
            url = url,
            primaryInfoSection = {
                TextWithStartIcon(
                    text = stringResource( Res.string.score, reactions),
                    textColor = Flamingo,
                    icon = Res.drawable.ic_arrow_drop_up,
                    tint = Flamingo
                )
                TextWithStartIcon(
                    text = date.timeAgo(),
                    icon = Res.drawable.ic_time_24,
                )
                val localUriHandler = LocalUriHandler.current
                TextWithStartIcon(
                    modifier = Modifier.clickable {
                        localUriHandler.openUri(commentsUrl)
                    },
                    text = stringResource( Res.string.comments, commentsCount),
                    textColor = Color(0xFF4799eb),
                    textDecoration = TextDecoration.Underline,
                    icon = Res.drawable.ic_comment,
                    tint = Color(0xFF4799eb)
                )
            }
        )
    }
}

@Preview()
@Composable
private fun LobstersItemPreview() {
    HackertabTheme {
        LobstersItem(
            lobster = Lobster(
                id = "feugiat",
                title = "XML based apps are worst",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 4849,
                reactions = 8948,
                url = "https://search.yahoo.com/search?p=habitasse",
                commentsUrl = "http://www.bing.com/search?q=brute"
            )
        )
    }
}