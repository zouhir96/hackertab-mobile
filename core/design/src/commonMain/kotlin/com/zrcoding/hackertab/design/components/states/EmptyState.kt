package com.zrcoding.hackertab.design.components.states

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    body: String? = null,
    primaryCta: EmptyStateCta? = null,
    secondaryCta: EmptyStateCta? = null,
    modifier: Modifier = Modifier,
) {
    val a11yLabel = if (!body.isNullOrEmpty()) "$title. $body" else title

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimension.space40)
            .semantics {
                liveRegion = LiveRegionMode.Polite
                contentDescription = a11yLabel
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(MaterialTheme.dimension.space24),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(MaterialTheme.dimension.space32),
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )

        if (!body.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimension.space6))
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 260.dp),
            )
        }

        if (primaryCta != null || secondaryCta != null) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))
            Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8)) {
                secondaryCta?.let { cta ->
                    OutlinedButton(
                        onClick = cta.onClick,
                        modifier = Modifier.height(MaterialTheme.dimension.space40),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        contentPadding = PaddingValues(horizontal = MaterialTheme.dimension.space16),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onBackground,
                        ),
                    ) {
                        Text(
                            text = cta.label,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
                primaryCta?.let { cta ->
                    Button(
                        onClick = cta.onClick,
                        modifier = Modifier.height(MaterialTheme.dimension.space40),
                        shape = CircleShape,
                        contentPadding = PaddingValues(horizontal = MaterialTheme.dimension.space16),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                    ) {
                        Text(
                            text = cta.label,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}

data class EmptyStateCta(val label: String, val onClick: () -> Unit)

@Preview
@Composable
private fun EmptyStateLightPreview() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        EmptyState(
            icon = Icons.Outlined.BookmarkBorder,
            title = "Nothing saved yet",
            body = "Tap the bookmark on any card to keep it here.",
            primaryCta = EmptyStateCta(label = "Browse feed") {},
        )
    }
}

@Preview
@Composable
private fun EmptyStateDarkPreview() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        EmptyState(
            icon = Icons.Outlined.BookmarkBorder,
            title = "Nothing here today",
            body = "Lobsters has no Swift posts right now. Try a different topic or source.",
            primaryCta = EmptyStateCta(label = "See all sources") {},
            secondaryCta = EmptyStateCta(label = "Clear filter") {},
        )
    }
}
