package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.Neutral0
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
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Neutral0,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            disabledContentColor = Neutral0.copy(alpha = 0.5f)
        )
    ) {
        leadingIcon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimension.space4))
        }
        Text(text = text)
        trailingIcon?.let {
            Spacer(modifier = Modifier.width(MaterialTheme.dimension.space4))
            Icon(
                imageVector = it,
                contentDescription = null,
            )
        }
    }
}
