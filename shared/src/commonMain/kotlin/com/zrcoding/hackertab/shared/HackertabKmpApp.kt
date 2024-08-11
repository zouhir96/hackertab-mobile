package com.zrcoding.hackertab.shared

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.shared.navigation.HomeScreen
import com.zrcoding.hackertab.shared.navigation.MainNavigator

@Composable
fun HackertabKmpApp(
    isExpanded: Boolean
) {
    HackertabTheme {
        Surface {
            MainNavigator(
                startDestination = HomeScreen(),
                isExpandedScree = isExpanded
            )
        }
    }
}