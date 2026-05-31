package com.zrcoding.hackertab.shared.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.LocalAnalyticsHelper
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.shared.navigation.MainNavHost
import io.kamel.core.config.Core
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.takeFrom
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.config.animatedImageDecoder
import io.kamel.image.config.imageBitmapDecoder
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HackertabApp(
    viewModel: AppViewModel = koinViewModel()
) {
    val analyticsHelper = koinInject<AnalyticsHelper>()
    val customKamelConfig = remember {
        KamelConfig {
            takeFrom(KamelConfig.Core)
            animatedImageDecoder()
            imageBitmapDecoder()
        }
    }

    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    viewState.setupStatus?.let { destination ->
        CompositionLocalProvider(
            LocalAnalyticsHelper provides analyticsHelper,
            LocalKamelConfig provides customKamelConfig
        ) {
            HackertabTheme(themeMode = viewState.themeMode) {
                MainNavHost(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .statusBarsPadding(),
                    setupStatus = destination,
                )
            }
        }
    }
}
