package com.zrcoding.hackertab.design.components.states

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Hackertab v4 SnackbarHost.
 *
 * Visual reference: `design/project/components/Library.jsx` line 177-181
 * (`Toast / Snackbar`).
 *
 * Anatomy:
 * - 12dp radius, `inverseSurface` background, `inverseOnSurface` text.
 * - 14/16 padding, 10dp gap.
 * - Leading `Icons.Outlined.Info` (16dp) tinted `colorScheme.primary`.
 * - Optional trailing action: 26dp tall, 0/8dp padding, fully rounded,
 *   1dp border tinted `inverseOnSurface @ 0.2f` alpha.
 * - 4-second auto-dismiss is the M3 default.
 *
 * A11y: marked as an assertive live region so action snackbars are announced.
 */
@Composable
fun HackertabSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier.semantics { liveRegion = LiveRegionMode.Assertive },
        snackbar = { data -> HackertabSnackbar(data) },
    )
}

@Composable
private fun HackertabSnackbar(data: SnackbarData) {
    val containerColor = MaterialTheme.colorScheme.inverseSurface
    val contentColor = MaterialTheme.colorScheme.inverseOnSurface
    val actionBorder = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.2f),
    )

    Snackbar(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(12.dp),
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp),
            )
            Text(
                text = data.visuals.message,
                style = MaterialTheme.typography.bodySmall,
                color = contentColor,
                modifier = Modifier.weight(1f, fill = true),
            )
            data.visuals.actionLabel?.let { actionLabel ->
                OutlinedButton(
                    onClick = { data.performAction() },
                    modifier = Modifier.height(26.dp),
                    shape = CircleShape,
                    border = actionBorder,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = contentColor,
                    ),
                ) {
                    Text(
                        text = actionLabel,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}

// ----- Previews ---------------------------------------------------------

@Preview
@Composable
private fun HackertabSnackbarHostLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        val state = remember { SnackbarHostState() }
        LaunchedEffect(Unit) {
            state.showSnackbar(
                message = "Bookmarked. Saved to library.",
                actionLabel = "Undo",
            )
        }
        HackertabSnackbarHost(hostState = state)
    }
}

@Preview
@Composable
private fun HackertabSnackbarHostDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        val state = remember { SnackbarHostState() }
        LaunchedEffect(Unit) {
            state.showSnackbar(message = "You're now offline.")
        }
        HackertabSnackbarHost(hostState = state)
    }
}
