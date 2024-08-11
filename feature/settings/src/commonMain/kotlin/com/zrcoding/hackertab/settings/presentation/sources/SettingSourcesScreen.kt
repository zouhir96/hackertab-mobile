package com.zrcoding.hackertab.settings.presentation.sources

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.ChipGroup
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.settings.R
import com.zrcoding.hackertab.settings.presentation.common.SettingScreen
import org.koin.compose.koinInject

@Composable
fun SettingSourcesRoute(
    viewModel: SettingSourcesScreenViewModel = koinInject(),
) {
    val state = viewModel.viewState.collectAsState().value
    SettingSourcesScreen(state, viewModel::onChipClicked)
}

@Composable
fun SettingSourcesScreen(
    state: SettingSourcesScreenViewState,
    onChipClicked: (ChipData) -> Unit
) {
    SettingScreen(
        title = R.string.setting_sources_screen_title,
        description = R.string.setting_sources_screen_description
    ) {
        SourcesChipGroup(
            sources = state.sources,
            onChipClicked = onChipClicked
        )
    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SettingTopicsScreenPreview() {
    var topics by remember {
        mutableStateOf(
            listOf(
                ChipData(id = "1", name = "Github repositories", image = R.drawable.ic_github),
                ChipData(id = "2", name = "Reddit", R.drawable.ic_reddit),
                ChipData(
                    id = "3",
                    name = "FreeCodeCamo",
                    R.drawable.ic_freecodecamp,
                    selected = true
                ),
                ChipData(id = "4", name = "Hackernews", R.drawable.ic_hackernews),
                ChipData(id = "5", name = "Upcoming events", R.drawable.ic_conferences),
                ChipData(id = "6", name = "Product Hunt", R.drawable.ic_product_hunt),
                ChipData(id = "7", name = "Lobsters", R.drawable.ic_lobsters),
                ChipData(id = "8", name = "Hashnode", R.drawable.ic_hashnode),
                ChipData(id = "9", name = "IndieHackers", R.drawable.ic_indie_hackers),
                ChipData(id = "10", name = "Medium", R.drawable.ic_medium),
                ChipData(id = "11", name = "Powered by AI", R.drawable.ic_ai),
                ChipData(id = "12", name = "Devto", R.drawable.ic_devto),
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

@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun TopicsChipGroupPreview() {
    var sources by remember {
        mutableStateOf(
            listOf(
                ChipData(id = "1", name = "Github repositories", image = R.drawable.ic_github),
                ChipData(id = "2", name = "Reddit", R.drawable.ic_reddit),
                ChipData(
                    id = "3",
                    name = "FreeCodeCamo",
                    R.drawable.ic_freecodecamp,
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