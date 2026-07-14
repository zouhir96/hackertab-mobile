package com.zrcoding.hackertab.settings.presentation.about

import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.states.HackertabSnackbarHost
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.common_ok
import com.zrcoding.hackertab.design.resources.img_logo
import com.zrcoding.hackertab.design.resources.settings_about_app_name
import com.zrcoding.hackertab.design.resources.settings_about_row_feedback
import com.zrcoding.hackertab.design.resources.settings_about_row_privacy
import com.zrcoding.hackertab.design.resources.settings_about_row_rate
import com.zrcoding.hackertab.design.resources.settings_about_row_show_tour
import com.zrcoding.hackertab.design.resources.settings_about_row_source_code
import com.zrcoding.hackertab.design.resources.settings_about_tour_reset_confirmation
import com.zrcoding.hackertab.design.resources.settings_about_version
import com.zrcoding.hackertab.design.resources.support_device_model
import com.zrcoding.hackertab.design.resources.support_device_os_version
import com.zrcoding.hackertab.design.resources.support_email
import com.zrcoding.hackertab.design.resources.support_email_subject
import com.zrcoding.hackertab.design.resources.support_no_apps_description
import com.zrcoding.hackertab.design.resources.support_no_apps_title
import com.zrcoding.hackertab.design.resources.support_support_footer_message
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.common.AppConfig
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

private const val SOURCE_CODE_URL = "https://github.com/zouhir96/hackertab-mobile"
private const val PRIVACY_POLICY_URL =
    "https://shining-brian-b1d.notion.site/Hackertab-mobile-Privacy-Policy-174032fc1bdf80478101edcb6e86e782"

@Composable
fun SettingsAboutRoute(
    onNavigateToWebView: (String) -> Unit,
    viewModel: SettingsAboutViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val tourResetMessage = stringResource(Res.string.settings_about_tour_reset_confirmation)

    val contactSupport: ContactSupport = koinInject()
    val appConfig: AppConfig = koinInject()
    val uriHandler = LocalUriHandler.current
    val contactSupportData = ContactSupportData(
        email = stringResource(Res.string.support_email),
        subject = stringResource(Res.string.support_email_subject),
        footerMessage = stringResource(Res.string.support_support_footer_message),
        osVersion = stringResource(Res.string.support_device_os_version),
        deviceModel = stringResource(Res.string.support_device_model),
        appVersion = appConfig.versionName,
        noAppFoundTitle = stringResource(Res.string.support_no_apps_title),
        noAppFoundDescription = stringResource(Res.string.support_no_apps_description),
        noAppFoundOk = stringResource(Res.string.common_ok),
    )

    LaunchedEffect(uiState.tourResetDone) {
        if (uiState.tourResetDone) {
            snackbarHostState.showSnackbar(tourResetMessage)
            viewModel.consumeTourResetConfirmation()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SettingsAboutScreen(
            appVersion = appConfig.versionName,
            onSendFeedback = { contactSupport.invoke(data = contactSupportData) },
            onOpenSourceCode = { uriHandler.openUri(SOURCE_CODE_URL) },
            onOpenPrivacy = { onNavigateToWebView(PRIVACY_POLICY_URL) },
            onRateApp = { uriHandler.openUri(appConfig.storeUrl) },
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space16))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MaterialTheme.dimension.space12))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(MaterialTheme.dimension.space20),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
                ) {
                    Image(
                        modifier = Modifier.size(MaterialTheme.dimension.space24),
                        painter = painterResource(Res.drawable.img_logo),
                        contentDescription = null
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MaterialTheme.dimension.space12))
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MaterialTheme.dimension.space12))
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
            modifier = Modifier.size(MaterialTheme.dimension.space20),
        )
    }
}

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
