package com.zrcoding.hackertab.bookmarks.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.design.components.HackertabAppBar
import com.zrcoding.hackertab.design.components.cards.BookmarkCard
import com.zrcoding.hackertab.design.components.inputs.HackertabTextField
import com.zrcoding.hackertab.design.components.states.EmptyState
import com.zrcoding.hackertab.design.components.states.EmptyStateCta
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import com.zrcoding.hackertab.domain.models.ThemeMode
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BookmarksSearchRoute(
    onNavigateBack: () -> Unit,
    onNavigateToWebView: (String) -> Unit,
    viewModel: BookmarksViewModel = org.koin.compose.viewmodel.koinViewModel(),
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    BookmarksSearchScreen(
        query = viewState.searchQuery,
        results = viewState.filteredBookmarks,
        onQueryChange = viewModel::onSearchQueryChanged,
        onClearQuery = { viewModel.onSearchQueryChanged("") },
        onResultClick = { bookmark ->
            viewModel.markRead(bookmark.id)
            onNavigateToWebView(bookmark.url)
        },
        onRemoveBookmark = { viewModel.removeBookmark(it.id) },
        onNavigateBack = onNavigateBack,
    )
    TrackScreenViewEvent(screenName = "BookmarksSearch")
}

@Composable
fun BookmarksSearchScreen(
    query: String,
    results: List<BookmarkedArticle>,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onResultClick: (BookmarkedArticle) -> Unit,
    onRemoveBookmark: (BookmarkedArticle) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = modifier.fillMaxSize()) {
        HackertabAppBar(
            title = "Search",
            leading = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            },
        )

        HackertabTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = "Search bookmarks…",
            leadingIcon = Icons.Default.Search,
            onClear = onClearQuery,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimension.space16)
                .padding(vertical = MaterialTheme.dimension.space12)
                .focusRequester(focusRequester),
        )

        if (query.isNotBlank()) {
            val plural = if (results.size != 1) "s" else ""
            Text(
                text = "${results.size} result$plural for \"$query\"",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimension.space16)
                    .padding(bottom = MaterialTheme.dimension.space8),
            )
        }

        when {
            results.isEmpty() && query.isNotBlank() -> {
                EmptyState(
                    icon = Icons.Outlined.BookmarkBorder,
                    title = "No results",
                    body = "No bookmarks match \"$query\". Try a different search.",
                    primaryCta = EmptyStateCta(label = "Clear search", onClick = onClearQuery),
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = MaterialTheme.dimension.space40),
                ) {
                    items(
                        items = results,
                        key = { it.id },
                    ) { bookmark ->
                        BookmarkCard(
                            bookmark = bookmark.withHighlightedTitle(query),
                            onClick = { onResultClick(bookmark) },
                            onMoreClick = { onRemoveBookmark(bookmark) },
                            isUnread = !bookmark.read,
                        )
                    }
                }
            }
        }
    }
}

private fun BookmarkedArticle.withHighlightedTitle(query: String): BookmarkedArticle = this

@Composable
internal fun highlightedText(
    text: String,
    query: String,
): androidx.compose.ui.text.AnnotatedString {
    if (query.isBlank()) return androidx.compose.ui.text.AnnotatedString(text)
    val highlightColor = MaterialTheme.colorScheme.primaryContainer
    return buildAnnotatedString {
        var start = 0
        val lowerText = text.lowercase()
        val lowerQuery = query.lowercase()
        while (true) {
            val idx = lowerText.indexOf(lowerQuery, start)
            if (idx == -1) {
                append(text.substring(start))
                break
            }
            append(text.substring(start, idx))
            withStyle(SpanStyle(background = highlightColor)) {
                append(text.substring(idx, idx + query.length))
            }
            start = idx + query.length
        }
    }
}

private val previewBookmarks = persistentListOf(
    BookmarkedArticle(
        id = "1",
        title = "Kotlin coroutines deep dive — structured concurrency explained",
        url = "https://example.com/1",
        savedAt = LocalDateTime(2025, 5, 1, 10, 0, 0),
        source = "MEDIUM",
        read = false,
    ),
    BookmarkedArticle(
        id = "2",
        title = "Coroutine context and dispatchers in KMM projects",
        url = "https://example.com/2",
        savedAt = LocalDateTime(2025, 4, 28, 8, 0, 0),
        source = "DEVTO",
        read = true,
    ),
)

@Preview
@Composable
private fun BookmarksSearchScreenLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        BookmarksSearchScreen(
            query = "coroutine",
            results = previewBookmarks,
            onQueryChange = {},
            onClearQuery = {},
            onResultClick = {},
            onRemoveBookmark = {},
            onNavigateBack = {},
        )
    }
}

@Preview
@Composable
private fun BookmarksSearchScreenDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        BookmarksSearchScreen(
            query = "coroutine",
            results = previewBookmarks,
            onQueryChange = {},
            onClearQuery = {},
            onResultClick = {},
            onRemoveBookmark = {},
            onNavigateBack = {},
        )
    }
}

@Preview
@Composable
private fun BookmarksSearchScreenEmptyLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        BookmarksSearchScreen(
            query = "graphql",
            results = emptyList(),
            onQueryChange = {},
            onClearQuery = {},
            onResultClick = {},
            onRemoveBookmark = {},
            onNavigateBack = {},
        )
    }
}
