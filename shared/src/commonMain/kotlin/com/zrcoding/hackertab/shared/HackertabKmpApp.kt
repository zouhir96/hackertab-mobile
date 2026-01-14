package com.zrcoding.hackertab.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.LocalAnalyticsHelper
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase
import com.zrcoding.hackertab.shared.navigation.MainNavHost
import io.kamel.core.config.Core
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.takeFrom
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.config.animatedImageDecoder
import io.kamel.image.config.imageBitmapDecoder
import org.koin.compose.koinInject

@Composable
fun HackertabKmpApp() {
    val startDestinationUseCase = koinInject<GetStartDestinationUseCase>()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    var setupStatus by remember { mutableStateOf<GetStartDestinationUseCase.Result?>(null) }
    val customKamelConfig = remember {
        KamelConfig {
            takeFrom(KamelConfig.Core)
            animatedImageDecoder()
            imageBitmapDecoder()
        }
    }

    setupStatus?.let { destination ->
        CompositionLocalProvider(
            LocalAnalyticsHelper provides  analyticsHelper,
            LocalKamelConfig provides customKamelConfig
        ) {
            HackertabTheme {
                Scaffold(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .statusBarsPadding(),
                ) {
                    MainNavHost(
                        modifier = Modifier.padding(it),
                        setupStatus = destination,
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        setupStatus = startDestinationUseCase()
    }
}