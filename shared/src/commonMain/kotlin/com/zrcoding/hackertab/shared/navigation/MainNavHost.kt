package com.zrcoding.hackertab.shared.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.zrcoding.hackertab.analytics.LocalAnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.bookmarks.presentation.BookmarksRoute
import com.zrcoding.hackertab.design.adaptive.LocalIsTabletSize
import com.zrcoding.hackertab.design.components.WebViewRoute
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase
import com.zrcoding.hackertab.home.presentation.HomeRoute
import com.zrcoding.hackertab.onboarding.profile.SetupProfileRoute
import com.zrcoding.hackertab.onboarding.sources.SetupSourcesRoute
import com.zrcoding.hackertab.onboarding.topics.SetupTopicsRoute
import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesRoute
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
data object SetupProfileScreen : NavKey

@Serializable
data class SetupTopicsScreen(val profile: Profile) : NavKey

@Serializable
data object SetupSourcesScreen : NavKey

@Serializable
object HomeScreen : NavKey

@Serializable
object SettingsTopicsScreen : NavKey

@Serializable
object SettingsSourcesScreen : NavKey

@Serializable
object BookmarksScreen : NavKey

@Serializable
data class WebViewScreen(val url: String) : NavKey

private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(SetupProfileScreen::class, SetupProfileScreen.serializer())
            subclass(SetupTopicsScreen::class, SetupTopicsScreen.serializer())
            subclass(SetupSourcesScreen::class, SetupSourcesScreen.serializer())
            subclass(HomeScreen::class, HomeScreen.serializer())
            subclass(SettingsTopicsScreen::class, SettingsTopicsScreen.serializer())
            subclass(SettingsSourcesScreen::class, SettingsSourcesScreen.serializer())
            subclass(BookmarksScreen::class, BookmarksScreen.serializer())
            subclass(WebViewScreen::class, WebViewScreen.serializer())
        }
    }
}

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

    fun navigateToHome() {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                name = AnalyticsEvent.Types.SETUP_COMPLETED,
                properties = emptySet()
            )
        )
        backStack.clear()
        backStack.add(HomeScreen)
    }
    CompositionLocalProvider(LocalIsTabletSize provides isTabletSize) {
        NavDisplay(
            modifier = modifier,
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
                                navigateToHome()
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
                    SetupSourcesRoute(navigateToNextScreen = ::navigateToHome)
                }
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
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
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
                    onNavigateToBookmarks = {
                        backStack.removeAll { it is WebViewScreen }
                        backStack.add(BookmarksScreen)
                    }
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
            entry<BookmarksScreen>(
                metadata = ListDetailSceneStrategy.listPane(
                    detailPlaceholder = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Select a bookmark to read",
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                )
            ) {
                ScreenWithBackButton(
                    onBackClick = { backStack.removeLastOrNull() },
                    screen = {
                        BookmarksRoute(
                            onNavigateToWebView = { url ->
                                backStack.removeAll { it is WebViewScreen }
                                backStack.add(WebViewScreen(url))
                            }
                        )
                    }
                )
            }
            entry<WebViewScreen>(
                metadata = ListDetailSceneStrategy.detailPane()
            ) { route ->
                if (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
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
        }
        )
    }
}

@Composable
private fun ScreenWithBackButton(
    onBackClick: () -> Unit,
    screen: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.background(
                            color = MaterialTheme.colors.secondary.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back button",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                },
                title = {},
                backgroundColor = MaterialTheme.colors.background,
                elevation = MaterialTheme.dimension.none
            )
        }
    ) {
        screen()
    }
}