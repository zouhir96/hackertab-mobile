package com.zrcoding.hackertab.home.presentation.cards.conferences

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_location
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConferenceItem(
    conf: Conference,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    SourceItemTemplate(
        title = conf.title.trim(),
        description = null,
        primaryInfoSection = {
            TextWithStartIcon(
                icon = Res.drawable.ic_location,
                text = getLocation(conf)
            )
            TextWithStartIcon(
                icon = Res.drawable.ic_time_24,
                text = getDate(conf)
            )
        },
        tags = conf.tags,
        isBookmarked = conf.bookmarked,
        onClick = onClick,
        onBookmarkClick = onBookmarkClick,
        onShareClick = onShareClick,
    )
}

private fun getDate(conf: Conference): String {
    val startDate = conf.startDate
    val endDate = conf.endDate
    return when {
        startDate == null -> ""
        endDate == null || startDate == endDate -> toMonthWithDay(startDate)
        else -> {
            val start = toMonthWithDay(startDate)
            if (startDate.month != endDate.month) {
                "$start - ${toMonthWithDay(endDate)}"
            } else {
                "$start - ${endDate.day}"
            }
        }
    }
}

private fun toMonthWithDay(date: LocalDate): String {
    val month = date.month.name.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
    return "$month ${date.day}"
}

private fun getLocation(conf: Conference): String {
    return if (conf.online) {
        "\uD83C\uDF10 Online"
    } else {
        "${conf.country} ${conf.city.orEmpty()}"
    }
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
                startDate = LocalDate(2025, 10, 10),
                endDate = LocalDate(2025, 10, 12),
                tags = listOf("Kotlin"),
                online = true,
                city = "Berlin",
                country = "Germany"
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {}
        )
    }
}