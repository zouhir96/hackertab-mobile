package com.zrcoding.hackertab.bookmarks.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import com.zrcoding.hackertab.domain.models.Source
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.until
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toDuration

@Composable
fun BookmarksRoute(
    viewModel: BookmarksScreenViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    BookmarksScreen(
        viewState = viewState,
        onRemoveBookmark = viewModel::removeBookmark
    )
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.BOOKMARKS)
}

@Composable
fun BookmarksScreen(
    viewState: BookmarksScreenViewState,
    onRemoveBookmark: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            viewState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            viewState.bookmarks.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No bookmarks yet",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.large),
                    contentPadding = PaddingValues(bottom = MaterialTheme.dimension.extraBig)
                ) {
                    items(
                        items = viewState.bookmarks,
                        key = { it.id }
                    ) { bookmark ->
                        BookmarkItem(
                            bookmark = bookmark,
                            onRemoveBookmark = { onRemoveBookmark(bookmark.id) }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun BookmarkItem(
    bookmark: BookmarkedArticle,
    onRemoveBookmark: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { uriHandler.openUri(bookmark.url) }
            .padding(
                horizontal = MaterialTheme.dimension.default,
                vertical = MaterialTheme.dimension.medium
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(Source.valueOf(bookmark.source).icon),
            contentDescription = null,
            modifier = Modifier.size(MaterialTheme.dimension.extraBig)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.small)
        ) {
            Text(
                text = bookmark.title,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            TextWithStartIcon(
                text = bookmark.savedAt.toInstant(TimeZone.currentSystemDefault()).timeAgo(),
                icon = Res.drawable.ic_time_24,
            )
        }
        IconButton(
            onClick = onRemoveBookmark,
            modifier = Modifier.background(
                color = MaterialTheme.colors.secondary.copy(alpha = 0.5f),
                shape = CircleShape
            ).size(MaterialTheme.dimension.extraBig)
        ) {
            Icon(
                imageVector = Icons.Default.BookmarkRemove,
                contentDescription = "Remove bookmark",
                tint = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
private fun Instant.timeAgo(): String {
    val now = Clock.System.now()
    val duration = this.until(now, DateTimeUnit.MINUTE)
        .toDuration(DurationUnit.MINUTES)

    return when {
        duration.inWholeMinutes < 1 -> "Just now"
        duration.inWholeMinutes < 60 -> "${duration.inWholeMinutes}m ago"
        duration.inWholeHours < 24 -> "${duration.inWholeHours}h ago"
        duration.inWholeDays < 7 -> "${duration.inWholeDays}d ago"
        duration.inWholeDays < 30 -> "${duration.inWholeDays / 7}w ago"
        duration.inWholeDays < 365 -> "${duration.inWholeDays / 30}mo ago"
        else -> "${duration.inWholeDays / 365}y ago"
    }
}

