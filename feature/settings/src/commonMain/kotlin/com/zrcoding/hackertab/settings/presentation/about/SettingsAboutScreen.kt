package com.zrcoding.hackertab.settings.presentation.about

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.asPaddingValues
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
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHostState
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
import com.zrcoding.hackertab.design.components.states.HackertabSnackbarHost
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.settings_about_app_name
import com.zrcoding.hackertab.design.resources.settings_about_row_feedback
import com.zrcoding.hackertab.design.resources.settings_about_row_privacy
import com.zrcoding.hackertab.design.resources.settings_about_row_rate
import com.zrcoding.hackertab.design.resources.settings_about_row_show_tour
import com.zrcoding.hackertab.design.resources.settings_about_row_source_code
import com.zrcoding.hackertab.design.resources.settings_about_title
import com.zrcoding.hackertab.design.resources.settings_about_tour_reset_confirmation
import com.zrcoding.hackertab.design.resources.settings_about_version
import com.zrcoding.hackertab.design.theme.BrandPrimary
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

// TODO Wave 4: register SettingsAboutScreen in MainNavHost

@Composable
fun SettingsAboutRoute(
    viewModel: SettingsAboutViewModel = koinViewModel(),
    appVersion: String = "",
    onSendFeedback: () -> Unit = {},
    onOpenSourceCode: () -> Unit = {},
    onOpenPrivacy: () -> Unit = {},
    onRateApp: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val tourResetMessage = stringResource(Res.string.settings_about_tour_reset_confirmation)

    LaunchedEffect(uiState.tourResetDone) {
        if (uiState.tourResetDone) {
            snackbarHostState.showSnackbar(tourResetMessage)
            viewModel.consumeTourResetConfirmation()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SettingsAboutScreen(
            appVersion = appVersion,
            onSendFeedback = onSendFeedback,
            onOpenSourceCode = onOpenSourceCode,
            onOpenPrivacy = onOpenPrivacy,
            onRateApp = onRateApp,
            onShowTourAgain = viewModel::resetCoachmarks,
        )
        HackertabSnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(ScaffoldDefaults.contentWindowInsets.asPaddingValues()),
        )
    }

    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETTINGS_ABOUT)
}

@Composable
internal fun SettingsAboutScreen(
    appVersion: String,
    onSendFeedback: () -> Unit,
    onOpenSourceCode: () -> Unit,
    onOpenPrivacy: () -> Unit,
    onRateApp: () -> Unit,
    onShowTourAgain: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space16))

        Text(
            text = stringResource(Res.string.settings_about_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space24))

        // Identity card with logo + version
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(MaterialTheme.dimension.space20),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Logo placeholder — 40dp branded block
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = BrandPrimary,
                                shape = RoundedCornerShape(6.dp),
                            )
                    )
                    Text(
                        text = stringResource(Res.string.settings_about_app_name),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                if (appVersion.isNotBlank()) {
                    Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
                    Text(
                        text = stringResource(Res.string.settings_about_version, appVersion),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))

        // Link rows
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface),
        ) {
            AboutRow(
                label = stringResource(Res.string.settings_about_row_feedback),
                onClick = onSendFeedback,
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space16),
            )
            AboutRow(
                label = stringResource(Res.string.settings_about_row_source_code),
                onClick = onOpenSourceCode,
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space16),
            )
            AboutRow(
                label = stringResource(Res.string.settings_about_row_privacy),
                onClick = onOpenPrivacy,
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space16),
            )
            AboutRow(
                label = stringResource(Res.string.settings_about_row_rate),
                onClick = onRateApp,
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space16))

        // Show tour again row — Issue 15 fix
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface),
        ) {
            AboutRow(
                label = stringResource(Res.string.settings_about_row_show_tour),
                onClick = onShowTourAgain,
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space40))
    }
}

@Composable
private fun AboutRow(
    label: String,
    onClick: () -> Unit,
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
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp),
        )
    }
}

// region Previews

@Preview
@Composable
private fun SettingsAboutScreenLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        SettingsAboutScreen(
            appVersion = "4.0.0",
            onSendFeedback = {},
            onOpenSourceCode = {},
            onOpenPrivacy = {},
            onRateApp = {},
            onShowTourAgain = {},
        )
    }
}

@Preview
@Composable
private fun SettingsAboutScreenDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        SettingsAboutScreen(
            appVersion = "4.0.0",
            onSendFeedback = {},
            onOpenSourceCode = {},
            onOpenPrivacy = {},
            onRateApp = {},
            onShowTourAgain = {},
        )
    }
}

// endregion
