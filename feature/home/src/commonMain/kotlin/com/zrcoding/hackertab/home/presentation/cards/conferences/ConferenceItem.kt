package com.zrcoding.hackertab.home.presentation.cards.conferences

import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.cards.ConferenceCard
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Conference
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Upcoming-events / conferences card.
 * Delegates to [ConferenceCard] from Wave 2C which renders calendar-block date,
 * location row, and tags.
 */
@Composable
fun ConferenceItem(
    conf: Conference,
    isRead: Boolean = false,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onLongClick: () -> Unit = {},
) {
    ConferenceCard(
        conference = conf,
        timeAgo = "",
        isBookmarked = conf.bookmarked,
        isFresh = false,
        onClick = onClick,
        onLongClick = onLongClick,
        onBookmarkClick = onBookmarkClick,
        onMoreClick = onLongClick,
    )
}

@Preview
@Composable
private fun ConferenceItemLightPreview() {
    HackertabTheme {
        ConferenceItem(
            conf = Conference(
                id = "conf_1",
                url = "https://kotlinconf.com",
                title = "KotlinConf 2026",
                startDate = LocalDate(2026, 5, 22),
                endDate = LocalDate(2026, 5, 24),
                tags = listOf("kotlin", "jvm", "multiplatform"),
                online = false,
                city = "Copenhagen",
                country = "Denmark",
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}

@Preview
@Composable
private fun ConferenceItemDarkPreview() {
    HackertabTheme {
        ConferenceItem(
            conf = Conference(
                id = "conf_2",
                url = "https://droidcon.com",
                title = "Droidcon SF 2026",
                startDate = LocalDate(2026, 6, 10),
                endDate = null,
                tags = listOf("android", "compose"),
                online = false,
                city = "San Francisco",
                country = "US",
            ),
            onClick = {},
            onBookmarkClick = {},
            onShareClick = {},
        )
    }
}
