package com.zrcoding.hackertab.settings.presentation.appearance

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
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
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.settings_appearance_dark
import com.zrcoding.hackertab.design.resources.settings_appearance_description
import com.zrcoding.hackertab.design.resources.settings_appearance_light
import com.zrcoding.hackertab.design.resources.settings_appearance_system
import com.zrcoding.hackertab.design.resources.settings_appearance_title
import com.zrcoding.hackertab.design.theme.DarkBg
import com.zrcoding.hackertab.design.theme.DarkSurface
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.LightBg
import com.zrcoding.hackertab.design.theme.LightSurface
import com.zrcoding.hackertab.design.theme.Neutral400
import com.zrcoding.hackertab.design.theme.Neutral900
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsAppearanceRoute(
    viewModel: SettingsAppearanceViewModel = koinViewModel(),
) {
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    SettingsAppearanceScreen(
        selectedMode = themeMode,
        onSelectMode = viewModel::setThemeMode,
    )
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETTINGS_APPEARANCE)
}

@Composable
internal fun SettingsAppearanceScreen(
    selectedMode: ThemeMode,
    onSelectMode: (ThemeMode) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space16))

        Text(
            text = stringResource(Res.string.settings_appearance_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
        Text(
            text = stringResource(Res.string.settings_appearance_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space24))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
        ) {
            ThemePreviewTile(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.settings_appearance_light),
                isDark = false,
                isSelected = selectedMode == ThemeMode.LIGHT,
                onClick = { onSelectMode(ThemeMode.LIGHT) },
            )
            ThemePreviewTile(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.settings_appearance_dark),
                isDark = true,
                isSelected = selectedMode == ThemeMode.DARK,
                onClick = { onSelectMode(ThemeMode.DARK) },
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space16))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MaterialTheme.dimension.space12))
                .background(MaterialTheme.colorScheme.surface),
        ) {
            ThemeOptionRow(
                labelRes = Res.string.settings_appearance_light,
                isSelected = selectedMode == ThemeMode.LIGHT,
                onClick = { onSelectMode(ThemeMode.LIGHT) },
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space16),
            )
            ThemeOptionRow(
                labelRes = Res.string.settings_appearance_dark,
                isSelected = selectedMode == ThemeMode.DARK,
                onClick = { onSelectMode(ThemeMode.DARK) },
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space16),
            )
            ThemeOptionRow(
                labelRes = Res.string.settings_appearance_system,
                isSelected = selectedMode == ThemeMode.SYSTEM,
                onClick = { onSelectMode(ThemeMode.SYSTEM) },
            )
        }
    }
}

@Composable
private fun ThemePreviewTile(
    modifier: Modifier = Modifier,
    label: String,
    isDark: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val bg = if (isDark) DarkBg else LightBg
    val surface = if (isDark) DarkSurface else LightSurface
    if (isDark) Neutral400 else Neutral900

    val borderColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(MaterialTheme.dimension.space12))
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(MaterialTheme.dimension.space12),
            )
            .clickable(role = Role.RadioButton, onClick = onClick)
            .semantics { selected = isSelected }
            .padding(MaterialTheme.dimension.space12),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(MaterialTheme.dimension.space8))
                .background(bg),
        ) {
            Column(modifier = Modifier.padding(MaterialTheme.dimension.space8)) {
                Box(
                    modifier = Modifier
                        .height(MaterialTheme.dimension.space8)
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(MaterialTheme.dimension.space4))
                        .background(surface),
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimension.space6))
                Box(
                    modifier = Modifier
                        .height(MaterialTheme.dimension.space6)
                        .fillMaxWidth(0.5f)
                        .clip(RoundedCornerShape(MaterialTheme.dimension.space4))
                        .background(surface),
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimension.space6))
                Box(
                    modifier = Modifier
                        .height(MaterialTheme.dimension.space6)
                        .fillMaxWidth(0.4f)
                        .clip(RoundedCornerShape(MaterialTheme.dimension.space4))
                        .background(surface),
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun ThemeOptionRow(
    labelRes: StringResource,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.RadioButton, onClick = onClick)
            .semantics { selected = isSelected }
            .padding(
                horizontal = MaterialTheme.dimension.space16,
                vertical = MaterialTheme.dimension.space16,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(labelRes),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(MaterialTheme.dimension.space20),
            )
        }
    }
}

@Preview
@Composable
private fun SettingsAppearanceScreenLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        SettingsAppearanceScreen(
            selectedMode = ThemeMode.LIGHT,
            onSelectMode = {},
        )
    }
}

@Preview
@Composable
private fun SettingsAppearanceScreenDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        SettingsAppearanceScreen(
            selectedMode = ThemeMode.DARK,
            onSelectMode = {},
        )
    }
}
