package com.zrcoding.hackertab.home.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.adaptive.LocalIsTabletSize
import com.zrcoding.hackertab.design.components.LocalCoachmarkAnchors
import com.zrcoding.hackertab.design.components.SourceRail
import com.zrcoding.hackertab.design.components.TopicChipStrip
import com.zrcoding.hackertab.design.components.cards.ArticleCard
import com.zrcoding.hackertab.design.components.cards.ConferenceCard
import com.zrcoding.hackertab.design.components.cards.LaunchCard
import com.zrcoding.hackertab.design.components.cards.MetaDotText
import com.zrcoding.hackertab.design.components.cards.MetaIconText
import com.zrcoding.hackertab.design.components.cards.RepoCard
import com.zrcoding.hackertab.design.components.states.EmptyState
import com.zrcoding.hackertab.design.components.states.EmptyStateCta
import com.zrcoding.hackertab.design.components.states.ErrorState
import com.zrcoding.hackertab.design.components.states.FeedLoadingSkeleton
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.home_empty_filter_body
import com.zrcoding.hackertab.design.resources.home_empty_filter_cta_clear
import com.zrcoding.hackertab.design.resources.home_empty_filter_title
import com.zrcoding.hackertab.design.resources.home_empty_no_sources_body
import com.zrcoding.hackertab.design.resources.home_empty_no_sources_cta
import com.zrcoding.hackertab.design.resources.home_empty_no_sources_title
import com.zrcoding.hackertab.design.resources.home_empty_no_topics_body
import com.zrcoding.hackertab.design.resources.home_empty_no_topics_cta
import com.zrcoding.hackertab.design.resources.home_empty_no_topics_title
import com.zrcoding.hackertab.design.resources.ic_claps
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_like
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.HackertabMotion
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.SourceHackerNews
import com.zrcoding.hackertab.design.theme.SourceLobsters
import com.zrcoding.hackertab.design.theme.SourceReddit
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.home.presentation.utils.ShareData
import com.zrcoding.hackertab.home.presentation.utils.ShareManager
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    onNavigateToWebView: (String) -> Unit,
    onNavigateToTopicsSettings: () -> Unit,
    onNavigateToSourcesSettings: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val shareManager: ShareManager = koinInject()

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

    val isTabletSize = LocalIsTabletSize.current
    LaunchedEffect(viewState.articles, isTabletSize) {
        if (isTabletSize && viewState.articles.isNotEmpty()) {
            onNavigateToWebView(viewState.articles.first().url)
        }
    }

    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.HOME)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    viewState: HomeViewState,
    onSourceSelected: (String) -> Unit,
    onTopicSelected: (Topic) -> Unit,
    onRefresh: () -> Unit,
    onNavigateToSourcesSettings: () -> Unit,
    onNavigateToTopicsSettings: () -> Unit,
    onCardClick: (BaseArticle) -> Unit,
    onBookmarkClick: (BaseArticle) -> Unit,
    onLongPress: (BaseArticle) -> Unit,
) {
    val pullRefreshState = rememberPullToRefreshState()
    val coachmarkAnchors = LocalCoachmarkAnchors.current

    Column(modifier = Modifier.fillMaxSize()) {
        SourceRail(
            sources = viewState.enabledSources,
            activeSourceId = viewState.activeSourceId,
            onSelect = onSourceSelected,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coachmarkAnchors.sourceRail = it.boundsInRoot() },
        )

        if (viewState.showTopicStrip && viewState.enabledTopics.isNotEmpty()) {
            TopicChipStrip(
                topics = viewState.enabledTopics,
                activeTopicId = viewState.selectedTopic?.value,
                onSelect = onTopicSelected,
                canAddTopic = viewState.canAddTopic,
                onAddTopic = onNavigateToTopicsSettings,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(MaterialTheme.dimension.space4))
        }

        PullToRefreshBox(
            isRefreshing = viewState.isLoading,
            onRefresh = onRefresh,
            state = pullRefreshState,
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coachmarkAnchors.feed = it.boundsInRoot() },
        ) {
            Crossfade(
                targetState = viewState.isLoading,
                animationSpec = tween(
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
                                .padding(top = MaterialTheme.dimension.space8),
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

                    viewState.needsTopicSetup -> {
                        EmptyState(
                            icon = Icons.Outlined.Tag,
                            title = stringResource(Res.string.home_empty_no_topics_title),
                            body = stringResource(Res.string.home_empty_no_topics_body),
                            primaryCta = EmptyStateCta(
                                label = stringResource(Res.string.home_empty_no_topics_cta),
                            ) {
                                onNavigateToTopicsSettings()
                            },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    viewState.articles.isEmpty() -> {
                        EmptyState(
                            icon = Icons.Outlined.Layers,
                            title = stringResource(Res.string.home_empty_filter_title),
                            body = stringResource(Res.string.home_empty_filter_body),
                            primaryCta = viewState.selectedTopic?.let {
                                EmptyStateCta(
                                    label = stringResource(Res.string.home_empty_filter_cta_clear),
                                ) {
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
                                top = MaterialTheme.dimension.space8,
                                bottom = 80.dp,
                            ),
                        ) {
                            feedItems(
                                items = viewState.articles,
                                onCardClick = onCardClick,
                                onBookmarkClick = onBookmarkClick,
                                onLongPress = onLongPress,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.feedItems(
    items: List<BaseArticle>,
    onCardClick: (BaseArticle) -> Unit,
    onBookmarkClick: (BaseArticle) -> Unit,
    onLongPress: (BaseArticle) -> Unit,
) {
    itemsIndexed(
        items = items,
        key = { _, item -> item.id },
    ) { index, article ->
        val coachmarkAnchors = LocalCoachmarkAnchors.current
        Box(
            modifier = if (index == 0) {
                Modifier.onGloballyPositioned { coachmarkAnchors.firstCard = it.boundsInRoot() }
            } else {
                Modifier
            },
        ) {
            article.ToFeedCard(
                onClick = { onCardClick(article) },
                onBookmarkClick = { onBookmarkClick(article) },
                onLongClick = { onLongPress(article) },
            )
        }
    }
}

@Composable
private fun BaseArticle.ToFeedCard(
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    when (this) {
        is GithubRepo -> RepoCard(
            repo = this,
            timeAgo = "trending",
            isBookmarked = this.bookmarked,
            onClick = onClick,
            onLongClick = onLongClick,
            onBookmarkClick = onBookmarkClick,
            onMoreClick = onLongClick,
        )

        is Conference -> ConferenceCard(
            conference = this,
            isBookmarked = this.bookmarked,
            onClick = onClick,
            onLongClick = onLongClick,
            onBookmarkClick = onBookmarkClick,
            onMoreClick = onLongClick,
        )

        is ProductHunt -> LaunchCard(
            product = this,
            isBookmarked = this.bookmarked,
            onClick = onClick,
            onLongClick = onLongClick,
            onBookmarkClick = onBookmarkClick,
            onMoreClick = onLongClick,
        )

        is Article -> when (this.source) {
            Source.FREE_CODE_CAMP -> ArticleCard(
                article = this,
                isBookmarked = this.bookmarked,
                onClick = onClick,
                onLongClick = onLongClick,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onLongClick,
                metaContent = {
                    MetaIconText(icon = Res.drawable.ic_time_24, text = this.publishedAt.timeAgo())
                },
            )

            Source.HACKER_NEWS -> ArticleCard(
                article = this,
                isBookmarked = this.bookmarked,
                onClick = onClick,
                onLongClick = onLongClick,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onLongClick,
                metaContent = {
                    MetaIconText(icon = Res.drawable.ic_time_24, text = this.publishedAt.timeAgo())
                    MetaDotText(text = "${this.reactions} pts", color = SourceHackerNews)
                    MetaIconText(icon = Res.drawable.ic_comment, text = "${this.commentsCount}")
                },
            )
            Source.HACKER_NOON -> ArticleCard(
                article = this,
                isBookmarked = this.bookmarked,
                onClick = onClick,
                onLongClick = onLongClick,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onLongClick,
                metaContent = {
                    MetaIconText(icon = Res.drawable.ic_time_24, text = this.publishedAt.timeAgo())
                },
            )

            Source.REDDIT -> ArticleCard(
                article = this,
                isBookmarked = this.bookmarked,
                onClick = onClick,
                onLongClick = onLongClick,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onLongClick,
                metaContent = {
                    MetaIconText(icon = Res.drawable.ic_time_24, text = this.publishedAt.timeAgo())
                    MetaDotText(text = "${this.reactions} pts", color = SourceReddit)
                    MetaIconText(icon = Res.drawable.ic_comment, text = "${this.commentsCount}")
                },
            )

            Source.DEVTO -> ArticleCard(
                article = this,
                isBookmarked = this.bookmarked,
                onClick = onClick,
                onLongClick = onLongClick,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onLongClick,
                metaContent = {
                    MetaIconText(icon = Res.drawable.ic_time_24, text = this.publishedAt.timeAgo())
                    MetaIconText(icon = Res.drawable.ic_comment, text = "${this.commentsCount}")
                    MetaIconText(icon = Res.drawable.ic_like, text = "${this.reactions}")
                },
            )

            Source.LOBSTERS -> ArticleCard(
                article = this,
                isBookmarked = this.bookmarked,
                onClick = onClick,
                onLongClick = onLongClick,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onLongClick,
                metaContent = {
                    MetaIconText(icon = Res.drawable.ic_time_24, text = this.publishedAt.timeAgo())
                    MetaDotText(text = "${this.reactions} pts", color = SourceLobsters)
                    MetaIconText(icon = Res.drawable.ic_comment, text = "${this.commentsCount}")
                },
            )

            Source.HASH_NODE -> ArticleCard(
                article = this,
                isBookmarked = this.bookmarked,
                onClick = onClick,
                onLongClick = onLongClick,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onLongClick,
                metaContent = {
                    MetaIconText(icon = Res.drawable.ic_time_24, text = this.publishedAt.timeAgo())
                    MetaIconText(icon = Res.drawable.ic_comment, text = "${this.commentsCount}")
                    MetaIconText(icon = Res.drawable.ic_like, text = "${this.reactions}")
                },
            )

            Source.INDIE_HACKERS -> ArticleCard(
                article = this,
                isBookmarked = this.bookmarked,
                onClick = onClick,
                onLongClick = onLongClick,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onLongClick,
                metaContent = {
                    MetaIconText(icon = Res.drawable.ic_time_24, text = this.publishedAt.timeAgo())
                    MetaDotText(text = "${this.reactions} pts", color = Color(0xFF4799EB))
                    MetaIconText(icon = Res.drawable.ic_comment, text = "${this.commentsCount}")
                },
            )

            Source.MEDIUM -> ArticleCard(
                article = this,
                isBookmarked = this.bookmarked,
                onClick = onClick,
                onLongClick = onLongClick,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onLongClick,
                metaContent = {
                    MetaIconText(icon = Res.drawable.ic_time_24, text = this.publishedAt.timeAgo())
                    MetaIconText(icon = Res.drawable.ic_claps, text = "${this.reactions}")
                    MetaIconText(icon = Res.drawable.ic_comment, text = "${this.commentsCount}")
                },
            )

            else -> {}
        }
    }
}

@Preview
@Composable
private fun HomeScreenLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        HomeScreen(
            viewState = HomeViewState(
                activeSourceId = "github",
                enabledSources = kotlinx.collections.immutable.persistentListOf(
                    Source.GITHUB, Source.HACKER_NEWS, Source.DEVTO,
                ),
                enabledTopics = kotlinx.collections.immutable.persistentListOf(
                    Topic(
                        value = "kotlin", label = "Kotlin", category = "mobile"
                    ),
                ),
                selectedTopic = Topic(
                    value = "kotlin", label = "Kotlin", category = "mobile"
                ),
                isLoading = false,
                articles = kotlinx.collections.immutable.persistentListOf(),
            ),
            onSourceSelected = {},
            onTopicSelected = {},
            onRefresh = {},
            onNavigateToSourcesSettings = {},
            onNavigateToTopicsSettings = {},
            onCardClick = {},
            onBookmarkClick = {},
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
                activeSourceId = "github",
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
            onLongPress = {},
        )
    }
}
