package com.zrcoding.hackertab.shared.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.zrcoding.hackertab.analytics.LocalAnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.bookmarks.presentation.BookmarksRoute
import com.zrcoding.hackertab.bookmarks.presentation.BookmarksSearchRoute
import com.zrcoding.hackertab.design.adaptive.LocalIsTabletSize
import com.zrcoding.hackertab.design.components.BottomNavItem
import com.zrcoding.hackertab.design.components.CoachmarkAnchors
import com.zrcoding.hackertab.design.components.HackertabBottomNav
import com.zrcoding.hackertab.design.components.HackertabNavRail
import com.zrcoding.hackertab.design.components.LocalCoachmarkAnchors
import com.zrcoding.hackertab.design.components.WebViewRoute
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.settings_about_title
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase
import com.zrcoding.hackertab.home.presentation.HomeRoute
import com.zrcoding.hackertab.onboarding.coachmarks.CoachmarkOverlay
import com.zrcoding.hackertab.onboarding.coachmarks.CoachmarkViewModel
import com.zrcoding.hackertab.onboarding.done.OnboardingDoneRoute
import com.zrcoding.hackertab.onboarding.profile.SetupProfileRoute
import com.zrcoding.hackertab.onboarding.sources.SetupSourcesRoute
import com.zrcoding.hackertab.onboarding.topics.SetupTopicsRoute
import com.zrcoding.hackertab.settings.presentation.about.SettingsAboutRoute
import com.zrcoding.hackertab.settings.presentation.appearance.SettingsAppearanceRoute
import com.zrcoding.hackertab.settings.presentation.master.SettingsMasterRoute
import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesRoute
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsRoute
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object SetupProfileScreen : NavKey

@Serializable
data class SetupTopicsScreen(val profile: Profile) : NavKey

@Serializable
data object SetupSourcesScreen : NavKey

@Serializable
data object OnboardingDoneScreen : NavKey

@Serializable
object HomeScreen : NavKey

@Serializable
data class FocusedFeedScreen(val sourceId: String) : NavKey

@Serializable
object SettingsMasterScreen : NavKey

@Serializable
object SettingsTopicsScreen : NavKey

@Serializable
object SettingsSourcesScreen : NavKey

@Serializable
object SettingsAppearanceScreen : NavKey

@Serializable
object SettingsAboutScreen : NavKey

@Serializable
object BookmarksScreen : NavKey

@Serializable
object BookmarksSearchScreen : NavKey

@Serializable
data class WebViewScreen(val url: String) : NavKey

private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(SetupProfileScreen::class, SetupProfileScreen.serializer())
            subclass(SetupTopicsScreen::class, SetupTopicsScreen.serializer())
            subclass(SetupSourcesScreen::class, SetupSourcesScreen.serializer())
            subclass(OnboardingDoneScreen::class, OnboardingDoneScreen.serializer())
            subclass(HomeScreen::class, HomeScreen.serializer())
            subclass(SettingsMasterScreen::class, SettingsMasterScreen.serializer())
            subclass(SettingsTopicsScreen::class, SettingsTopicsScreen.serializer())
            subclass(SettingsSourcesScreen::class, SettingsSourcesScreen.serializer())
            subclass(SettingsAppearanceScreen::class, SettingsAppearanceScreen.serializer())
            subclass(SettingsAboutScreen::class, SettingsAboutScreen.serializer())
            subclass(BookmarksScreen::class, BookmarksScreen.serializer())
            subclass(BookmarksSearchScreen::class, BookmarksSearchScreen.serializer())
            subclass(WebViewScreen::class, WebViewScreen.serializer())
        }
    }
}

