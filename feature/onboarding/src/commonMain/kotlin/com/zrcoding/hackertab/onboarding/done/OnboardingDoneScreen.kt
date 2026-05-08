package com.zrcoding.hackertab.onboarding.done

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.buttons.ButtonSize
import com.zrcoding.hackertab.design.components.buttons.PrimaryButton
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.onboarding_done_body
import com.zrcoding.hackertab.design.resources.onboarding_done_cta
import com.zrcoding.hackertab.design.resources.onboarding_done_headline
import com.zrcoding.hackertab.design.resources.onboarding_done_tip1_body
import com.zrcoding.hackertab.design.resources.onboarding_done_tip1_title
import com.zrcoding.hackertab.design.resources.onboarding_done_tip2_body
import com.zrcoding.hackertab.design.resources.onboarding_done_tip2_title
import com.zrcoding.hackertab.design.resources.onboarding_done_tip3_body
import com.zrcoding.hackertab.design.resources.onboarding_done_tip3_title
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ThemeMode
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

// TODO Wave 4: register in MainNavHost — NavKey: OnboardingDone
//   Navigation: replace the entire onboarding back-stack with the Home graph
//   so the user cannot navigate back to onboarding after completing it.

@Composable
fun OnboardingDoneRoute(
    navigateToFeed: () -> Unit,
    viewModel: OnboardingDoneViewModel = koinViewModel(),
) {
    OnboardingDoneScreen(
        onOpenFeedClicked = viewModel::onOpenFeedClicked,
    )
    LaunchedEffect(viewModel) {
        viewModel.navigateToFeed.collectLatest { navigateToFeed() }
    }
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.ONBOARDING_DONE)
}

@Composable
fun OnboardingDoneScreen(
    onOpenFeedClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal)
            .safeDrawingPadding(),
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Headline
        Text(
            text = stringResource(Res.string.onboarding_done_headline),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-1.0).sp,
            ),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space12))
        Text(
            text = stringResource(Res.string.onboarding_done_body),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space32))

        // 3 orientation cards
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12)) {
            OrientationCard(
                number = 1,
                titleRes = Res.string.onboarding_done_tip1_title,
                bodyRes = Res.string.onboarding_done_tip1_body,
            )
            OrientationCard(
                number = 2,
                titleRes = Res.string.onboarding_done_tip2_title,
                bodyRes = Res.string.onboarding_done_tip2_body,
            )
            OrientationCard(
                number = 3,
                titleRes = Res.string.onboarding_done_tip3_title,
                bodyRes = Res.string.onboarding_done_tip3_body,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = stringResource(Res.string.onboarding_done_cta),
            onClick = onOpenFeedClicked,
            modifier = Modifier.fillMaxWidth(),
            size = ButtonSize.Large,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))
    }
}

@Composable
private fun OrientationCard(
    number: Int,
    titleRes: StringResource,
    bodyRes: StringResource,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier.padding(MaterialTheme.dimension.space16),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
        ) {
            // Number badge
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.shapes.small,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            // Text content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(titleRes),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimension.space4))
                Text(
                    text = stringResource(bodyRes),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Previews
// ---------------------------------------------------------------------------

@Preview
@Composable
private fun OnboardingDoneScreenPreview_Light() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        OnboardingDoneScreen(onOpenFeedClicked = {})
    }
}

@Preview
@Composable
private fun OnboardingDoneScreenPreview_Dark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        OnboardingDoneScreen(onOpenFeedClicked = {})
    }
}
