package com.zrcoding.hackertab.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.WebViewRoute
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

// TODO Wave 4: register WebViewScreen in MainNavHost with url + title arguments.

/**
 * WebView modal screen.
 *
 * Anatomy (per design spec §05/06):
 * - Top bar: back arrow | page title (truncated) + URL host | — (no action icons per brief)
 * - WebView content fills remaining space
 * - Bottom action bar: Save | Share | Open-in-browser
 */
@Composable
fun WebViewScreen(
    url: String,
    title: String = "",
    isBookmarked: Boolean = false,
    onBack: () -> Unit,
    onSave: () -> Unit = {},
    onShare: () -> Unit = {},
    onOpenInBrowser: () -> Unit = {},
) {
    val urlHost = remember(url) {
        runCatching { java.net.URI(url).host.orEmpty().removePrefix("www.") }.getOrDefault("")
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .statusBarsPadding(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    Spacer(Modifier.width(4.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        if (title.isNotBlank()) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        if (urlHost.isNotBlank()) {
                            Text(
                                text = urlHost,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .navigationBarsPadding(),
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    WebViewAction(
                        icon = {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                contentDescription = if (isBookmarked) "Remove bookmark" else "Save article",
                                tint = if (isBookmarked) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        label = if (isBookmarked) "Saved" else "Save",
                        onClick = onSave,
                        modifier = Modifier.weight(1f),
                    )
                    WebViewAction(
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        label = "Share",
                        onClick = onShare,
                        modifier = Modifier.weight(1f),
                    )
                    WebViewAction(
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.OpenInBrowser,
                                contentDescription = "Open in browser",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        label = "Browser",
                        onClick = onOpenInBrowser,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            WebViewRoute(url = url)
        }
    }

    TrackScreenViewEvent(screenName = "web_view")
}

@Composable
private fun WebViewAction(
    icon: @Composable () -> Unit,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(36.dp)) {
            icon()
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// -------------------------------------------------------------------------
// Previews
// -------------------------------------------------------------------------

@Preview
@Composable
private fun WebViewScreenLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        var bookmarked by remember { mutableStateOf(false) }
        WebViewScreen(
            url = "https://hackernews.com/item?id=12345",
            title = "React is the best web framework ever",
            isBookmarked = bookmarked,
            onBack = {},
            onSave = { bookmarked = !bookmarked },
            onShare = {},
            onOpenInBrowser = {},
        )
    }
}

@Preview
@Composable
private fun WebViewScreenDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        WebViewScreen(
            url = "https://github.com/square/okhttp",
            title = "square/okhttp",
            isBookmarked = true,
            onBack = {},
            onSave = {},
            onShare = {},
            onOpenInBrowser = {},
        )
    }
}
