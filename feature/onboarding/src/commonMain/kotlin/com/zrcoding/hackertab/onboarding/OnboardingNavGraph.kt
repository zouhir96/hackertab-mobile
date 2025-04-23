package com.zrcoding.hackertab.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase.ScreenToComplete
import com.zrcoding.hackertab.onboarding.profile.SetupProfileRoute
import com.zrcoding.hackertab.onboarding.sources.SetupSourcesRoute
import com.zrcoding.hackertab.onboarding.topics.SetupTopicsRoute
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingScreen

@Serializable
data class SetupProfileScreen(val newUser: Boolean)

@Serializable
data object SetupTopicsScreen

@Serializable
data object SetupSourcesScreen

fun ScreenToComplete.toDestination(): Any = when (this) {
    is ScreenToComplete.ProfileSetup -> SetupProfileScreen(newUser)
    ScreenToComplete.TopicsSetup -> SetupTopicsScreen
    ScreenToComplete.SourcesSetup -> SetupSourcesScreen
}

fun NavGraphBuilder.onboardingNavGraph(
    screensToComplete: List<ScreenToComplete>,
    navController: NavHostController,
    navigateToHome: (Any) -> Unit
) {
    navigation<OnboardingScreen>(startDestination = screensToComplete.first().toDestination()) {
        val setupProfileScreen = screensToComplete.firstOrNull { it is ScreenToComplete.ProfileSetup }?.toDestination()
        if (setupProfileScreen != null) {
            composable<SetupProfileScreen> {
                SetupProfileRoute(
                    navigateToNextScreen = {
                        if (screensToComplete.contains(ScreenToComplete.TopicsSetup)) {
                            navController.navigate(SetupTopicsScreen)
                        } else if (screensToComplete.contains(ScreenToComplete.SourcesSetup)) {
                            navController.navigate(SetupSourcesScreen)
                        } else {
                            navigateToHome(setupProfileScreen)
                        }
                    }
                )
            }
            if (screensToComplete.contains(ScreenToComplete.TopicsSetup)) {
                composable<SetupTopicsScreen> {
                    SetupTopicsRoute(
                        navigateToNextScreen = {
                            if (screensToComplete.contains(ScreenToComplete.SourcesSetup)) {
                                navController.navigate(SetupSourcesScreen)
                            } else {
                                val popupTo =
                                    if (screensToComplete.any { it is ScreenToComplete.ProfileSetup }) {
                                        setupProfileScreen
                                    } else SetupTopicsScreen
                                navigateToHome(popupTo)
                            }
                        }
                    )
                }
            }
            if (screensToComplete.contains(ScreenToComplete.SourcesSetup)) {
                composable<SetupSourcesScreen> {
                    SetupSourcesRoute(
                        navigateToNextScreen = {
                            val popupTo =
                                if (screensToComplete.any { it is ScreenToComplete.ProfileSetup }) {
                                    setupProfileScreen
                                } else if (screensToComplete.contains(ScreenToComplete.TopicsSetup)) {
                                    SetupTopicsScreen
                                } else SetupSourcesScreen
                            navigateToHome(popupTo)
                        }
                    )
                }
            }
        }
    }
}