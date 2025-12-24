package com.zrcoding.hackertab.settings.presentation.topics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.ChipGroup
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.setting_topics_screen_description
import com.zrcoding.hackertab.design.resources.setting_topics_screen_title
import com.zrcoding.hackertab.design.theme.dimension
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
        var expandedCategory by remember { mutableStateOf<String?>(null) }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = MaterialTheme.dimension.extraBig),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium)
        ) {
            items(
                items = topics,
                key = { it.first }
            ) { (category, chips) ->
                val expanded by derivedStateOf { expandedCategory == category }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.small)
                ) {
                    Row(
                        modifier = Modifier.clickable { expandedCategory = category },
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = category.capitalize(Locale("en")),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onBackground
                        )
                        Icon(
                            imageVector = if (expanded) {
                                Icons.Default.ArrowDropUp
                            } else Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                    AnimatedVisibility(visible = expanded) {
                        ChipGroup(
                            chips = chips,
                            onChipClicked = viewModel::onChipClicked
                        )
                    }
                }
            }
        }

        LaunchedEffect(topics) {
            if (expandedCategory == null && topics.isNotEmpty()) {
                expandedCategory = topics.firstOrNull()?.first
            }
        }
    }
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETTINGS_TOPICS)
}