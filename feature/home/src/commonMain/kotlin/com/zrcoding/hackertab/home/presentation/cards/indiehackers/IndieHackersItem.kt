package com.zrcoding.hackertab.home.presentation.cards.indiehackers

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.ic_arrow_drop_up
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.resources.score
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.IndieHackers
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
fun IndieHackersItem(
    indieHackers: IndieHackers,
    isBookmarked: Boolean,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    with(indieHackers) {
        SourceItemTemplate(
            title = title,
            primaryInfoSection = {
                TextWithStartIcon(
                    text = stringResource( Res.string.score, reactions),
                    textColor = Color(0xFF4799eb),
                    icon = Res.drawable.ic_arrow_drop_up,
                    tint = Color(0xFF4799eb)
                )
                TextWithStartIcon(
                    text = date.timeAgo(),
                    icon = Res.drawable.ic_time_24,
                )
                TextWithStartIcon(
                    text = stringResource( Res.string.comments, commentsCount),
                    icon = Res.drawable.ic_comment,
                )
            },
            isBookmarked = isBookmarked,
            onBookmarkClick = onBookmarkClick,
            onClick = onClick
        )
    }
}

@OptIn(ExperimentalTime::class)
@Preview()
@Composable
private fun IndieHackersItemPreview() {
    HackertabTheme {
        IndieHackersItem(
            indieHackers = IndieHackers(
                id = "fastidii",
                title = "Hackertab will pay someday",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 1349,
                reactions = 1634,
                url = "http://www.bing.com/search?q=dicam"
            ),
            isBookmarked = false,
            onClick = {},
            onBookmarkClick = {}
        )
    }
}