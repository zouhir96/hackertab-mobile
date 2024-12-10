package com.zrcoding.hackertab.settings.presentation.master

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.common_ok
import com.zrcoding.hackertab.design.resources.ic_baseline_arrow_forward
import com.zrcoding.hackertab.design.resources.setting_master_screen_contact_us
import com.zrcoding.hackertab.design.resources.setting_master_screen_sources
import com.zrcoding.hackertab.design.resources.setting_master_screen_topics
import com.zrcoding.hackertab.design.resources.setting_master_screen_version_name
import com.zrcoding.hackertab.design.resources.support_device_model
import com.zrcoding.hackertab.design.resources.support_device_os_version
import com.zrcoding.hackertab.design.resources.support_email
import com.zrcoding.hackertab.design.resources.support_email_subject
import com.zrcoding.hackertab.design.resources.support_no_apps_description
import com.zrcoding.hackertab.design.resources.support_no_apps_title
import com.zrcoding.hackertab.design.resources.support_support_footer_message
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.settings.presentation.common.AppConfig
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun SettingMasterRoute(
    modifier: Modifier = Modifier,
    showSelectedItem: Boolean,
    onNavigateToTopics: () -> Unit,
    onNavigateToSources: () -> Unit
) {
    val contactSupport: ContactSupport = koinInject()
    val appConfig: AppConfig = koinInject()
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
    Box(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(
                top = MaterialTheme.dimension.extraBig,
                bottom = MaterialTheme.dimension.default
            )
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.large)
        ) {
            // TODO Waiting for compose multiplatform to support BackHandler
            //  so we can change the selectedItem to 0 when the user clicks on the Tablet back button and the selected item is 0.
            var selectedItem by remember {
                mutableIntStateOf(
                    if (showSelectedItem) {
                        0
                    } else -1
                )
            }
            SettingItemsContainer {
                SettingItem(
                    text = Res.string.setting_master_screen_topics,
                    selected = selectedItem == 0,
                    onClick = {
                        if (showSelectedItem) {
                            selectedItem = 0
                        }
                        onNavigateToTopics()
                    }
                )
                SettingItem(
                    text = Res.string.setting_master_screen_sources,
                    selected = selectedItem == 1,
                    onClick = {
                        if (showSelectedItem) {
                            selectedItem = 1
                        }
                        onNavigateToSources()
                    }
                )
            }
            SettingItemsContainer {
                SettingItem(
                    text = Res.string.setting_master_screen_contact_us,
                    selected = false,
                    onClick = { contactSupport.invoke(data = contactSupportData) }
                )
            }
        }
        AppVersionName(
            modifier = Modifier.align(Alignment.BottomCenter),
            versionName = appConfig.versionName
        )
    }
}

@Preview
@Preview
@Composable
fun SettingMasterScreenPreview() {
    HackertabTheme {
        SettingMasterRoute(
            showSelectedItem = false,
            onNavigateToTopics = {},
            onNavigateToSources = {})
    }
}

@Composable
fun SettingItemsContainer(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(shape = MaterialTheme.shapes.large) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.dimension.large,
                    vertical = MaterialTheme.dimension.default
                )
        ) {
            content()
        }
    }
}

@Preview
@Preview
@Composable
fun SettingItemsContainerPreview() {
    HackertabTheme {
        SettingItemsContainer {
            SettingItem(Res.string.setting_master_screen_topics, false) {}
            SettingItem(Res.string.setting_master_screen_topics, true) {}
        }
    }
}

@Composable
fun SettingItem(
    text: StringResource,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .clickable(onClick = onClick)
            .background(if (selected) MaterialTheme.colors.background else Color.Unspecified)
            .padding(MaterialTheme.dimension.large),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(text),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle1
            )
            Icon(
                modifier = Modifier.size(MaterialTheme.dimension.big),
                painter = painterResource(Res.drawable.ic_baseline_arrow_forward),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        }
        Divider(
            color = MaterialTheme.colors.onBackground,
            thickness = 1.dp
        )
    }
}

@Preview()
@Composable
fun SettingItemPreview() {
    HackertabTheme {
        SettingItem(Res.string.setting_master_screen_topics, false) {}
        SettingItem(Res.string.setting_master_screen_topics, true) {}
    }
}

@Composable
fun AppVersionName(modifier: Modifier = Modifier, versionName: String) {
    Text(
        modifier = modifier,
        text = stringResource(Res.string.setting_master_screen_version_name, versionName),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.subtitle1
    )
}