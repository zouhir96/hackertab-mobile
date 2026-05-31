package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SectionHeader(
    label: String,
    count: Int? = null,
    showCount: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = MaterialTheme.dimension.space20,
                bottom = MaterialTheme.dimension.space8,
                start = MaterialTheme.dimension.space20,
                end = MaterialTheme.dimension.space20,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.semantics { heading() },
        )
        if (showCount && count != null && count > 0) {
            Text(
                text = "$count ITEMS",
                style = codeSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
private fun SectionHeaderTodayLight() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        SectionHeader(label = "Today")
    }
}

@Preview
@Composable
private fun SectionHeaderWithCountDark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        SectionHeader(label = "This week", count = 12, showCount = true)
    }
}
