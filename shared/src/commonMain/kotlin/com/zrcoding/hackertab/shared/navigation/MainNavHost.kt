package com.zrcoding.hackertab.shared.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
import com.zrcoding.hackertab.home.presentation.HomeRoute
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
    isExpandedScree: Boolean
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreen
    ) {
        composable<HomeScreen> {
            HomeRoute(
                isExpandedScreen = isExpandedScree,
                onNavigateToSettings = {
                    navController.navigate(Settings)
                }
            )
        }
        if (isExpandedScree) {
            composable<Settings> {
                SettingsTwoPanNavigation()
            }
        } else {
            settingsOnePanNavigation(navController)
        }
    }
}

fun NavGraphBuilder.settingsOnePanNavigation(navController: NavHostController) {
    navigation<Settings>(startDestination = SettingsMasterScreen) {
        composableWithAnimation<SettingsMasterScreen>{
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
        composableWithAnimation<SettingsTopicsScreen> {
            SettingTopicsRoute()
        }
        composableWithAnimation<SettingsSourcesScreen> {
            SettingSourcesRoute()
        }
    }
}

@Composable
fun SettingsTwoPanNavigation() {
    val navController = rememberNavController()
    fun navigateWithPopUpToTopics(route: Any) {
        navController.navigate(
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
            navController = navController,
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

private inline fun <reified T: Any> NavGraphBuilder.composableWithAnimation(
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