private val topLevelNavItems = persistentListOf(
    BottomNavItem(id = "today", label = "Today", icon = Icons.Outlined.Today),
    BottomNavItem(id = "saved", label = "Saved", icon = Icons.AutoMirrored.Outlined.LibraryBooks),
    BottomNavItem(id = "settings", label = "Settings", icon = Icons.Outlined.Settings),
)

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    setupStatus: GetStartDestinationUseCase.Result,
) {
    val analyticsHelper = LocalAnalyticsHelper.current
    val profile = setupStatus.profile
    val stack = when {
        profile == null -> arrayOf(SetupProfileScreen)
        setupStatus.topicsSetup -> arrayOf(
            SetupProfileScreen,
            SetupTopicsScreen(profile)
        )

        setupStatus.sourcesSetup -> arrayOf(
            SetupProfileScreen,
            SetupTopicsScreen(profile),
            SetupSourcesScreen
        )

        else -> arrayOf(HomeScreen)
    }
    val backStack = rememberNavBackStack(configuration = config, elements = stack)
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isTabletSize = windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)

    val tip by remember { derivedStateOf { backStack.lastOrNull() } }
    val isTopLevel by remember {
        derivedStateOf {
            tip is HomeScreen ||
                    tip is BookmarksScreen ||
                    tip is SettingsMasterScreen
        }
    }
    val activeId by remember {
        derivedStateOf {
            when (tip) {
                is BookmarksScreen -> "saved"
                is SettingsMasterScreen -> "settings"
                else -> "today"
            }
        }
    }

    fun navigateToOnboardingDone() {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                name = AnalyticsEvent.Types.SETUP_COMPLETED,
                properties = emptySet()
            )
        )
        backStack.clear()
        backStack.add(OnboardingDoneScreen)
    }

    fun switchTab(id: String) {
        backStack.clear()
        when (id) {
            "today" -> backStack.add(HomeScreen)
            "saved" -> backStack.add(BookmarksScreen)
            "settings" -> backStack.add(SettingsMasterScreen)
        }
    }

    val coachmarkViewModel: CoachmarkViewModel = koinViewModel()
    val coachmarksSeen by coachmarkViewModel.coachmarksSeen.collectAsStateWithLifecycle()
    val isOnHome by remember { derivedStateOf { tip is HomeScreen } }

    val coachmarkAnchors = remember { CoachmarkAnchors() }

    CompositionLocalProvider(
        LocalIsTabletSize provides isTabletSize,
        LocalCoachmarkAnchors provides coachmarkAnchors,
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize()) {
                if (isTabletSize && isTopLevel) {
                    HackertabNavRail(
                        items = topLevelNavItems,
                        activeId = activeId,
                        onSelect = ::switchTab,
                    )
                }
                Box(modifier = Modifier.fillMaxSize(),) {
                    NavDisplay(
                        modifier = Modifier.fillMaxSize(),
                        backStack = backStack,
                        sceneStrategy = listDetailStrategy,
                        entryProvider = entryProvider {
                            entry<SetupProfileScreen> {
                                ScreenWithBackButton(
                                    onBackClick = { backStack.removeLastOrNull() }
                                ) {
                                    SetupProfileRoute(
                                        navigateToNextScreen = {
                                            if (setupStatus.topicsSetup) {
                                                backStack.add(SetupTopicsScreen(it))
                                            } else if (setupStatus.sourcesSetup) {
                                                backStack.add(SetupSourcesScreen)
                                            } else {
                                                navigateToOnboardingDone()
                                            }
                                        }
                                    )
                                }
                            }
                            entry<SetupTopicsScreen> {
                                ScreenWithBackButton(
                                    onBackClick = { backStack.removeLastOrNull() }
                                ) {
                                    SetupTopicsRoute(
                                        profile = it.profile,
                                        navigateToNextScreen = {
                                            backStack.add(SetupSourcesScreen)
                                        }
                                    )
                                }
                            }
                            entry<SetupSourcesScreen> {
                                ScreenWithBackButton(
                                    onBackClick = { backStack.removeLastOrNull() }
                                ) {
                                    SetupSourcesRoute(navigateToNextScreen = ::navigateToOnboardingDone)
                                }
                            }
                            entry<OnboardingDoneScreen> {
                                OnboardingDoneRoute(
                                    navigateToFeed = {
                                        backStack.clear()
                                        backStack.add(HomeScreen)
                                    },
                                )
                            }
                            entry<HomeScreen>(
                                metadata = ListDetailSceneStrategy.listPane(
                                    detailPlaceholder = {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "Select an article to read",
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.onBackground.copy(
                                                    alpha = 0.6f
                                                )
                                            )
                                        }
                                    }
                                )
                            ) {
                                HomeRoute(
                                    onNavigateToWebView = { url ->
                                        backStack.removeAll { it is WebViewScreen }
                                        backStack.add(WebViewScreen(url))
                                    },
                                    onNavigateToTopicsSettings = {
                                        backStack.add(SettingsTopicsScreen)
                                    },
                                    onNavigateToSourcesSettings = {
                                        backStack.add(SettingsSourcesScreen)
                                    },
                                )
                            }
                            entry<SettingsMasterScreen>(
                                metadata = ListDetailSceneStrategy.listPane(
                                    detailPlaceholder = {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(
                                                    text = "Settings",
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                )
                                                Text(
                                                    text = "Pick a section",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                )
                                            }
                                        }
                                    },
                                ),
                            ) {
                                SettingsMasterRoute(
                                    onNavigateToTopics = { backStack.add(SettingsTopicsScreen) },
                                    onNavigateToSources = { backStack.add(SettingsSourcesScreen) },
                                    onNavigateToAppearance = {
                                        backStack.add(SettingsAppearanceScreen)
                                    },
                                    onNavigateToAbout = { backStack.add(SettingsAboutScreen) },
                                )
                            }
                            entry<SettingsTopicsScreen> {
                                ScreenWithBackButton(
                                    onBackClick = { backStack.removeLastOrNull() },
                                    screen = {
                                        SettingTopicsRoute()
                                    }
                                )
                            }
                            entry<SettingsSourcesScreen> {
                                ScreenWithBackButton(
                                    onBackClick = { backStack.removeLastOrNull() },
                                    screen = {
                                        SettingSourcesRoute()
                                    }
                                )
                            }
                            entry<SettingsAppearanceScreen> {
                                ScreenWithBackButton(
                                    onBackClick = { backStack.removeLastOrNull() },
                                    screen = {
                                        SettingsAppearanceRoute()
                                    }
                                )
                            }
                            entry<SettingsAboutScreen> {
                                ScreenWithBackButton(
                                    onBackClick = { backStack.removeLastOrNull() },
                                    title = stringResource(Res.string.settings_about_title),
                                    screen = {
                                        SettingsAboutRoute(
                                            onNavigateToWebView = { url ->
                                                backStack.removeAll { it is WebViewScreen }
                                                backStack.add(WebViewScreen(url))
                                            },
                                        )
                                    }
                                )
                            }
                            entry<BookmarksScreen>(
                                metadata = ListDetailSceneStrategy.listPane(
                                    detailPlaceholder = {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "Select a bookmark to read",
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.6f
                                                )
                                            )
                                        }
                                    }
                                )
                            ) {
                                BookmarksRoute(
                                    onNavigateToWebView = { url ->
                                        backStack.removeAll { it is WebViewScreen }
                                        backStack.add(WebViewScreen(url))
                                    },
                                    onNavigateToSearch = {
                                        backStack.add(BookmarksSearchScreen)
                                    },
                                    onNavigateToHome = { switchTab("today") }
                                )
                            }
                            entry<BookmarksSearchScreen> {
                                BookmarksSearchRoute(
                                    onNavigateBack = { backStack.removeLastOrNull() },
                                    onNavigateToWebView = { url ->
                                        backStack.removeAll { it is WebViewScreen }
                                        backStack.add(WebViewScreen(url))
                                    },
                                )
                            }
                            entry<WebViewScreen>(
                                metadata = ListDetailSceneStrategy.detailPane()
                            ) { route ->
                                if (isTabletSize) {
                                    WebViewRoute(url = route.url)
                                } else {
                                    ScreenWithBackButton(
                                        onBackClick = { backStack.removeLastOrNull() },
                                        screen = {
                                            WebViewRoute(url = route.url)
                                        }
                                    )
                                }
                            }
                        },
                        transitionSpec = {
                            fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                        },
                        popTransitionSpec = {
                            fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                        },
                        predictivePopTransitionSpec = {
                            fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                        }
                    )
                    if (isTopLevel && !isTabletSize) {
                        HackertabBottomNav(
                            items = topLevelNavItems,
                            activeId = activeId,
                            onSelect = ::switchTab,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .windowInsetsPadding(WindowInsets())
                                .padding(bottom = MaterialTheme.dimension.space20)
                        )
                    }
                }
            }
            CoachmarkOverlay(
                visible = !coachmarksSeen && isOnHome,
                onDismiss = coachmarkViewModel::dismiss,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenWithBackButton(
    onBackClick: () -> Unit,
    title: String ?= null,
    screen: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back button",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            title = {
                title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            windowInsets = WindowInsets()
        )
        screen()
    }
}
