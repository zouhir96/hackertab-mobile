package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.domain.models.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * 3-segment progress bar for the onboarding flow.
 *
 * @param currentStep zero-indexed step the user is currently on (0..[totalSteps]-1).
 */
@Composable
fun OnboardingStepIndicator(
    currentStep: Int,
    totalSteps: Int = 3,
    modifier: Modifier = Modifier,
) {
    val pastColor = MaterialTheme.colorScheme.onSurfaceVariant
    val currentColor = MaterialTheme.colorScheme.primary
    val futureColor = MaterialTheme.colorScheme.outline

    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                progressBarRangeInfo = ProgressBarRangeInfo(
                    current = (currentStep + 1).toFloat(),
                    range = 1f..totalSteps.toFloat(),
                    steps = totalSteps,
                )
            },
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(totalSteps) { index ->
            val color = when {
                index < currentStep -> pastColor
                index == currentStep -> currentColor
                else -> futureColor
            }
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(color),
            )
        }
    }
}

@Preview
@Composable
private fun StepIndicatorStep1() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        OnboardingStepIndicator(currentStep = 0)
    }
}

@Preview
@Composable
private fun StepIndicatorStep2() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        OnboardingStepIndicator(currentStep = 1)
    }
}

@Preview
@Composable
private fun StepIndicatorStep3Dark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        OnboardingStepIndicator(currentStep = 2)
    }
}
