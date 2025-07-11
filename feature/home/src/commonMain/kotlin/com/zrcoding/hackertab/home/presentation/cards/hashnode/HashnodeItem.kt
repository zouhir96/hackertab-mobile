package com.zrcoding.hackertab.home.presentation.cards.hashnode

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
import com.zrcoding.hackertab.domain.models.Hashnode
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
fun HashnodeItem(hashnode: Hashnode) {
    with(hashnode) {
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
            url = url,
            tags = tags,
        )
    }
}

@OptIn(ExperimentalTime::class)
@Preview()
@Composable
private fun HashnodeItemPreview() {
    HackertabTheme {
        HashnodeItem(
            hashnode = Hashnode(
                id = "reque",
                title = "Just migrate your app to jetpack compose",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 4459,
                reactions = 4022,
                url = "http://www.bing.com/search?q=vocent",
                tags = listOf("Kotlin")
            )
        )
    }
}