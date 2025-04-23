package com.zrcoding.hackertab.settings.presentation.sources

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.design.components.ChipGroup
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.setting_sources_screen_description
import com.zrcoding.hackertab.design.resources.setting_sources_screen_title
import com.zrcoding.hackertab.settings.presentation.common.SettingScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingSourcesRoute(
    viewModel: SettingSourcesScreenViewModel = koinViewModel(),
) {
    val sources = viewModel.viewState.collectAsStateWithLifecycle().value
    SettingScreen(
        title = Res.string.setting_sources_screen_title,
        description = Res.string.setting_sources_screen_description
    ) {
        ChipGroup(
            chips = sources,
            onChipClicked = viewModel::onChipClicked
        )
    }
}