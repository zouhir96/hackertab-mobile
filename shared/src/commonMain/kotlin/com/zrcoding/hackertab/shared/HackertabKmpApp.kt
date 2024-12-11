package com.zrcoding.hackertab.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.shared.navigation.MainNavHost

@Composable
fun HackertabKmpApp(
    isExpanded: Boolean
) {
    HackertabTheme {
        Surface(
           modifier = Modifier
               .background(MaterialTheme.colors.background)
               .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            val navController = rememberNavController()
            MainNavHost(
                navController = navController,
                isExpandedScree = isExpanded
            )
        }
    }
}