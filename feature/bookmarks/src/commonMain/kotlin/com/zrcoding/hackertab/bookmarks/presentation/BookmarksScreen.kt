package com.zrcoding.hackertab.bookmarks.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.adaptive.LocalIsTabletSize
import com.zrcoding.hackertab.design.components.HackertabAppBar
import com.zrcoding.hackertab.design.components.SectionHeader
import com.zrcoding.hackertab.design.components.cards.BookmarkCard
import com.zrcoding.hackertab.design.components.inputs.SegmentOption
import com.zrcoding.hackertab.design.components.inputs.SegmentedControl
import com.zrcoding.hackertab.design.components.states.EmptyState
import com.zrcoding.hackertab.design.components.states.EmptyStateCta
import com.zrcoding.hackertab.design.components.states.FeedLoadingSkeleton
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.bookmarks_empty_body
import com.zrcoding.hackertab.design.resources.bookmarks_empty_cta
import com.zrcoding.hackertab.design.resources.bookmarks_empty_title
import com.zrcoding.hackertab.design.resources.bookmarks_group_all
import com.zrcoding.hackertab.design.resources.bookmarks_group_by_date
import com.zrcoding.hackertab.design.resources.bookmarks_group_by_source
import com.zrcoding.hackertab.design.resources.bookmarks_search_cd
import com.zrcoding.hackertab.design.resources.bookmarks_subtitle
import com.zrcoding.hackertab.design.resources.bookmarks_title
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import com.zrcoding.hackertab.domain.models.ThemeMode
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.until
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@Composable
fun BookmarksRoute(
    onNavigateToWebView: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: BookmarksViewModel = org.koin.compose.viewmodel.koinViewModel(),
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    BookmarksScreen(
        viewState = viewState,
        onBookmarkClick = { bookmark ->
            viewModel.markRead(bookmark.id)
            onNavigateToWebView(bookmark.url)
        },
        onRemoveBookmark = viewModel::removeBookmark,
        onGroupByChanged = viewModel::onGroupByChanged,
        onSearchClick = onNavigateToSearch,
        onBrowsClick = onNavigateToHome,
    )

    val isTabletSize = LocalIsTabletSize.current
    LaunchedEffect(viewState.bookmarks, isTabletSize) {
        if (isTabletSize && viewState.bookmarks.isNotEmpty()) {
            onNavigateToWebView(viewState.bookmarks.first().url)
        }
    }
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.BOOKMARKS)
}

@Composable
fun BookmarksScreen(
    viewState: BookmarksViewState,
    onBookmarkClick: (BookmarkedArticle) -> Unit,
    onRemoveBookmark: (String) -> Unit,
    onGroupByChanged: (GroupBy) -> Unit,
    onSearchClick: () -> Unit,
    onBrowsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val groupOptions = persistentListOf(
        SegmentOption(id = GroupBy.ALL.name, label = stringResource(Res.string.bookmarks_group_all)),
        SegmentOption(id = GroupBy.BY_SOURCE.name, label = stringResource(Res.string.bookmarks_group_by_source)),
        SegmentOption(id = GroupBy.BY_DATE.name, label = stringResource(Res.string.bookmarks_group_by_date)),
    )
    Column(modifier = modifier.fillMaxSize()) {
        HackertabAppBar(
            title = stringResource(Res.string.bookmarks_title),
            subtitle = stringResource(
                Res.string.bookmarks_subtitle,
                viewState.totalCount,
                viewState.unreadCount,
            ),
            trailing = {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = stringResource(Res.string.bookmarks_search_cd),
                    )
                }
            },
        )

        SegmentedControl(
            options = groupOptions,
            selectedId = viewState.groupBy.name,
            onSelect = { id -> onGroupByChanged(GroupBy.valueOf(id)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.dimension.space16,
                    vertical = MaterialTheme.dimension.space12,
                ),
        )

        when {
            viewState.isLoading -> {
                FeedLoadingSkeleton(itemCount = 5)
            }

            viewState.bookmarks.isEmpty() -> {
                EmptyState(
                    icon = Icons.Outlined.BookmarkBorder,
                    title = stringResource(Res.string.bookmarks_empty_title),
                    body = stringResource(Res.string.bookmarks_empty_body),
                    primaryCta = EmptyStateCta(
                        label = stringResource(Res.string.bookmarks_empty_cta),
                        onClick = onBrowsClick,
                    ),
                )
            }

            else -> {
                BookmarksList(
                    viewState = viewState,
                    onBookmarkClick = onBookmarkClick,
                    onRemoveBookmark = onRemoveBookmark,
                )
            }
        }
    }
}

