package com.zrcoding.hackertab.design.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.zrcoding.hackertab.design.theme.dimension

@Composable
internal fun ButtonContentRow(
    text: String,
    leadingIcon: ImageVector?,
    trailingIcon: ImageVector?,
    iconSize: androidx.compose.ui.unit.Dp,
    isLoading: Boolean,
    contentColor: Color,
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(MaterialTheme.dimension.space16),
            strokeWidth = MaterialTheme.dimension.space2,
            color = contentColor,
        )
        return
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space4),
    ) {
        leadingIcon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(iconSize),
            )
        }
        Text(text = text)
        trailingIcon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(iconSize),
            )
        }
    }
}
