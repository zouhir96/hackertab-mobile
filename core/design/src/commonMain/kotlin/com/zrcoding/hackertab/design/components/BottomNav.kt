package com.zrcoding.hackertab.design.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zrcoding.hackertab.design.theme.DarkBgElevated
import com.zrcoding.hackertab.design.theme.HackertabMotion
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.LightBgElevated
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
 * Hackertab v4 bottom navigation — iOS 26 "Liquid Glass" floating pill.
 *
 * A centered capsule that hovers above content (bottom 18dp), with a refractive
 * rim highlight, a top specular streak, and a brand-tinted glass capsule marking
 * the active tab. Mirrors `.tab-bar` / `.tab-item` in `design/project/styles/app.css`.
 *
 * **Compose Multiplatform 1.9.3 limitation**: real backdrop blur (`Modifier.blur`)
 * is unreliable on iOS Skia and Android < 12. The glass surface is approximated
 * with a translucent `bg-elevated` tint + rim/shadow stack instead of a live
 * backdrop blur. Revisit once CMP exposes a stable cross-platform blur.
 */
@Composable
fun HackertabBottomNav(
    items: ImmutableList<BottomNavItem>,
    activeId: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    val isDark = colorScheme.background.luminance() < 0.5f
    val onBg = colorScheme.onBackground
    val pillShape = RoundedCornerShape(percent = 50)

    // Glass surface: translucent bg-elevated (CSS used 38%; bumped for legibility
    // since there is no live backdrop blur to separate it from scrolling content).
    val glassFill = (if (isDark) DarkBgElevated else LightBgElevated).copy(alpha = 0.82f)
    // Rim highlight — brighter at the top, fading down (CSS inset top rim).
    val rimBrush = Brush.verticalGradient(
        colors = listOf(onBg.copy(alpha = 0.14f), onBg.copy(alpha = 0.04f)),
    )
    // Specular streak across the top half.
    val specularBrush = Brush.verticalGradient(
        colors = listOf(
            (if (isDark) Color.White else onBg).copy(alpha = if (isDark) 0.10f else 0.08f),
            Color.Transparent,
        ),
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .height(62.dp)
                .clip(pillShape)
                .background(glassFill)
                .border(width = 1.dp, brush = rimBrush, shape = pillShape),
        ) {
            // Top specular highlight, clipped to the pill.
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(specularBrush),
            )
            Row(
                modifier = Modifier.padding(6.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items.forEach { item ->
                    TabItem(
                        item = item,
                        selected = item.id == activeId,
                        onSelect = { onSelect(item.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun TabItem(
    item: BottomNavItem,
    selected: Boolean,
    onSelect: () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val itemShape = RoundedCornerShape(percent = 50)
    val contentColor by animateColorAsState(
        targetValue = if (selected) colorScheme.onPrimary else colorScheme.onSurfaceVariant,
        animationSpec = tween(HackertabMotion.fast, easing = HackertabMotion.standardEasing),
        label = "tabItemColor",
    )

    // Active = brand-tinted glass capsule with a refraction rim + brand glow.
    val activeRim = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.32f),
            colorScheme.primary.copy(alpha = 0.30f),
        ),
    )
    val capsuleModifier = if (selected) {
        Modifier
            .shadow(
                elevation = 6.dp,
                shape = itemShape,
                clip = false,
                spotColor = colorScheme.primary,
                ambientColor = colorScheme.primary,
            )
            .clip(itemShape)
            .background(colorScheme.primary.copy(alpha = 0.88f))
            .border(width = 1.dp, brush = activeRim, shape = itemShape)
    } else {
        Modifier.clip(itemShape)
    }

    Column(
        modifier = Modifier
            .width(76.dp)
            .height(50.dp)
            .then(capsuleModifier)
            .selectable(
                selected = selected,
                role = Role.Tab,
                onClick = onSelect,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = contentColor,
            modifier = Modifier.size(22.dp),
        )
        Text(
            text = item.label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = contentColor,
            maxLines = 1,
        )
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
