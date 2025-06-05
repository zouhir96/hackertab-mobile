package com.zrcoding.hackertab.shared.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.zrcoding.hackertab.analytics.LocalAnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase
import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase.ScreenToComplete
import com.zrcoding.hackertab.home.presentation.HomeRoute
import com.zrcoding.hackertab.onboarding.SetupProfileScreen
import com.zrcoding.hackertab.onboarding.SetupSourcesScreen
import com.zrcoding.hackertab.onboarding.SetupTopicsScreen
import com.zrcoding.hackertab.onboarding.profile.SetupProfileRoute
import com.zrcoding.hackertab.onboarding.sources.SetupSourcesRoute
import com.zrcoding.hackertab.onboarding.topics.SetupTopicsRoute
import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesRoute
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsRoute
import kotlinx.serialization.Serializable

fun ScreenToComplete.toDestination(): Any = when (this) {
    is ScreenToComplete.ProfileSetup -> SetupProfileScreen(newUser)
    ScreenToComplete.TopicsSetup -> SetupTopicsScreen
    ScreenToComplete.SourcesSetup -> SetupSourcesScreen
}

@Serializable
object HomeScreen

@Serializable
object SettingsTopicsScreen

@Serializable
object SettingsSourcesScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: GetStartDestinationUseCase.Result,
) {
    val analyticsHelper = LocalAnalyticsHelper.current
    val screensToComplete =
        if (startDestination is GetStartDestinationUseCase.Result.NotCompleted && startDestination.screens.isNotEmpty()) {
            startDestination.screens
        } else emptyList()
    val setupProfileScreen = screensToComplete.firstOrNull {
        it is ScreenToComplete.ProfileSetup
    }?.toDestination()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = setupProfileScreen ?: HomeScreen
    ) {
        if (setupProfileScreen != null) {
            val shouldSetupTopics = screensToComplete.contains(ScreenToComplete.TopicsSetup)
            val shouldSetupSources = screensToComplete.contains(ScreenToComplete.SourcesSetup)
            fun navigateToHome(popupTo: Any) {
                analyticsHelper.logEvent(
                    event = AnalyticsEvent(
                        name = AnalyticsEvent.Types.SETUP_COMPLETED,
                        properties = emptySet()
                    )
                )
                navController.navigate(
                    HomeScreen,
                    navOptions {
                        popUpTo(popupTo) {
                            inclusive = true
                        }
                    }
                )
            }
            composable<SetupProfileScreen> {
                ScreenWithBackButton(
                    onBackClick = { navController.navigateUp() }
                ) {
                    SetupProfileRoute(
                        navigateToNextScreen = { if (shouldSetupTopics) {
                                navController.navigate(SetupTopicsScreen)
                            } else if (shouldSetupSources) {
                                navController.navigate(SetupSourcesScreen)
                            } else {
                                navigateToHome(setupProfileScreen)
                            }
                        }
                    )
                }
            }
            if (shouldSetupTopics) {
                composable<SetupTopicsScreen> {
                    ScreenWithBackButton(
                        onBackClick = { navController.navigateUp() }
                    ) {
                        SetupTopicsRoute(
                            navigateToNextScreen = {
                                navController.navigate(SetupSourcesScreen)
                            }
                        )
                    }
                }
            }
            if (shouldSetupSources) {
                composable<SetupSourcesScreen> {
                    ScreenWithBackButton(
                        onBackClick = { navController.navigateUp() }
                    ) {
                        SetupSourcesRoute(
                            navigateToNextScreen = {
                                navigateToHome(setupProfileScreen)
                            }
                        )
                    }
                }
            }
        }
        composable<HomeScreen> {
            HomeRoute(
                onNavigateToTopicsSettings = {
                    navController.navigate(SettingsTopicsScreen)
                },
                onNavigateToSourcesSettings = {
                    navController.navigate(SettingsSourcesScreen)
                }
            )
        }
        composableWithAnimation<SettingsTopicsScreen> {
            ScreenWithBackButton(
                onBackClick = { navController.navigateUp() },
                screen = {
                    SettingTopicsRoute()
                }
            )
        }
        composableWithAnimation<SettingsSourcesScreen> {
            ScreenWithBackButton(
                onBackClick = { navController.navigateUp() },
                screen = {
                    SettingSourcesRoute()
                }
            )
        }
    }
}

private inline fun <reified T : Any> NavGraphBuilder.composableWithAnimation(
    duration: Int = 700,
    noinline composable: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(duration)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(duration)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(duration)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(duration)
            )
        }
    ) {
        composable(it)
    }
}

@Composable
private fun ScreenWithBackButton(
    onBackClick: () -> Unit,
    screen: @Composable () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackClick) {
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
    }) {
        screen()
    }
}