package com.zrcoding.hackertab.shared.navigation

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.zrcoding.hackertab.design.theme.HackertabTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

const val TRANSITION_DURATION = 400

@Composable
fun MainNavigator(
    startDestination: Screen,
    isExpandedScree: Boolean
) {
    Navigator(startDestination)
}

@Preview
@Composable
fun MainNavigatorPreview() {
    HackertabTheme {
        Surface {
            MainNavigator(
                startDestination = HomeScreen(),
                isExpandedScree = false
            )
        }
    }
}