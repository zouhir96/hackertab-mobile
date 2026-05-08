package com.zrcoding.hackertab.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.adaptive.LocalIsTabletSize
import com.zrcoding.hackertab.design.components.HackertabAppBar
import com.zrcoding.hackertab.design.components.SectionHeader
import com.zrcoding.hackertab.design.components.SourceRail
import com.zrcoding.hackertab.design.components.TopicChipStrip
import com.zrcoding.hackertab.design.components.states.EmptyState
import com.zrcoding.hackertab.design.components.states.EmptyStateCta
import com.zrcoding.hackertab.design.components.states.ErrorState
import com.zrcoding.hackertab.design.components.states.FeedLoadingSkeleton
import com.zrcoding.hackertab.design.components.states.HackertabSnackbarHost
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.home.presentation.cards.conferences.ConferenceItem
import com.zrcoding.hackertab.home.presentation.cards.devto.DevtoItem
import com.zrcoding.hackertab.home.presentation.cards.freecodecamp.FreeCodeCampItem
import com.zrcoding.hackertab.home.presentation.cards.github.GithubItem
import com.zrcoding.hackertab.home.presentation.cards.hackernews.HackerNewsItem
import com.zrcoding.hackertab.home.presentation.cards.hackernoon.HackerNoonItem
import com.zrcoding.hackertab.home.presentation.cards.hashnode.HashnodeItem
import com.zrcoding.hackertab.home.presentation.cards.indiehackers.IndieHackersItem
import com.zrcoding.hackertab.home.presentation.cards.lobsters.LobstersItem
import com.zrcoding.hackertab.home.presentation.cards.mediun.MediumItem
import com.zrcoding.hackertab.home.presentation.cards.producthunt.ProductHuntItem
import com.zrcoding.hackertab.home.presentation.cards.reddit.RedditItem
import com.zrcoding.hackertab.home.presentation.utils.ShareData
import com.zrcoding.hackertab.home.presentation.utils.ShareManager
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

// TODO Wave 4: register HomeRoute + FocusedFeedRoute + WebViewRoute + LongPressActionSheet
//  in MainNavHost. For now these routes use the existing navigation wiring.

