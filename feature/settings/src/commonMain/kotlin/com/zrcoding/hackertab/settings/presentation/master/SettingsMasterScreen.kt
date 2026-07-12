package com.zrcoding.hackertab.settings.presentation.master

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.settings_master_profile_setup_as
import com.zrcoding.hackertab.design.resources.settings_master_row_about
import com.zrcoding.hackertab.design.resources.settings_master_row_appearance
import com.zrcoding.hackertab.design.resources.settings_master_row_sources
import com.zrcoding.hackertab.design.resources.settings_master_row_topics
import com.zrcoding.hackertab.design.resources.settings_master_section_app
import com.zrcoding.hackertab.design.resources.settings_master_section_feed
import com.zrcoding.hackertab.design.resources.settings_master_sources_count
import com.zrcoding.hackertab.design.resources.settings_master_title
import com.zrcoding.hackertab.design.resources.settings_master_topics_count
import com.zrcoding.hackertab.design.resources.settings_master_version_footer
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsMasterRoute(
    viewModel: SettingsMasterViewModel = koinViewModel(),
    onNavigateToTopics: () -> Unit = {},
    onNavigateToSources: () -> Unit = {},
    onNavigateToAppearance: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    appVersion: String = "",
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsMasterScreen(
        state = uiState,
        onNavigateToTopics = onNavigateToTopics,
        onNavigateToSources = onNavigateToSources,
        onNavigateToAppearance = onNavigateToAppearance,
        onNavigateToAbout = onNavigateToAbout,
        appVersion = appVersion,
    )
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETTINGS_MASTER)
}

@Composable
internal fun SettingsMasterScreen(
    state: SettingsMasterUiState,
    onNavigateToTopics: () -> Unit,
    onNavigateToSources: () -> Unit,
    onNavigateToAppearance: () -> Unit,
    onNavigateToAbout: () -> Unit,
    appVersion: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space16))

        Text(
            text = stringResource(Res.string.settings_master_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))

        SettingsProfileCard(
            profile = state.profile,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space24))

        SettingsSectionLabel(text = stringResource(Res.string.settings_master_section_feed))

        SettingsCard {
            SettingsRow(
                label = stringResource(Res.string.settings_master_row_topics),
                caption = if (state.topicsCount > 0) {
                    stringResource(Res.string.settings_master_topics_count, state.topicsCount)
                } else null,
                onClick = onNavigateToTopics,
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space16),
            )
            SettingsRow(
                label = stringResource(Res.string.settings_master_row_sources),
                caption = if (state.sourcesCount > 0) {
                    stringResource(Res.string.settings_master_sources_count, state.sourcesCount)
                } else null,
                onClick = onNavigateToSources,
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))

        SettingsSectionLabel(text = stringResource(Res.string.settings_master_section_app))

        SettingsCard {
            SettingsRow(
                label = stringResource(Res.string.settings_master_row_appearance),
                onClick = onNavigateToAppearance,
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space16),
            )
            SettingsRow(
                label = stringResource(Res.string.settings_master_row_about),
                onClick = onNavigateToAbout,
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space16),
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space40))

        if (appVersion.isNotBlank()) {
            Text(
                text = stringResource(Res.string.settings_master_version_footer, appVersion),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space24))
    }
}

@Composable
private fun SettingsProfileCard(
    profile: Profile?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MaterialTheme.dimension.space12))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(
                horizontal = MaterialTheme.dimension.space16,
                vertical = MaterialTheme.dimension.space16,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = stringResource(Res.string.settings_master_profile_setup_as),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (profile != null) {
                    Spacer(modifier = Modifier.height(MaterialTheme.dimension.space4))
                    Text(
                        text = profile.label.replace("\n", " "),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(
            start = MaterialTheme.dimension.space4,
            bottom = MaterialTheme.dimension.space8,
        ),
    )
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MaterialTheme.dimension.space12))
            .background(MaterialTheme.colorScheme.surface),
    ) {
        content()
    }
}

@Composable
private fun SettingsRow(
    label: String,
    caption: String? = null,
    onClick: () -> Unit,
    showChevron: Boolean = true,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick)
            .padding(
                horizontal = MaterialTheme.dimension.space16,
                vertical = MaterialTheme.dimension.space16,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            if (caption != null) {
                Spacer(modifier = Modifier.height(MaterialTheme.dimension.space2))
                Text(
                    text = caption,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        if (showChevron) {
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(MaterialTheme.dimension.space20),
            )
        }
    }
}

@Preview
@Composable
private fun SettingsMasterScreenLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        SettingsMasterScreen(
            state = SettingsMasterUiState(
                profile = Profile.MOBILE_ENGINEER,
                topicsCount = 12,
                sourcesCount = 4,
                themeMode = ThemeMode.SYSTEM,
            ),
            onNavigateToTopics = {},
            onNavigateToSources = {},
            onNavigateToAppearance = {},
            onNavigateToAbout = {},
            appVersion = "4.0.0",
        )
    }
}

@Preview
@Composable
private fun SettingsMasterScreenDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        SettingsMasterScreen(
            state = SettingsMasterUiState(
                profile = Profile.BACKEND_ENGINEER,
                topicsCount = 8,
                sourcesCount = 6,
                themeMode = ThemeMode.DARK,
            ),
            onNavigateToTopics = {},
            onNavigateToSources = {},
            onNavigateToAppearance = {},
            onNavigateToAbout = {},
            appVersion = "4.0.0",
        )
    }
}
