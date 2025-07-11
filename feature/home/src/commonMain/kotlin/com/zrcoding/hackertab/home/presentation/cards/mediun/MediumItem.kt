package com.zrcoding.hackertab.home.presentation.cards.mediun

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.claps
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.ic_claps
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Medium
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

@OptIn(ExperimentalTime::class)
@Preview()
@Composable
private fun MediumItemPreview() {
    HackertabTheme {
        MediumItem(
            medium = Medium(
                id = "porttitor",
                title = "Coroutines explained in a simple way",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 9783,
                claps = 9145,
                url = "https://duckduckgo.com/?q=minim"
            )
        )
    }
}