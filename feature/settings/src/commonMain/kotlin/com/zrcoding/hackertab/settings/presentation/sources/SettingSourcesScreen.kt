package com.zrcoding.hackertab.settings.presentation.sources

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.Icon
import com.zrcoding.hackertab.design.components.states.HackertabSnackbarHost
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.setting_sources_screen_description
import com.zrcoding.hackertab.design.resources.setting_sources_screen_title
import com.zrcoding.hackertab.design.resources.settings_sources_min_one_guard
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import com.zrcoding.hackertab.domain.models.Source as DomainSource

@Composable
fun SettingSourcesRoute(
    viewModel: SettingSourcesViewModel = koinViewModel(),
) {
    val sources by viewModel.viewState.collectAsStateWithLifecycle()
    val minOneViolation by viewModel.minOneSourceViolation.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val minOneMessage = stringResource(Res.string.settings_sources_min_one_guard)

    LaunchedEffect(minOneViolation) {
        if (minOneViolation) {
            snackbarHostState.showSnackbar(minOneMessage)
            viewModel.consumeMinOneSourceViolation()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SettingSourcesScreen(
            sources = sources,
            onChipClicked = viewModel::onChipClicked,
        )
        HackertabSnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(ScaffoldDefaults.contentWindowInsets.asPaddingValues()),
        )
    }

    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETTINGS_SOURCES)
}

@Composable
private fun SettingSourcesScreen(
    sources: PersistentList<ChipData>,
    onChipClicked: (ChipData) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = MaterialTheme.dimension.space16)
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal),
    ) {
        Text(
            text = stringResource(Res.string.setting_sources_screen_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
        Text(
            text = stringResource(Res.string.setting_sources_screen_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
        ) {
            sources.forEachIndexed { index, chipData ->
                val domainSource = DomainSource.fromId(chipData.id)
                SourceRow(
                    chipData = chipData,
                    supportsFilters = domainSource?.supportsFilters ?: true,
                    onToggle = onChipClicked,
                )
                if (index < sources.lastIndex) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = MaterialTheme.dimension.space16),
                    )
                }
            }
        }
    }
}

@Composable
private fun SourceRow(
    chipData: ChipData,
    supportsFilters: Boolean,
    onToggle: (ChipData) -> Unit,
) {
    val domainSource = DomainSource.fromId(chipData.id)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(role = Role.Switch, onClick = { onToggle(chipData) })
            .padding(
                horizontal = MaterialTheme.dimension.space16,
                vertical = MaterialTheme.dimension.space12,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
    ) {
        // Brand icon block
        if (domainSource != null) {
            domainSource.Icon(size = 32.dp)
        } else {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = chipData.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = if (supportsFilters) "Filterable by topic" else "Source-wide",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Switch(
            checked = chipData.selected,
            onCheckedChange = { onToggle(chipData) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        )
    }
}

// region Previews

@Preview
@Composable
private fun SettingSourcesScreenLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        SettingSourcesScreen(
            sources = DomainSource.entries.mapIndexed { i, s ->
                s.toChipDataPreview(selected = i % 3 != 0)
            }.toPersistentList(),
            onChipClicked = {},
        )
    }
}

@Preview
@Composable
private fun SettingSourcesScreenDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        SettingSourcesScreen(
            sources = DomainSource.entries.mapIndexed { i, s ->
                s.toChipDataPreview(selected = i % 2 == 0)
            }.toPersistentList(),
            onChipClicked = {},
        )
    }
}

private fun DomainSource.toChipDataPreview(selected: Boolean) = ChipData(
    id = id,
    name = label,
    analyticsTag = analyticsTag,
    image = null,
    selected = selected,
)

// endregion
