package com.zrcoding.hackertab.settings.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
            .background(MaterialTheme.colorScheme.background)
            .padding(top = MaterialTheme.dimension.space16)
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal)
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
        Text(
            text = stringResource(description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))
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
