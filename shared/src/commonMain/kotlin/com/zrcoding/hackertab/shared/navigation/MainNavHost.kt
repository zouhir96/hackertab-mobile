package com.zrcoding.hackertab.shared.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase
import com.zrcoding.hackertab.home.presentation.HomeRoute
import com.zrcoding.hackertab.onboarding.OnboardingScreen
import com.zrcoding.hackertab.onboarding.onboardingNavGraph
import com.zrcoding.hackertab.settings.presentation.master.SettingMasterRoute
import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesRoute
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsRoute
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object Settings

@Serializable
object SettingsMasterScreen

@Serializable
object SettingsTopicsScreen

@Serializable
object SettingsSourcesScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: GetStartDestinationUseCase.Result,
    isExpandedScree: Boolean
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (startDestination == GetStartDestinationUseCase.Result.COMPLETED) {
            HomeScreen
        } else OnboardingScreen
    ) {
        if (startDestination is GetStartDestinationUseCase.Result.NotCompleted && startDestination.screens.isNotEmpty()) {
            onboardingNavGraph(
                screensToComplete = startDestination.screens,
                navController = navController,
                navigateToHome = {
                    navController.navigate(
                        HomeScreen,
                        navOptions {
                            popUpTo(it) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }
        composable<HomeScreen> {
            HomeRoute(
                isExpandedScreen = isExpandedScree,
                onNavigateToSettings = {
                    navController.navigate(Settings)
                },
                onNavigateToSourcesSettings = {
                    navController.navigate(SettingsSourcesScreen)
                }
            )
        }
        if (isExpandedScree) {
            composable<Settings> {
                SettingsTwoPanNavigation(rootNavHostController = navController)
            }
        } else {
            settingsOnePanNavigation(navController)
        }
    }
}

fun NavGraphBuilder.settingsOnePanNavigation(navController: NavHostController) {
    navigation<Settings>(startDestination = SettingsMasterScreen) {
        composableWithAnimation<SettingsMasterScreen> {
            ScreenWithBackButton(
                onBackClick = { navController.navigateUp() },
                screen = {
                    SettingMasterRoute(
                        showSelectedItem = false,
                        onNavigateToTopics = {
                            navController.navigate(SettingsTopicsScreen)
                        },
                        onNavigateToSources = {
                            navController.navigate(SettingsSourcesScreen)
                        }
                    )
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

@Composable
fun SettingsTwoPanNavigation(rootNavHostController: NavHostController) {
    val nestedNavController = rememberNavController()
    fun navigateWithPopUpToTopics(route: Any) {
        nestedNavController.navigate(
            route = route,
            navOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(SettingsTopicsScreen) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // re selecting the same item
                launchSingleTop = true
                // Restore state when re selecting a previously selected item
                restoreState = true
            }
        )
    }

    ScreenWithBackButton(
        onBackClick = { rootNavHostController.navigateUp() },
        screen = {
            Row(modifier = Modifier.fillMaxWidth()) {
                SettingMasterRoute(
                    modifier = Modifier.width(400.dp),
                    showSelectedItem = true,
                    onNavigateToTopics = {
                        navigateWithPopUpToTopics(SettingsTopicsScreen)
                    },
                    onNavigateToSources = {
                        navigateWithPopUpToTopics(SettingsSourcesScreen)
                    }
                )

                NavHost(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f),
                    navController = nestedNavController,
                    startDestination = SettingsTopicsScreen
                ) {
                    composable<SettingsTopicsScreen>(
                        enterTransition = { fadeIn() },
                        exitTransition = { fadeOut() }
                    ) {
                        SettingTopicsRoute()
                    }
                    composable<SettingsSourcesScreen>(
                        enterTransition = { fadeIn() },
                        exitTransition = { fadeOut() }
                    ) {
                        SettingSourcesRoute()
                    }
                }
            }
        }
    )
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
                IconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.secondaryVariant,
                        shape = CircleShape
                    ),
                    onClick = onBackClick,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back button",
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            },
            title = {},
            elevation = MaterialTheme.dimension.none
        )
    }) {
        screen()
    }
}