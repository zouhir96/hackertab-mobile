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
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Day-group / list-section heading. Sticky-friendly.
 *
 * @param showCount when true and [count] is non-null and > 0, renders an
 *   `"$count ITEMS"` mono uppercase label on the right. Default false per
 *   v4 critique Issue 5 — counts are noise on the unfiltered Today feed.
 */
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
            .padding(top = 22.dp, bottom = 10.dp, start = 20.dp, end = 20.dp),
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
