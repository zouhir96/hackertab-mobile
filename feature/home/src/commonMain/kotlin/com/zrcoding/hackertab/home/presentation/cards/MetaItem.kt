package com.zrcoding.hackertab.home.presentation.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.codeSmall
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Small icon+text meta chip used inside `ArticleCard.metaContent` slots
 * by the per-source feed cards. Renders an inline icon (12.dp) followed
 * by short label text in `codeSmall` typography.
 */
@Composable
internal fun MetaIconText(
    icon: DrawableResource,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    iconTint: Color = color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(12.dp),
        )
        Text(text = text, style = codeSmall, color = color)
    }
}

/**
 * Score-style meta with a colored circle dot followed by a label.
 * Used by Lobsters, Reddit, IndieHackers — variants of the same pattern.
 */
@Composable
internal fun MetaDotText(
    text: String,
    color: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(color),
        )
        Text(text = text, style = codeSmall, color = color)
    }
}
