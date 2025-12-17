package com.zrcoding.hackertab.home.presentation.cards.conferences

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_location
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.usecases.BuildConferenceDisplayedDateUseCase
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConferenceItem(
    conf: Conference,
    isBookmarked: Boolean,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
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
        tags = listOf(conf.tag),
        isBookmarked = isBookmarked,
        onClick = onClick,
        onBookmarkClick = onBookmarkClick,
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
            ),
            isBookmarked = false,
            onClick = {},
            onBookmarkClick = {}
        )
    }
}