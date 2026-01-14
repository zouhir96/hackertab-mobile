package com.zrcoding.hackertab.settings.presentation.common

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
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.setting_topics_screen_description
import com.zrcoding.hackertab.design.resources.setting_topics_screen_title
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SettingScreen(
    title: StringResource,
    description: StringResource,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = MaterialTheme.dimension.default)
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal)
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.medium))
        Text(
            text = stringResource(description),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.big))
        content()
    }
}

@Preview()
@Composable
fun SettingScreenPreview() {
    HackertabTheme {
        SettingScreen(
            title = Res.string.setting_topics_screen_title,
            description = Res.string.setting_topics_screen_description
        ) {
            Text(text = "some content here :)")
        }
    }
}