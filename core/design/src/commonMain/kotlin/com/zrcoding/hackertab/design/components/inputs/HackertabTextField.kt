package com.zrcoding.hackertab.design.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Hackertab v4 single-line text field. Mirrors `Library.jsx` slug 03.05
 * (InputField).
 *
 * Anatomy:
 *  - 40dp tall row, 14dp horizontal / 0 vertical padding, 10dp radius.
 *  - `colorScheme.surfaceVariant` background. Optional 1dp `error` border.
 *  - Optional [leadingIcon] (16dp) tinted `colorScheme.onSurfaceVariant`.
 *  - Optional clear icon (14dp X) shown when the field is non-empty and
 *    [onClear] is non-null.
 *  - When [isError], the row gets a 1dp `colorScheme.error` border and the
 *    optional [errorMessage] is rendered below in `bodySmall` / `error` color.
 *
 * A11y: errors are exposed via `Modifier.semantics { error(errorMessage) }`
 * which TalkBack and VoiceOver announce alongside the value.
 */
@Composable
fun HackertabTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    onClear: (() -> Unit)? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
) {
    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant
        .copy(alpha = if (enabled) 1f else 0.4f)
    val contentColor = MaterialTheme.colorScheme.onSurface
        .copy(alpha = if (enabled) 1f else 0.4f)
    val mutedColor = MaterialTheme.colorScheme.onSurfaceVariant
        .copy(alpha = if (enabled) 1f else 0.4f)
    val errorColor = MaterialTheme.colorScheme.error

    Column(modifier = modifier) {
        val rowModifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .let { base ->
                if (isError) base.border(1.dp, errorColor, RoundedCornerShape(10.dp)) else base
            }
            .padding(horizontal = 14.dp)
            .semantics {
                if (isError && errorMessage != null) error(errorMessage)
            }

        Row(
            modifier = rowModifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = mutedColor,
                    modifier = Modifier.size(16.dp),
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium,
                        color = mutedColor,
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    enabled = enabled,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = contentColor),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = imeAction,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            if (value.isNotEmpty() && onClear != null && enabled) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear text",
                    tint = mutedColor,
                    modifier = Modifier
                        .size(14.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .clickable(role = Role.Button, onClick = onClear)
                        .semantics { role = Role.Button },
                )
            }
        }
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = errorColor,
                modifier = Modifier.padding(start = 14.dp, top = 4.dp),
            )
        }
    }
}

@Preview
@Composable
private fun HackertabTextFieldPreview_Empty() {
    HackertabTheme {
        HackertabTextField(
            value = "",
            onValueChange = {},
            placeholder = "Search bookmarks…",
            leadingIcon = Icons.Default.Search,
        )
    }
}

@Preview
@Composable
private fun HackertabTextFieldPreview_Filled() {
    HackertabTheme {
        HackertabTextField(
            value = "kotlin coroutines",
            onValueChange = {},
            leadingIcon = Icons.Default.Search,
            onClear = {},
        )
    }
}

@Preview
@Composable
private fun HackertabTextFieldPreview_Error() {
    HackertabTheme {
        HackertabTextField(
            value = "abc",
            onValueChange = {},
            isError = true,
            errorMessage = "Must be at least 6 characters",
        )
    }
}

@Preview
@Composable
private fun HackertabTextFieldPreview_Disabled() {
    HackertabTheme {
        HackertabTextField(
            value = "Disabled",
            onValueChange = {},
            enabled = false,
        )
    }
}
