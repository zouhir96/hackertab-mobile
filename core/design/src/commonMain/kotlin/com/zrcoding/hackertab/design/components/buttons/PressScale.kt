package com.zrcoding.hackertab.design.components.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.zrcoding.hackertab.design.theme.motion

@Composable
internal fun rememberPressScale(interactionSource: InteractionSource): State<Float> {
    val isPressed by interactionSource.collectIsPressedAsState()
    val target = if (isPressed) 0.97f else 1f
    return animateFloatAsState(
        targetValue = target,
        animationSpec = MaterialTheme.motion.springStandard,
        label = "hackertab.button.pressScale",
    )
}

@Composable
internal fun Modifier.pressScale(interactionSource: InteractionSource): Modifier {
    val scale by rememberPressScale(interactionSource)
    return this.scale(scale)
}
