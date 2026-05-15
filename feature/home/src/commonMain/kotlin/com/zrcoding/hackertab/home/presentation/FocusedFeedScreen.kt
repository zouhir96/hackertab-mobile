package com.zrcoding.hackertab.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.adaptive.LocalIsTabletSize
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.home.presentation.utils.ShareData
import com.zrcoding.hackertab.home.presentation.utils.ShareManager
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

/**
 * Focused feed — same scaffold as [HomeRoute] but the [HomeViewModel] is
 * initialised with a specific [sourceId] active, so the SourceRail starts on
 * that pill and the body shows only that source's content.
 *
 * Registered in MainNavHost with a `sourceId` route argument.
 */
@Composable
fun FocusedFeedRoute(
    sourceId: String,
    onNavigateToWebView: (String) -> Unit,
    onNavigateToTopicsSettings: () -> Unit,
    onNavigateToSourcesSettings: () -> Unit,
    onNavigateToBookmarks: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val shareManager: ShareManager = koinInject()
    val snackbarHostState = remember { SnackbarHostState() }

    // Drive the ViewModel to the requested source on first composition
    LaunchedEffect(sourceId) {
        viewModel.onSourceSelected(sourceId)
    }

    val isTabletSize = LocalIsTabletSize.current
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(viewState.allArticles, isTabletSize) {
        if (isTabletSize && viewState.allArticles.isNotEmpty()) {
            onNavigateToWebView(viewState.allArticles.first().url)
        }
    }

    HomeScreen(
        viewState = viewState,
        snackbarHostState = snackbarHostState,
        onSourceSelected = viewModel::onSourceSelected,
        onTopicSelected = viewModel::onTopicSelected,
        onRefresh = viewModel::refresh,
        onNavigateToSourcesSettings = onNavigateToSourcesSettings,
        onNavigateToTopicsSettings = onNavigateToTopicsSettings,
        onCardClick = { article ->
            viewModel.markRead(article.id)
            onNavigateToWebView(article.url)
        },
        onBookmarkClick = viewModel::toggleBookmark,
        onShareClick = { article ->
            shareManager.share(ShareData(title = article.title, url = article.url))
        },
        onLongPress = viewModel::onLongPress,
    )

    val longPressed = viewState.longPressedArticle
    if (longPressed != null) {
        LongPressActionSheet(
            article = longPressed,
            isBookmarked = longPressed.bookmarked,
            onDismiss = viewModel::dismissLongPressSheet,
            onSave = { viewModel.toggleBookmark(longPressed) },
            onShare = {
                shareManager.share(ShareData(title = longPressed.title, url = longPressed.url))
            },
            onOpenInBrowser = {
                viewModel.markRead(longPressed.id)
                onNavigateToWebView(longPressed.url)
            },
        )
    }

    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.HOME)
}

// -------------------------------------------------------------------------
// Previews
// -------------------------------------------------------------------------

@Preview
@Composable
private fun FocusedFeedLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        HomeScreen(
            viewState = HomeViewState(
                activeSourceId = "github",
                enabledSources = persistentListOf(
                    Source.GITHUB, Source.HACKER_NEWS, Source.DEVTO,
                ),
                enabledTopics = persistentListOf(
                    com.zrcoding.hackertab.domain.models.Topic(
                        value = "kotlin", label = "Kotlin", category = "mobile"
                    ),
                ),
                selectedTopic = com.zrcoding.hackertab.domain.models.Topic(
                    value = "kotlin", label = "Kotlin", category = "mobile"
                ),
                isLoading = false,
                articlesByDay = kotlinx.collections.immutable.persistentMapOf(),
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onSourceSelected = {},
            onTopicSelected = {},
            onRefresh = {},
            onNavigateToSourcesSettings = {},
            onNavigateToTopicsSettings = {},
            onCardClick = {},
            onBookmarkClick = {},
            onShareClick = {},
            onLongPress = {},
        )
    }
}

@Preview
@Composable
private fun FocusedFeedDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        HomeScreen(
            viewState = HomeViewState(
                activeSourceId = "hackernews",
                enabledSources = persistentListOf(
                    Source.GITHUB, Source.HACKER_NEWS,
                ),
                enabledTopics = persistentListOf(),
                isLoading = false,
                articlesByDay = kotlinx.collections.immutable.persistentMapOf(),
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onSourceSelected = {},
            onTopicSelected = {},
            onRefresh = {},
            onNavigateToSourcesSettings = {},
            onNavigateToTopicsSettings = {},
            onCardClick = {},
            onBookmarkClick = {},
            onShareClick = {},
            onLongPress = {},
        )
    }
}
