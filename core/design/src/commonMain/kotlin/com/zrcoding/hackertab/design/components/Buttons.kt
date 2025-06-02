package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.zrcoding.hackertab.design.theme.White600
import com.zrcoding.hackertab.design.theme.dimension

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        elevation = null,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = White600,
            disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.5f),
            disabledContentColor = White600.copy(alpha = 0.5f)
        )
    ) {
        leadingIcon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimension.small))
        }
        Text(text = text)
        trailingIcon?.let {
            Spacer(modifier = Modifier.width(MaterialTheme.dimension.small))
            Icon(
                imageVector = it,
                contentDescription = null,
            )
        }
    }
}