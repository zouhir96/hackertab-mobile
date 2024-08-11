package com.zrcoding.hackertab.settings.presentation.master

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.annotation.StringRes
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.settings.BuildConfig
import com.zrcoding.hackertab.settings.R

@Composable
fun SettingMasterScreen(
    modifier: Modifier = Modifier,
    showSelectedItem: Boolean,
    onNavigateToTopics: () -> Unit,
    onNavigateToSources: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
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
            var selectedItem by remember {
                mutableIntStateOf(
                    if (showSelectedItem) {
                        0
                    } else -1
                )
            }
            SettingItemsContainer {
                SettingItem(
                    text = R.string.setting_master_screen_topics,
                    selected = selectedItem == 0,
                    onClick = {
                        if (showSelectedItem) {
                            selectedItem = 0
                        }
                        onNavigateToTopics()
                    }
                )
                SettingItem(
                    text = R.string.setting_master_screen_sources,
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
                    text = R.string.setting_master_screen_contact_us,
                    selected = false,
                    onClick = { contactSupport(context) }
                )
            }
        }
        AppVersionName(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SettingMasterScreenPreview() {
    HackertabTheme {
        SettingMasterScreen(
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

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SettingItemsContainerPreview() {
    HackertabTheme {
        SettingItemsContainer {
            SettingItem(R.string.setting_master_screen_topics, false) {}
            SettingItem(R.string.setting_master_screen_topics, true) {}
        }
    }
}

@Composable
fun SettingItem(
    @StringRes text: Int,
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
                text = stringResource(id = text),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle1
            )
            Icon(
                modifier = Modifier.size(MaterialTheme.dimension.big),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward),
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

@Preview(showBackground = true)
@Composable
fun SettingItemPreview() {
    HackertabTheme {
        SettingItem(R.string.setting_master_screen_topics, false) {}
        SettingItem(R.string.setting_master_screen_topics, true) {}
    }
}

@Composable
fun AppVersionName(modifier: Modifier = Modifier) {
    val versionName = BuildConfig.VERSION_NAME
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.setting_master_screen_version_name, versionName),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.subtitle1
    )
}

private fun contactSupport(context: Context) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("mailto:" + context.getString(R.string.support_email))
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.support_email_subject))
        intent.putExtra(Intent.EXTRA_TEXT, menuContactMessageTemplate(context))
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        AlertDialog.Builder(context)
        .setTitle(R.string.support_no_apps_title)
        .setMessage(R.string.support_no_apps_description)
        .setNeutralButton(R.string.common_ok) { dialog, _ -> dialog.dismiss() }
        .show()
    }
}

private fun menuContactMessageTemplate(context: Context): String {
    return buildString {
        append(("\n\n\n\n"))
        append("----------------------\n")
        append("----------------------\n")
        append(context.getString(R.string.support_support_footer_message))
        append("\n")
        append(context.getString(R.string.support_device_os_version))
        append(Build.VERSION.RELEASE)
        append("\n")
        append(context.getString(R.string.support_device_model))
        append(Build.MANUFACTURER)
        append(" ").append(Build.DEVICE)
        append(" ").append(Build.MODEL)
        append("\n")
        append(context.getString(R.string.support_application_version))
        append(BuildConfig.VERSION_NAME)
    }
}