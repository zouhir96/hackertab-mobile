package com.zrcoding.hackertab.home.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.components.sheets.ActionRow
import com.zrcoding.hackertab.design.components.sheets.ActionSheet
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.action_open_in_browser
import com.zrcoding.hackertab.design.resources.action_share
import com.zrcoding.hackertab.domain.models.BaseArticle
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun LongPressActionSheet(
    article: BaseArticle,
    isBookmarked: Boolean,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onShare: () -> Unit,
    onOpenInBrowser: () -> Unit,
) {
    ActionSheet(
        visible = true,
        onDismiss = onDismiss,
        title = article.title.take(60).let { if (it.length < article.title.length) "$it…" else it },
        actions = persistentListOf(
            ActionRow(
                id = "save",
                icon = if (isBookmarked) Icons.Filled.BookmarkAdded else Icons.Filled.BookmarkBorder,
                label = if (isBookmarked) "Remove bookmark" else "Save",
                onClick = onSave,
            ),
            ActionRow(
                id = "share",
                icon = Icons.Filled.Share,
                label = stringResource(Res.string.action_share),
                onClick = onShare,
            ),
            ActionRow(
                id = "open_browser",
                icon = Icons.Filled.OpenInBrowser,
                label = stringResource(Res.string.action_open_in_browser),
                onClick = onOpenInBrowser,
            ),
        ),
    )
}
