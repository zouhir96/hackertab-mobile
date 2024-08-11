package com.zrcoding.hackertab.settings.presentation.topics

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
fun SettingTopicsRoute(
    viewModel: SettingTopicsScreenViewModel = koinInject(),
) {
    val state = viewModel.viewState.collectAsState().value
    SettingTopicsScreen(state, viewModel::onChipClicked)
}

@Composable
fun SettingTopicsScreen(
    state: SettingTopicsScreenViewState,
    onChipClicked: (ChipData) -> Unit
) {
    SettingScreen(
        title = R.string.setting_topics_screen_title,
        description = R.string.setting_topics_screen_description
    ) {
        TopicsChipGroup(
            topics = state.topics,
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
                ChipData(id = "1", name = "Android"),
                ChipData(id = "2", name = "Artificial intelligence"),
                ChipData(id = "3", name = "C"),
                ChipData(id = "4", name = "C++"),
                ChipData(id = "5", name = "C#", selected = true),
                ChipData(id = "6", name = "Dart"),
                ChipData(id = "7", name = "Data science"),
                ChipData(id = "8", name = "Devops"),
                ChipData(id = "9", name = "Elixir"),
                ChipData(id = "10", name = "Erlang"),
                ChipData(id = "11", name = "Go"),
            )
        )
    }
    HackertabTheme {
        SettingTopicsScreen(
            SettingTopicsScreenViewState(topics = topics),
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
fun TopicsChipGroup(
    topics: List<ChipData>,
    onChipClicked: (ChipData) -> Unit
) {
    ChipGroup(
        chips = topics,
        onChipClicked = onChipClicked
    )
}

@Preview(showBackground = true)
@Composable
fun TopicsChipGroupPreview() {
    var topics by remember {
        mutableStateOf(
            listOf(
                ChipData(id = "1", name = "Android"),
                ChipData(id = "2", name = "Artificial intelligence"),
                ChipData(id = "5", name = "C#", selected = true),
            )
        )
    }
    HackertabTheme {
        TopicsChipGroup(
            topics = topics,
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