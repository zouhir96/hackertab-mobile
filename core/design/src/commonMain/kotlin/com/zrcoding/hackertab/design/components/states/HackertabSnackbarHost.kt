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
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

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
        modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space16, vertical = MaterialTheme.dimension.space12),
        shape = RoundedCornerShape(MaterialTheme.dimension.space12),
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(MaterialTheme.dimension.space16),
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
                    modifier = Modifier.height(MaterialTheme.dimension.space24),
                    shape = CircleShape,
                    border = actionBorder,
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimension.space8, vertical = 0.dp),
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
