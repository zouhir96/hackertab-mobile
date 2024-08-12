package com.zrcoding.hackertab.shared.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_baseline_arrow_back
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.home.presentation.HomeRoute
import com.zrcoding.hackertab.settings.presentation.master.SettingMasterRoute
import com.zrcoding.hackertab.settings.presentation.sources.SettingSourcesRoute
import com.zrcoding.hackertab.settings.presentation.topics.SettingTopicsRoute
import org.jetbrains.compose.resources.painterResource

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
        Column {
            SettingsTopBar { navigator.pop() }
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
}

class SettingsTopicsScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column {
            SettingsTopBar { navigator.pop() }
            SettingTopicsRoute()
        }
    }
}

class SettingsSourcesScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column {
            SettingsTopBar { navigator.pop() }
            SettingSourcesRoute()
        }
    }
}

@Composable
fun SettingsTopBar(
    onBackClicked: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = MaterialTheme.dimension.none
    ) {
        Button(
            onClick = onBackClicked,
            modifier = Modifier.size(MaterialTheme.dimension.extraBig),
            shape = MaterialTheme.shapes.large,
            contentPadding = PaddingValues(MaterialTheme.dimension.none),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.onBackground
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_baseline_arrow_back),
                contentDescription = "back button",
            )
        }
    }
}