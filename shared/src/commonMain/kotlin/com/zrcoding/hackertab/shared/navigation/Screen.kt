package com.zrcoding.hackertab.shared.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.zrcoding.hackertab.home.presentation.HomeRoute
import com.zrcoding.hackertab.settings.presentation.master.SettingMasterRoute
import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesRoute
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsRoute

class HomeScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        HomeRoute(
            isExpandedScree = false,
            onNavigateToSettings = {
                navigator.push(SettingsMasterScreen())
            }
        )
    }
}

class SettingsMasterScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        SettingMasterRoute(
            showSelectedItem = false,
            onNavigateToTopics = {
                navigator.push(SettingsTopicsScreen())
            },
            onNavigateToSources = {
                navigator.push(SettingsSourcesScreen())
            },
        )
    }
}

class SettingsTopicsScreen: Screen {

    @Composable
    override fun Content() {
        SettingTopicsRoute()
    }
}

class SettingsSourcesScreen: Screen {

    @Composable
    override fun Content() {
        SettingSourcesRoute()
    }
}