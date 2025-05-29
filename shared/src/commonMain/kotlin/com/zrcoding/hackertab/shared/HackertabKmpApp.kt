package com.zrcoding.hackertab.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.LocalAnalyticsHelper
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase
import com.zrcoding.hackertab.shared.navigation.MainNavHost
import org.koin.compose.koinInject

@Composable
fun HackertabKmpApp(
    isExpanded: Boolean,
) {
    val startDestinationUseCase = koinInject<GetStartDestinationUseCase>()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    var startDestination by remember { mutableStateOf<GetStartDestinationUseCase.Result?>(null) }

    startDestination?.let { destination ->
        CompositionLocalProvider(LocalAnalyticsHelper provides  analyticsHelper) {
            HackertabTheme {
                Scaffold(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .windowInsetsPadding(WindowInsets.statusBars),
                ) {
                    val navController = rememberNavController()
                    MainNavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        startDestination = destination,
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        startDestination = startDestinationUseCase()
    }
}