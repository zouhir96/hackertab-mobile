package com.zrcoding.hackertab.home.presentation.cards.freecodecamp

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.FreeCodeCamp
import com.zrcoding.hackertab.home.presentation.cards.SourceItemTemplate
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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

@OptIn(ExperimentalTime::class)
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