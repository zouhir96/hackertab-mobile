package com.zrcoding.hackertab.design.components.states

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Hackertab v4 OfflineChip — a pinned row used at the top of feeds when the
 * device is offline.
 *
 * Visual reference: `design/project/components/Screens.jsx` lines 759-765
 * (`ErrorState noNet` row).
 *
 * Anatomy:
 * - 14dp horizontal margin / 8dp bottom margin / 12dp radius.
 * - `surfaceVariant` background, 10/14 padding, 10dp gap.
 * - WifiOff icon (16dp) tinted with `colorScheme.error`.
 * - "No internet connection" text in `bodySmall`, weight 1f.
 * - Retry pill: 28dp tall, 0/10dp padding, 1dp `outline` border, fully rounded.
 */
@Composable
fun OfflineChip(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "No internet connection",
    retryLabel: String = "Retry",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
            .padding(bottom = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .semantics {
                liveRegion = LiveRegionMode.Polite
                contentDescription = label
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            imageVector = Icons.Outlined.WifiOff,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        OutlinedButton(
            onClick = onRetry,
            modifier = Modifier.height(28.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
            ),
        ) {
            Text(
                text = retryLabel,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview
@Composable
private fun OfflineChipLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        OfflineChip(onRetry = {})
    }
}

@Preview
@Composable
private fun OfflineChipDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        OfflineChip(onRetry = {})
    }
}
