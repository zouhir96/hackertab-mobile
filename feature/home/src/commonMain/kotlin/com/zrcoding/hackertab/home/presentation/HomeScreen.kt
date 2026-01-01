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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ChipDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.ErrorMsgWithBtn
import com.zrcoding.hackertab.design.components.Icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.common_ok
import com.zrcoding.hackertab.design.resources.common_retry
import com.zrcoding.hackertab.design.resources.common_settings
import com.zrcoding.hackertab.design.resources.setting_master_screen_bookmarks
import com.zrcoding.hackertab.design.resources.setting_master_screen_contact_us
import com.zrcoding.hackertab.design.resources.setting_master_screen_sources
import com.zrcoding.hackertab.design.resources.setting_master_screen_topics
import com.zrcoding.hackertab.design.resources.setting_master_screen_version_name
import com.zrcoding.hackertab.design.resources.support_device_model
import com.zrcoding.hackertab.design.resources.support_device_os_version
import com.zrcoding.hackertab.design.resources.support_email
import com.zrcoding.hackertab.design.resources.support_email_subject
import com.zrcoding.hackertab.design.resources.support_no_apps_description
import com.zrcoding.hackertab.design.resources.support_no_apps_title
import com.zrcoding.hackertab.design.resources.support_support_footer_message
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.common.AppConfig
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
import com.zrcoding.hackertab.home.presentation.utils.ContactSupport
import com.zrcoding.hackertab.home.presentation.utils.ContactSupportData
import com.zrcoding.hackertab.home.presentation.utils.ShareData
import com.zrcoding.hackertab.home.presentation.utils.ShareManager
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeRoute(
    onNavigateToWebView: (String) -> Unit,
    onNavigateToTopicsSettings: () -> Unit,
    onNavigateToSourcesSettings: () -> Unit,
    onNavigateToBookmarks: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val shareManager: ShareManager = koinInject()

    Scaffold(
        scaffoldState = rememberScaffoldState(drawerState = drawerState),
        topBar = {
            HomeScreenTopAppBar(
                enabledSources = viewState.enabledSources,
                selectedSource = viewState.selectedSource,
                canAddSource = viewState.canAddSource,
                onSourceSelected = viewModel::onSourceSelected,
                onNavigationBtnClick = {
                    scope.launch { drawerState.open() }
                },
                onAddSourceClick = onNavigateToSourcesSettings,
            )
        },
        drawerContent = {
            HomeScreenDrawer(
                onNavigateToTopicsSettings = {
                    scope.launch {
                        drawerState.close()
                        onNavigateToTopicsSettings()
                    }
                },
                onNavigateToSourcesSettings = {
                    scope.launch {
                        drawerState.close()
                        onNavigateToSourcesSettings()
                    }
                },
                onNavigateToBookmarks = {
                    scope.launch {
                        drawerState.close()
                        onNavigateToBookmarks()
                    }
                }
            )
        },
        drawerShape = RoundedCornerShape(
            topEnd = MaterialTheme.dimension.medium,
            bottomEnd = MaterialTheme.dimension.medium
        )
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimension.medium))
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
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.large),
                    contentPadding = PaddingValues(bottom = MaterialTheme.dimension.extraBig)
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
                        Divider()
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
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.HOME)
}

@OptIn(ExperimentalMaterialApi::class)
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
                                modifier = Modifier.size(MaterialTheme.dimension.bigger),
                                painter = painterResource(it.Icon().first),
                                contentScale = ContentScale.FillBounds,
                                contentDescription = null,
                                colorFilter = if (it.Icon().second == Color.Unspecified) {
                                    null
                                } else ColorFilter.tint(it.Icon().second)
                            )
                            Spacer(modifier = Modifier.width(MaterialTheme.dimension.small))
                        }
                        Text(
                            text = selectedSource?.label.orEmpty(),
                            color = MaterialTheme.colors.onBackground,
                            style = MaterialTheme.typography.h6,
                            overflow = TextOverflow.Visible,
                            maxLines = 1
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Select source",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        enabledSources.forEach { source ->
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    onSourceSelected(source)
                                }
                            ) {
                                source.Icon(size = MaterialTheme.dimension.bigger)
                                Spacer(modifier = Modifier.width(MaterialTheme.dimension.small))
                                Text(text = source.label)
                            }
                        }
                        if (canAddSource) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    onAddSourceClick()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddBox,
                                    contentDescription = "Select source",
                                )
                                Spacer(modifier = Modifier.width(MaterialTheme.dimension.small))
                                Text(
                                    text = "Add source",
                                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationBtnClick,
                modifier = Modifier.background(
                    color = MaterialTheme.colors.secondary.copy(alpha = 0.5f),
                    shape = CircleShape
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Navigation button to show drawer",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = MaterialTheme.dimension.none
    )
}

