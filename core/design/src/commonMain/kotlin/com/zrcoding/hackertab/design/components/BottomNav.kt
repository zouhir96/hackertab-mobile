package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.ThemeMode
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Item descriptor for [HackertabBottomNav] / [HackertabNavRail].
 *
 * **Icon convention for the "Saved" tab**: do NOT use [Icons.Outlined.BookmarkBorder]
 * — that glyph is used by per-card actions and would visually collide with the tab
 * (Issue 2 from the v4 critique). Pass [Icons.AutoMirrored.Outlined.LibraryBooks]
 * or `Icons.Outlined.CollectionsBookmark` instead.
 */
data class BottomNavItem(
    val id: String,
    val label: String,
    val icon: ImageVector,
)

/**
 * Hackertab v4 bottom navigation. 3 tabs (Today / Saved / Settings).
 *
 * **Compose Multiplatform 1.9.3 limitation**: real backdrop blur (`Modifier.blur`)
 * is unreliable on iOS Skia and Android < 12. v4.0 ships a flat 85%-alpha surface
 * tint instead. Revisit in v4.1 once CMP exposes a stable cross-platform blur.
 */
@Composable
fun HackertabBottomNav(
    items: ImmutableList<BottomNavItem>,
    activeId: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
            modifier = Modifier
                .height(78.dp)
                .padding(bottom = 22.dp),
        ) {
            items.forEach { item ->
                val selected = item.id == activeId
                NavigationBarItem(
                    selected = selected,
                    onClick = { onSelect(item.id) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp),
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.surface,
                    ),
                )
            }
        }
    }
}

@Suppress("unused") // referenced by previews and by callers via this object
private object BottomNavSampleItems {
    val Today = BottomNavItem("today", "Today", Icons.Outlined.Home)
    val Saved = BottomNavItem("saved", "Saved", Icons.AutoMirrored.Outlined.LibraryBooks)
    val Settings = BottomNavItem("settings", "Settings", Icons.Outlined.Settings)
}

@Preview
@Composable
private fun BottomNavLight() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        HackertabBottomNav(
            items = persistentListOf(
                BottomNavSampleItems.Today,
                BottomNavSampleItems.Saved,
                BottomNavSampleItems.Settings,
            ),
            activeId = "today",
            onSelect = {},
        )
    }
}

@Preview
@Composable
private fun BottomNavDark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        HackertabBottomNav(
            items = persistentListOf(
                BottomNavSampleItems.Today,
                BottomNavSampleItems.Saved,
                BottomNavSampleItems.Settings,
            ),
            activeId = "saved",
            onSelect = {},
        )
    }
}
