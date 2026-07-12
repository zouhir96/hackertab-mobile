package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_hackertab
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HackertabAppBar(
    title: String? = null,
    subtitle: String? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable () -> Unit = {},
    wordmark: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = MaterialTheme.dimension.space16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
        ) {
            leading?.invoke()
            when {
                wordmark -> WordmarkContent()
                title != null -> TitleContent(title = title, subtitle = subtitle)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space4),
            content = { trailing() },
        )
    }
}

@Composable
private fun WordmarkContent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_hackertab),
            contentDescription = null,
            modifier = Modifier
                .size(MaterialTheme.dimension.space16)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(MaterialTheme.dimension.space4),
                ),
        )
        Text(
            text = "hackertab",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.semantics { heading() },
        )
    }
}

@Composable
private fun TitleContent(title: String, subtitle: String?) {
    Column(verticalArrangement = Arrangement.Center) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.semantics { heading() },
        )
        if (subtitle != null) {
            Spacer(Modifier.height(1.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun AppBarWordmarkLight() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        HackertabAppBar(
            wordmark = true,
            trailing = {
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Search, contentDescription = "Search")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Refresh, contentDescription = "Refresh")
                }
            },
        )
    }
}

@Preview
@Composable
private fun AppBarTitleDark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        HackertabAppBar(title = "Bookmarks", subtitle = "12 saved · 3 unread")
    }
}