@Composable
fun HomeRoute(
    onNavigateToWebView: (String) -> Unit,
    onNavigateToTopicsSettings: () -> Unit,
    onNavigateToSourcesSettings: () -> Unit,
    onNavigateToBookmarks: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val shareManager: ShareManager = koinInject()
    val snackbarHostState = remember { SnackbarHostState() }

    // Auto-select first article on tablets
    val isTabletSize = LocalIsTabletSize.current
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

    // Long-press action sheet
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    viewState: HomeViewState,
    snackbarHostState: SnackbarHostState,
    onSourceSelected: (String) -> Unit,
    onTopicSelected: (com.zrcoding.hackertab.domain.models.Topic) -> Unit,
    onRefresh: () -> Unit,
    onNavigateToSourcesSettings: () -> Unit,
    onNavigateToTopicsSettings: () -> Unit,
    onCardClick: (BaseArticle) -> Unit,
    onBookmarkClick: (BaseArticle) -> Unit,
    onShareClick: (BaseArticle) -> Unit,
    onLongPress: (BaseArticle) -> Unit,
) {
    val pullRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            HackertabAppBar(
                wordmark = true,
                trailing = {
                    // Issue 14: no search icon in the feed AppBar
                    IconButton(onClick = onRefresh) {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = "Refresh feed",
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
            )
        },
        snackbarHost = { HackertabSnackbarHost(hostState = snackbarHostState) },
        // TODO Wave 4: bottomBar = { HackertabBottomNav(...) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // Source rail — Issue 11: plain "All" (no ★), SourceRail handles it
            SourceRail(
                sources = viewState.enabledSources,
                activeSourceId = viewState.activeSourceId,
                onSelect = onSourceSelected,
                modifier = Modifier.fillMaxWidth(),
            )

            // Topic chip strip — shown for "All" mode and for filterable sources
            if (viewState.showTopicStrip && viewState.enabledTopics.isNotEmpty()) {
                TopicChipStrip(
                    topics = viewState.enabledTopics,
                    activeTopicId = viewState.selectedTopic?.value,
                    onSelect = onTopicSelected,
                    canAddTopic = viewState.canAddTopic,
                    onAddTopic = onNavigateToTopicsSettings,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(4.dp))
            }

            // Body
            PullToRefreshBox(
                isRefreshing = viewState.isLoading,
                onRefresh = onRefresh,
                state = pullRefreshState,
                modifier = Modifier.fillMaxSize(),
            ) {
                when {
                    viewState.isLoading -> {
                        FeedLoadingSkeleton(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 8.dp),
                        )
                    }

                    viewState.error != null -> {
                        ErrorState(
                            onRetry = onRefresh,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    viewState.enabledSources.isEmpty() -> {
                        EmptyState(
                            icon = Icons.Outlined.Layers,
                            title = "No sources selected",
                            body = "You haven't followed any source yet. Add some to start your feed.",
                            primaryCta = EmptyStateCta(label = "Add sources") {
                                onNavigateToSourcesSettings()
                            },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    viewState.articlesByDay.isEmpty() -> {
                        EmptyState(
                            icon = Icons.Outlined.Layers,
                            title = "Nothing here yet",
                            body = "No items match your current filter. Try clearing the filter or switching to a different source.",
                            primaryCta = EmptyStateCta(label = "See all sources") {
                                onSourceSelected("all")
                            },
                            secondaryCta = viewState.selectedTopic?.let {
                                EmptyStateCta(label = "Clear filter") {
                                    // Reselect first topic to reset
                                    viewState.enabledTopics.firstOrNull()?.let { t ->
                                        onTopicSelected(t)
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = 8.dp,
                                bottom = 80.dp,   // TODO Wave 4: adjust for actual BottomNav height
                            ),
                        ) {
                            DayBucket.entries.forEach { bucket ->
                                val items = viewState.articlesByDay[bucket] ?: return@forEach
                                if (items.isEmpty()) return@forEach

                                item(key = "header_${bucket.name}") {
                                    SectionHeader(label = bucket.label)
                                }

                                feedItems(
                                    items = items,
                                    seenIds = viewState.seenArticleIds,
                                    lastVisitedEpoch = 0L, // TODO: pass from viewState once surfaced
                                    onCardClick = onCardClick,
                                    onBookmarkClick = onBookmarkClick,
                                    onShareClick = onShareClick,
                                    onLongPress = onLongPress,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/** LazyListScope extension to emit feed cards for a bucket's item list. */
private fun LazyListScope.feedItems(
    items: List<BaseArticle>,
    seenIds: List<String>,
    lastVisitedEpoch: Long,
    onCardClick: (BaseArticle) -> Unit,
    onBookmarkClick: (BaseArticle) -> Unit,
    onShareClick: (BaseArticle) -> Unit,
    onLongPress: (BaseArticle) -> Unit,
) {
    items(
        items = items,
        key = { it.id },
    ) { article ->
        val isRead = seenIds.contains(article.id)
        article.ToFeedCard(
            isRead = isRead,
            onClick = { onCardClick(article) },
            onBookmarkClick = { onBookmarkClick(article) },
            onShareClick = { onShareClick(article) },
            onLongClick = { onLongPress(article) },
        )
    }
}

@Composable
private fun BaseArticle.ToFeedCard(
    isRead: Boolean,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    when (this) {
        is GithubRepo -> GithubItem(
            post = this,
            isRead = isRead,
            onClick = onClick,
            onBookmarkClick = onBookmarkClick,
            onShareClick = onShareClick,
            onLongClick = onLongClick,
        )
        is Conference -> ConferenceItem(
            conf = this,
            isRead = isRead,
            onClick = onClick,
            onBookmarkClick = onBookmarkClick,
            onShareClick = onShareClick,
            onLongClick = onLongClick,
        )
        is ProductHunt -> ProductHuntItem(
            product = this,
            isRead = isRead,
            onClick = onClick,
            onBookmarkClick = onBookmarkClick,
            onShareClick = onShareClick,
            onLongClick = onLongClick,
        )
        is Article -> when (this.source) {
            Source.FREE_CODE_CAMP -> FreeCodeCampItem(
                article = this, isRead = isRead,
                onClick = onClick, onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick, onLongClick = onLongClick,
            )
            Source.HACKER_NEWS -> HackerNewsItem(
                article = this, isRead = isRead,
                onClick = onClick, onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick, onLongClick = onLongClick,
            )
            Source.HACKER_NOON -> HackerNoonItem(
                article = this, isRead = isRead,
                onClick = onClick, onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick, onLongClick = onLongClick,
            )
            Source.REDDIT -> RedditItem(
                article = this, isRead = isRead,
                onClick = onClick, onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick, onLongClick = onLongClick,
            )
            Source.DEVTO -> DevtoItem(
                article = this, isRead = isRead,
                onClick = onClick, onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick, onLongClick = onLongClick,
            )
            Source.LOBSTERS -> LobstersItem(
                article = this, isRead = isRead,
                onClick = onClick, onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick, onLongClick = onLongClick,
            )
            Source.HASH_NODE -> HashnodeItem(
                article = this, isRead = isRead,
                onClick = onClick, onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick, onLongClick = onLongClick,
            )
            Source.INDIE_HACKERS -> IndieHackersItem(
                article = this, isRead = isRead,
                onClick = onClick, onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick, onLongClick = onLongClick,
            )
            Source.MEDIUM -> MediumItem(
                article = this, isRead = isRead,
                onClick = onClick, onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick, onLongClick = onLongClick,
            )
            else -> {} // Source types handled by their own model type above
        }
    }
}

// -------------------------------------------------------------------------
// Previews
// -------------------------------------------------------------------------

@Preview
@Composable
private fun HomeScreenLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        HomeScreen(
            viewState = HomeViewState(
                activeSourceId = "all",
                enabledSources = kotlinx.collections.immutable.persistentListOf(
                    Source.GITHUB, Source.HACKER_NEWS, Source.DEVTO,
                ),
                enabledTopics = kotlinx.collections.immutable.persistentListOf(
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
private fun HomeScreenLoadingDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        HomeScreen(
            viewState = HomeViewState(
                activeSourceId = "all",
                enabledSources = kotlinx.collections.immutable.persistentListOf(
                    Source.GITHUB, Source.HACKER_NEWS,
                ),
                isLoading = true,
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