@Composable
private fun BookmarksList(
    viewState: BookmarksViewState,
    onBookmarkClick: (BookmarkedArticle) -> Unit,
    onRemoveBookmark: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp),
    ) {
        if (viewState.groupBy == GroupBy.ALL) {
            val items = viewState.groupedBookmarks.values
                .flatten()
                .toPersistentList()
            items(
                items = items,
                key = { it.id },
            ) { bookmark ->
                SwipeToDismissBookmark(
                    bookmark = bookmark,
                    onClick = { onBookmarkClick(bookmark) },
                    onRemove = { onRemoveBookmark(bookmark.id) },
                )
            }
        } else {
            viewState.groupedBookmarks.forEach { (groupKey, items) ->
                if (groupKey.label.isNotEmpty()) {
                    stickyHeader(key = "header_${groupKey.label}") {
                        SectionHeader(
                            label = groupKey.label.friendlyGroupLabel(),
                            count = items.size,
                            showCount = true,
                        )
                    }
                }
                items(
                    items = items,
                    key = { it.id },
                ) { bookmark ->
                    SwipeToDismissBookmark(
                        bookmark = bookmark,
                        onClick = { onBookmarkClick(bookmark) },
                        onRemove = { onRemoveBookmark(bookmark.id) },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToDismissBookmark(
    bookmark: BookmarkedArticle,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {
    key(bookmark.id) {
        val dismissState = rememberSwipeToDismissBoxState(
            confirmValueChange = { value ->
                if (value == SwipeToDismissBoxValue.EndToStart ||
                    value == SwipeToDismissBoxValue.StartToEnd
                ) {
                    onRemove()
                    true
                } else {
                    false
                }
            },
        )
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MaterialTheme.dimension.space16)
                )
            },
        ) {
            BookmarkCard(
                bookmark = bookmark,
                onClick = onClick,
                onRemove = onRemove,
                isUnread = !bookmark.read,
            )
        }
    }
}

private fun String.friendlyGroupLabel(): String {
    return this
}

@OptIn(ExperimentalTime::class)
internal fun LocalDateTime.timeAgoLabel(): String {
    val instant = toInstant(TimeZone.currentSystemDefault())
    val now = Clock.System.now()
    val minutes = instant.until(now, DateTimeUnit.MINUTE)
    val duration = minutes.toDuration(DurationUnit.MINUTES)
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

private fun fakeSavedAt(): LocalDateTime = LocalDateTime(2025, 5, 1, 10, 0, 0)

private val fakeBookmarks = persistentListOf(
    BookmarkedArticle(
        id = "1",
        title = "Kotlin 2.0 is out — what's new for multiplatform developers",
        url = "https://example.com/1",
        savedAt = fakeSavedAt(),
        source = "HACKER_NEWS",
        read = false,
    ),
    BookmarkedArticle(
        id = "2",
        title = "Jetpack Compose performance tips you might have missed",
        url = "https://example.com/2",
        savedAt = fakeSavedAt(),
        source = "DEVTO",
        read = true,
    ),
    BookmarkedArticle(
        id = "3",
        title = "Building offline-first apps with Room and KMM",
        url = "https://example.com/3",
        savedAt = fakeSavedAt(),
        source = "MEDIUM",
        read = false,
    ),
)

@Preview
@Composable
private fun BookmarksScreenLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        BookmarksScreen(
            viewState = BookmarksViewState(
                bookmarks = fakeBookmarks,
                isLoading = false,
                groupBy = GroupBy.ALL,
            ),
            onBookmarkClick = {},
            onRemoveBookmark = {},
            onGroupByChanged = {},
            onSearchClick = {},
            onBrowsClick = {},
        )
    }
}

@Preview
@Composable
private fun BookmarksScreenDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        BookmarksScreen(
            viewState = BookmarksViewState(
                bookmarks = fakeBookmarks,
                isLoading = false,
                groupBy = GroupBy.ALL,
            ),
            onBookmarkClick = {},
            onRemoveBookmark = {},
            onGroupByChanged = {},
            onSearchClick = {},
            onBrowsClick = {},
        )
    }
}

@Preview
@Composable
private fun BookmarksScreenEmptyLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        BookmarksScreen(
            viewState = BookmarksViewState(isLoading = false),
            onBookmarkClick = {},
            onRemoveBookmark = {},
            onGroupByChanged = {},
            onSearchClick = {},
            onBrowsClick = {},
        )
    }
}

@Preview
@Composable
private fun BookmarksScreenLoadingDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        BookmarksScreen(
            viewState = BookmarksViewState(isLoading = true),
            onBookmarkClick = {},
            onRemoveBookmark = {},
            onGroupByChanged = {},
            onSearchClick = {},
            onBrowsClick = {},
        )
    }
}
