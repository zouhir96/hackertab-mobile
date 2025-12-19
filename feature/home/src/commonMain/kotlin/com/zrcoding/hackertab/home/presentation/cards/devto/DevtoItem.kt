package com.zrcoding.hackertab.home.presentation.cards.devto

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_like
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.resources.reactions
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Devto
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DevtoItem(
    devto: Devto,
    isBookmarked: Boolean,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    with(devto) {
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
            tags = tags,
            isBookmarked = isBookmarked,
            onBookmarkClick = onBookmarkClick,
            onShareClick = onShareClick,
            onClick = onClick
        )
    }
}

@OptIn(ExperimentalTime::class)
@Preview()
@Composable
private fun DevtoItemPreview() {
    HackertabTheme {
        DevtoItem(
            devto = Devto(
                id = "convenire",
                title = "Jetpack compose is the best",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 7527,
                reactions = 6147,
                url = "https://search.yahoo.com/search?p=mnesarchum",
                tags = listOf("Kotlin")
            ),
            isBookmarked = false,
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {}
        )
    }
}