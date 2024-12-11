package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import com.zrcoding.hackertab.design.theme.dimension
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun RoundedIconButton(
    modifier: Modifier = Modifier,
    size: Dp,
    icon: DrawableResource,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .padding(start = MaterialTheme.dimension.default)
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colors.background),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground
        )
    }
}