@Composable
private fun HomeScreenDrawer(
    onNavigateToTopicsSettings: () -> Unit,
    onNavigateToSourcesSettings: () -> Unit,
    onNavigateToBookmarks: () -> Unit,
) {
    val contactSupport: ContactSupport = koinInject()
    val appConfig: AppConfig = koinInject()
    val contactSupportData = ContactSupportData(
        email = stringResource(Res.string.support_email),
        subject = stringResource(Res.string.support_email_subject),
        footerMessage = stringResource(Res.string.support_support_footer_message),
        osVersion = stringResource(Res.string.support_device_os_version),
        deviceModel = stringResource(Res.string.support_device_model),
        appVersion = appConfig.versionName,
        noAppFoundTitle = stringResource(Res.string.support_no_apps_title),
        noAppFoundDescription = stringResource(Res.string.support_no_apps_description),
        noAppFoundOk = stringResource(Res.string.common_ok),
    )
    Column(
        modifier = Modifier.fillMaxSize().padding(MaterialTheme.dimension.big),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hackertab", style = MaterialTheme.typography.h5)
        Divider()
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.large)
        ) {
            HomeScreenDrawerItem(
                title = stringResource(Res.string.setting_master_screen_topics),
                onClick = onNavigateToTopicsSettings
            )
            HomeScreenDrawerItem(
                title = stringResource(Res.string.setting_master_screen_sources),
                onClick = onNavigateToSourcesSettings
            )
            HomeScreenDrawerItem(
                title = stringResource(Res.string.setting_master_screen_bookmarks),
                onClick = onNavigateToBookmarks
            )
            HomeScreenDrawerItem(
                title = stringResource(Res.string.setting_master_screen_contact_us),
                onClick = {
                    contactSupport.invoke(data = contactSupportData)
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        AppVersionName(versionName = appConfig.versionName)
        Spacer(modifier = Modifier.heightIn(MaterialTheme.dimension.big))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeScreenDrawerItem(
    title: String,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.secondary,
        elevation = 0.4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimension.medium)
        ) {
            Spacer(modifier = Modifier.width(MaterialTheme.dimension.large))
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(MaterialTheme.dimension.bigger),
                imageVector = Icons.AutoMirrored.Default.ArrowRight,
                contentDescription = "Arrow forward",
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
private fun AppVersionName(modifier: Modifier = Modifier, versionName: String) {
    Text(
        modifier = modifier,
        text = stringResource(Res.string.setting_master_screen_version_name, versionName),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center
    )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun HomeScreenTopicsFilter(
    enabledTopics: ImmutableList<Topic>,
    selectedTopic: Topic?,
    canAddTopic: Boolean,
    onTopicSelected: (Topic) -> Unit,
    onAddTopicClick: () -> Unit,
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(bottom = MaterialTheme.dimension.small),
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimension.default),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium),
        ) {
            items(enabledTopics) { topic ->
                val selected = selectedTopic == topic
                FilterChip(
                    selected = selected,
                    onClick = { onTopicSelected(topic) },
                    shape = MaterialTheme.shapes.medium,
                    colors = if (selected) ChipDefaults.filterChipColors(
                        backgroundColor = MaterialTheme.colors.onBackground,
                        contentColor = MaterialTheme.colors.background
                    ) else ChipDefaults.filterChipColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        contentColor = MaterialTheme.colors.onBackground
                    ),
                ) {
                    Text(text = topic.label)
                }
            }
            if (canAddTopic) {
                item {
                    IconButton(
                        onClick = onAddTopicClick,
                        modifier = Modifier.background(
                            color = MaterialTheme.colors.secondary,
                            shape = CircleShape
                        ).size(ChipDefaults.MinHeight)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add topic",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
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
