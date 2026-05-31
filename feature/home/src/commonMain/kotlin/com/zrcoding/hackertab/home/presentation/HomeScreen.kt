package com.zrcoding.hackertab.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.adaptive.LocalIsTabletSize
import com.zrcoding.hackertab.design.components.SectionHeader
import com.zrcoding.hackertab.design.components.SourceRail
import com.zrcoding.hackertab.design.components.TopicChipStrip
import com.zrcoding.hackertab.design.components.states.EmptyState
import com.zrcoding.hackertab.design.components.states.EmptyStateCta
import com.zrcoding.hackertab.design.components.states.ErrorState
import com.zrcoding.hackertab.design.components.states.FeedLoadingSkeleton
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.home_empty_filter_body
import com.zrcoding.hackertab.design.resources.home_empty_filter_cta_clear
import com.zrcoding.hackertab.design.resources.home_empty_filter_cta_see_all
import com.zrcoding.hackertab.design.resources.home_empty_filter_title
import com.zrcoding.hackertab.design.resources.home_empty_no_sources_body
import com.zrcoding.hackertab.design.resources.home_empty_no_sources_cta
import com.zrcoding.hackertab.design.resources.home_empty_no_sources_title
import com.zrcoding.hackertab.design.theme.HackertabMotion
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.SourceLoadState
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
import com.zrcoding.hackertab.home.presentation.cards.medium.MediumItem
import com.zrcoding.hackertab.home.presentation.cards.producthunt.ProductHuntItem
import com.zrcoding.hackertab.home.presentation.cards.reddit.RedditItem
import com.zrcoding.hackertab.home.presentation.utils.ShareData
import com.zrcoding.hackertab.home.presentation.utils.ShareManager
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

// TODO v4.1: CMP has no cross-platform reduce-motion flag; degrade per-platform via expect/actual.
private const val IS_REDUCED_MOTION = false

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

    // Auto-select first article on tablets
    val isTabletSize = LocalIsTabletSize.current
    LaunchedEffect(viewState.allArticles, isTabletSize) {
        if (isTabletSize && viewState.allArticles.isNotEmpty()) {
            onNavigateToWebView(viewState.allArticles.first().url)
        }
    }

    HomeScreen(
        viewState = viewState,
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

    Column(modifier = Modifier.fillMaxSize(),) {
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

        // Body — M7: Crossfade between skeleton and feed content on isLoading toggle
        PullToRefreshBox(
            isRefreshing = viewState.isLoading,
            onRefresh = onRefresh,
            state = pullRefreshState,
            modifier = Modifier.fillMaxSize(),
        ) {
            Crossfade(
                targetState = viewState.isLoading,
                animationSpec = if (IS_REDUCED_MOTION) tween(0) else tween(
                    durationMillis = HackertabMotion.fast,
                    easing = HackertabMotion.deceleratedEasing,
                ),
                label = "feed-loading-crossfade",
            ) { isLoading ->
                when {
                    isLoading -> {
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
                            title = stringResource(Res.string.home_empty_no_sources_title),
                            body = stringResource(Res.string.home_empty_no_sources_body),
                            primaryCta = EmptyStateCta(
                                label = stringResource(Res.string.home_empty_no_sources_cta),
                            ) {
                                onNavigateToSourcesSettings()
                            },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    viewState.articlesByDay.isEmpty() -> {
                        EmptyState(
                            icon = Icons.Outlined.Layers,
                            title = stringResource(Res.string.home_empty_filter_title),
                            body = stringResource(Res.string.home_empty_filter_body),
                            primaryCta = EmptyStateCta(
                                label = stringResource(Res.string.home_empty_filter_cta_see_all),
                            ) {
                                onSourceSelected("all")
                            },
                            secondaryCta = viewState.selectedTopic?.let {
                                EmptyStateCta(
                                    label = stringResource(Res.string.home_empty_filter_cta_clear),
                                ) {
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
                                bottom = 80.dp,   // Accounts for BottomNav height (provided by MainNavHost).
                            ),
                        ) {
                            // Wave 5L: top-of-feed partial-reveal skeleton —
                            // shown while < 50% of aggregated sources have loaded.
                            if (viewState.isPartialReveal && viewState.isAllSourcesMode) {
                                item(key = "partial_reveal_skeleton") {
                                    FeedLoadingSkeleton(
                                        itemCount = 3,
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                }
                            }

                            var runningOffset = 0
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
                                    indexOffset = runningOffset,
                                )
                                runningOffset += items.size
                            }

                            // Wave 5L (Issue 3 / E6): inline error caption per
                            // failed source at the end of the feed.
                            viewState.perSourceLoadState.forEach { (source, state) ->
                                if (state is SourceLoadState.Failed) {
                                    item(key = "failed-${source.id}") {
                                        Text(
                                            text = "${source.label} unavailable",
                                            style = codeSmall,
                                            color = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 8.dp,
                                            ),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } // end Crossfade content
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
    indexOffset: Int = 0,
) {
    itemsIndexed(
        items = items,
        key = { _, item -> item.id },
    ) { index, article ->
        val isRead = seenIds.contains(article.id)
        StaggeredFeedCardEntry(
            absoluteIndex = indexOffset + index,
            article = article,
            isRead = isRead,
            onCardClick = onCardClick,
            onBookmarkClick = onBookmarkClick,
            onShareClick = onShareClick,
            onLongPress = onLongPress,
        )
    }
}

/**
 * M6: staggered fade-in + slide for the first 6 items on initial load. After
 * the first reveal, subsequent updates render immediately (no re-stagger).
 */
@Composable
private fun StaggeredFeedCardEntry(
    absoluteIndex: Int,
    article: BaseArticle,
    isRead: Boolean,
    onCardClick: (BaseArticle) -> Unit,
    onBookmarkClick: (BaseArticle) -> Unit,
    onShareClick: (BaseArticle) -> Unit,
    onLongPress: (BaseArticle) -> Unit,
) {
    val shouldStagger = !IS_REDUCED_MOTION && absoluteIndex < 6
    // Positional scope is fine here — LazyColumn `key` already keys by article.id.
    var visible by rememberSaveable(article.id) { mutableStateOf(!shouldStagger) }
    LaunchedEffect(article.id) {
        if (!visible) {
            kotlinx.coroutines.delay(30L * absoluteIndex)
            visible = true
        }
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = HackertabMotion.fast)) +
            slideInVertically(
                animationSpec = tween(
                    durationMillis = HackertabMotion.fast,
                    easing = HackertabMotion.deceleratedEasing,
                ),
                initialOffsetY = { it / 4 },
            ),
    ) {
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
