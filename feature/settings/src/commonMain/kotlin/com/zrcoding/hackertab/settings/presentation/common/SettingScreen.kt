package com.zrcoding.hackertab.settings.presentation.common

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.settings.R

@Composable
fun SettingScreen(
    @StringRes title: Int,
    @StringRes description: Int,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = MaterialTheme.dimension.extraBig)
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal)
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.medium))
        Text(
            text = stringResource(id = description),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.big))
        content()
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SettingScreenPreview() {
    HackertabTheme {
        SettingScreen(
            title = R.string.setting_topics_screen_title,
            description = R.string.setting_topics_screen_description
        ) {
            Text(text = "some content here :)")
        }
    }
}