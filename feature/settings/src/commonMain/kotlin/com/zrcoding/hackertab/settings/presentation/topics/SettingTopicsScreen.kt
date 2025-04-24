package com.zrcoding.hackertab.settings.presentation.topics

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.ChipGroup
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.setting_topics_screen_description
import com.zrcoding.hackertab.design.resources.setting_topics_screen_title
import com.zrcoding.hackertab.settings.presentation.common.SettingScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingTopicsRoute(
    viewModel: SettingTopicsScreenViewModel = koinViewModel(),
) {
    val topics = viewModel.viewState.collectAsStateWithLifecycle().value
    SettingScreen(
        title = Res.string.setting_topics_screen_title,
        description = Res.string.setting_topics_screen_description
    ) {
        ChipGroup(
            chips = topics,
            onChipClicked = viewModel::onChipClicked
        )
    }
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETTINGS_TOPICS)
}