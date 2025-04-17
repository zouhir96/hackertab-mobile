package com.zrcoding.hackertab.settings.presentation.sources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.ChipGroup
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_ai
import com.zrcoding.hackertab.design.resources.ic_conferences
import com.zrcoding.hackertab.design.resources.ic_devto
import com.zrcoding.hackertab.design.resources.ic_freecodecamp
import com.zrcoding.hackertab.design.resources.ic_github
import com.zrcoding.hackertab.design.resources.ic_hackernews
import com.zrcoding.hackertab.design.resources.ic_hashnode
import com.zrcoding.hackertab.design.resources.ic_indie_hackers
import com.zrcoding.hackertab.design.resources.ic_lobsters
import com.zrcoding.hackertab.design.resources.ic_medium
import com.zrcoding.hackertab.design.resources.ic_product_hunt
import com.zrcoding.hackertab.design.resources.ic_reddit
import com.zrcoding.hackertab.design.resources.setting_sources_screen_description
import com.zrcoding.hackertab.design.resources.setting_sources_screen_title
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.settings.presentation.common.SettingScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingSourcesRoute(
    viewModel: SettingSourcesScreenViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value
    SettingSourcesScreen(state, viewModel::onChipClicked)
}

@Composable
fun SettingSourcesScreen(
    state: SettingSourcesScreenViewState,
    onChipClicked: (ChipData) -> Unit
) {
    SettingScreen(
        title = Res.string.setting_sources_screen_title,
        description = Res.string.setting_sources_screen_description
    ) {
        SourcesChipGroup(
            sources = state.sources,
            onChipClicked = onChipClicked
        )
    }
}

@Preview
@Composable
fun SettingTopicsScreenPreview() {
    var topics by remember {
        mutableStateOf(
            listOf(
                ChipData(id = "1", name = "Github repositories", image = Res.drawable.ic_github),
                ChipData(id = "2", name = "Reddit", Res.drawable.ic_reddit),
                ChipData(
                    id = "3",
                    name = "FreeCodeCamo",
                    Res.drawable.ic_freecodecamp,
                    selected = true
                ),
                ChipData(id = "4", name = "Hackernews", Res.drawable.ic_hackernews),
                ChipData(id = "5", name = "Upcoming events", Res.drawable.ic_conferences),
                ChipData(id = "6", name = "Product Hunt", Res.drawable.ic_product_hunt),
                ChipData(id = "7", name = "Lobsters", Res.drawable.ic_lobsters),
                ChipData(id = "8", name = "Hashnode", Res.drawable.ic_hashnode),
                ChipData(id = "9", name = "IndieHackers", Res.drawable.ic_indie_hackers),
                ChipData(id = "10", name = "Medium", Res.drawable.ic_medium),
                ChipData(id = "11", name = "Powered by AI", Res.drawable.ic_ai),
                ChipData(id = "12", name = "Devto", Res.drawable.ic_devto),
            )
        )
    }
    HackertabTheme {
        SettingSourcesScreen(
            SettingSourcesScreenViewState(sources = topics),
            onChipClicked = {
                val indexOf = topics.indexOf(it)
                topics = topics.toMutableList().apply {
                    set(
                        indexOf,
                        it.copy(selected = it.selected.not())
                    )
                }.toList()
            }
        )
    }
}

@Composable
fun SourcesChipGroup(
    sources: List<ChipData>,
    onChipClicked: (ChipData) -> Unit
) {
    ChipGroup(
        chips = sources,
        onChipClicked = onChipClicked
    )
}

@Preview
@Composable
fun TopicsChipGroupPreview() {
    var sources by remember {
        mutableStateOf(
            listOf(
                ChipData(id = "1", name = "Github repositories", image = Res.drawable.ic_github),
                ChipData(id = "2", name = "Reddit", Res.drawable.ic_reddit),
                ChipData(
                    id = "3",
                    name = "FreeCodeCamo",
                    Res.drawable.ic_freecodecamp,
                    selected = true
                ),
            )
        )
    }
    HackertabTheme {
        SourcesChipGroup(
            sources = sources,
            onChipClicked = {
                val indexOf = sources.indexOf(it)
                sources = sources.toMutableList().apply {
                    set(
                        indexOf,
                        it.copy(selected = it.selected.not())
                    )
                }.toList()
            }
        )
    }
}