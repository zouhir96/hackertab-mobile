package com.zrcoding.hackertab.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.adaptive.LocalIsTabletSize
import com.zrcoding.hackertab.design.components.ErrorMsgWithBtn
import com.zrcoding.hackertab.design.components.Icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.common_retry
import com.zrcoding.hackertab.design.resources.common_settings
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
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
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    onNavigateToWebView: (String) -> Unit,
    onNavigateToTopicsSettings: () -> Unit,
    onNavigateToSourcesSettings: () -> Unit,
    onNavigateToBookmarks: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val shareManager: ShareManager = koinInject()

    // TODO Wave 4: drawer removed during M3 migration; revisit nav. The
    //  HomeScreenDrawer/HomeScreenDrawerItem composables and rememberDrawerState
    //  scaffolding were dropped here so HomeScreen can compile against M3.
    //  onNavigateToBookmarks is intentionally retained for Wave 4 wiring.
    @Suppress("UNUSED_PARAMETER")
    val unusedBookmarksRoute = onNavigateToBookmarks

    Scaffold(
        topBar = {
            HomeScreenTopAppBar(
                enabledSources = viewState.enabledSources,
                selectedSource = viewState.selectedSource,
                canAddSource = viewState.canAddSource,
                onSourceSelected = viewModel::onSourceSelected,
                onNavigationBtnClick = {
                    // TODO Wave 4: re-add drawer / nav opening behaviour.
                },
                onAddSourceClick = onNavigateToSourcesSettings,
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
            if (viewState.selectedSource?.supportsFilters == true && viewState.enabledTopics.isNotEmpty()) {
                HomeScreenTopicsFilter(
                    enabledTopics = viewState.enabledTopics,
                    selectedTopic = viewState.selectedTopic,
                    canAddTopic = viewState.canAddTopic,
                    onTopicSelected = viewModel::onTopicSelected,
                    onAddTopicClick = onNavigateToTopicsSettings
                )
            }
            if (viewState.isLoading) {
                CircularProgressIndicator()
            }
            when {
                viewState.articles.isNotEmpty() -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
                    contentPadding = PaddingValues(bottom = MaterialTheme.dimension.space40)
                ) {
                    items(
                        items = viewState.articles,
                        key = { item -> item.id }
                    ) { item: BaseArticle ->
                        item.ToListItem(
                            onClick = { onNavigateToWebView(item.url) },
                            onBookmarkClick = { viewModel.toggleBookmark(item) },
                            onShareClick = {
                                shareManager.share(
                                    ShareData(
                                        title = item.title,
                                        url = item.url
                                    )
                                )
                            }
                        )
                        HorizontalDivider()
                    }
                }

                viewState.error != null -> ErrorMsgWithBtn(
                    modifier = Modifier.fillMaxSize(),
                    text = viewState.error,
                    btnText = if (viewState.canRefresh) Res.string.common_retry else null,
                    onBtnClicked = viewModel::onRefreshBtnClick
                )

                viewState.enabledSources.isEmpty() && viewState.isLoading.not() -> ErrorMsgWithBtn(
                    modifier = Modifier.fillMaxSize(),
                    text = "You didn't follow any source, you can follow your favorite sources in settings !!",
                    btnText = Res.string.common_settings,
                    onBtnClicked = onNavigateToSourcesSettings
                )
            }
        }
    }
    // Auto-select first article when articles are loaded (only on tablets)
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
private fun HomeScreenTopAppBar(
    enabledSources: ImmutableList<Source>,
    selectedSource: Source?,
    canAddSource: Boolean,
    onSourceSelected: (Source) -> Unit,
    onNavigationBtnClick: () -> Unit,
    onAddSourceClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        modifier = Modifier.heightIn(56.dp),
        title = {
            if (enabledSources.isNotEmpty()) {
                Box {
                    Row(
                        modifier = Modifier.clickable(
                            onClick = { expanded = true },
                            role = Role.DropdownList
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        selectedSource?.let {
                            Image(
                                modifier = Modifier.size(MaterialTheme.dimension.space24),
                                painter = painterResource(it.Icon().first),
                                contentScale = ContentScale.FillBounds,
                                contentDescription = null,
                                colorFilter = if (it.Icon().second == Color.Unspecified) {
                                    null
                                } else ColorFilter.tint(it.Icon().second)
                            )
                            Spacer(modifier = Modifier.width(MaterialTheme.dimension.space4))
                        }
                        Text(
                            text = selectedSource?.label.orEmpty(),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.headlineSmall,
                            overflow = TextOverflow.Visible,
                            maxLines = 1
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Select source",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        enabledSources.forEach { source ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        source.Icon(size = MaterialTheme.dimension.space24)
                                        Spacer(modifier = Modifier.width(MaterialTheme.dimension.space4))
                                        Text(text = source.label)
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    onSourceSelected(source)
                                }
                            )
                        }
                        if (canAddSource) {
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.AddBox,
                                            contentDescription = "Select source",
                                        )
                                        Spacer(modifier = Modifier.width(MaterialTheme.dimension.space4))
                                        Text(
                                            text = "Add source",
                                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                                        )
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    onAddSourceClick()
                                }
                            )
                        }
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationBtnClick,
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    shape = CircleShape
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Navigation button to show drawer",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun HomeScreenTopicsFilter(
    enabledTopics: ImmutableList<Topic>,
    selectedTopic: Topic?,
    canAddTopic: Boolean,
    onTopicSelected: (Topic) -> Unit,
    onAddTopicClick: () -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(bottom = MaterialTheme.dimension.space4),
        contentPadding = PaddingValues(horizontal = MaterialTheme.dimension.space16),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
    ) {
        items(enabledTopics) { topic ->
            val selected = selectedTopic == topic
            FilterChip(
                selected = selected,
                onClick = { onTopicSelected(topic) },
                shape = MaterialTheme.shapes.medium,
                label = { Text(text = topic.label) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    labelColor = MaterialTheme.colorScheme.onBackground,
                    selectedContainerColor = MaterialTheme.colorScheme.onBackground,
                    selectedLabelColor = MaterialTheme.colorScheme.background,
                ),
            )
        }
        if (canAddTopic) {
            item {
                IconButton(
                    onClick = onAddTopicClick,
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape
                    ).size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add topic",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Composable
private fun BaseArticle.ToListItem(
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    when (this) {
        is GithubRepo -> GithubItem(
            post = this,
            onClick = onClick,
            onBookmarkClick = onBookmarkClick,
            onShareClick = onShareClick
        )
        is Conference -> ConferenceItem(
            conf = this,
            onClick = onClick,
            onBookmarkClick = onBookmarkClick,
            onShareClick = onShareClick
        )
        is ProductHunt -> ProductHuntItem(
            product = this,
            onClick = onClick,
            onBookmarkClick = onBookmarkClick,
            onShareClick = onShareClick
        )
        is Article -> when(this.source) {
            Source.FREE_CODE_CAMP -> FreeCodeCampItem(
                article = this,
                onClick = onClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
            Source.HACKER_NEWS -> HackerNewsItem(
                article = this,
                onClick = onClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
            Source.HACKER_NOON -> HackerNoonItem(
                article = this,
                onClick = onClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
            Source.REDDIT -> RedditItem(
                article = this,
                onClick = onClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
            Source.DEVTO -> DevtoItem(
                article = this,
                onClick = onClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
            Source.LOBSTERS -> LobstersItem(
                article = this,
                onClick = onClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
            Source.HASH_NODE -> HashnodeItem(
                article = this,
                onClick = onClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
            Source.INDIE_HACKERS -> IndieHackersItem(
                article = this,
                onClick = onClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
            Source.MEDIUM -> MediumItem(
                article = this,
                onClick = onClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
            else -> {}
        }
    }
}
