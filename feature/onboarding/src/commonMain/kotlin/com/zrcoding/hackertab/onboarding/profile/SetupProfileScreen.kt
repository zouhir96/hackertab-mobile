package com.zrcoding.hackertab.onboarding.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.OnboardingStepIndicator
import com.zrcoding.hackertab.design.components.buttons.PrimaryButton
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.onboarding_profile_cta
import com.zrcoding.hackertab.design.resources.onboarding_profile_subtitle
import com.zrcoding.hackertab.design.resources.onboarding_profile_title
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.models.ThemeMode
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

private data class ProfileUiMeta(val emoji: String, val sub: String)

private val profileMeta: Map<Profile, ProfileUiMeta> = mapOf(
    Profile.MOBILE_ENGINEER to ProfileUiMeta("📱", ".kt / .swift"),
    Profile.FRONTEND_ENGINEER to ProfileUiMeta("🖥️", "react / vue"),
    Profile.BACKEND_ENGINEER to ProfileUiMeta("⚙️", "api / infra"),
    Profile.FULL_STACK_ENGINEER to ProfileUiMeta("🔧", "end-to-end"),
    Profile.DEVOPS_ENGINEER to ProfileUiMeta("🚀", "ci / k8s"),
    Profile.DATA_ENGINEER to ProfileUiMeta("📊", "sql / spark"),
    Profile.SECURITY_ENGINEER to ProfileUiMeta("🔒", "sec / pen test"),
    Profile.ML_ENGINEER to ProfileUiMeta("🤖", "ml / llm"),
    Profile.OTHER to ProfileUiMeta("✨", "my own path"),
)

@Composable
fun SetupProfileRoute(
    navigateToNextScreen: (Profile) -> Unit,
    viewModel: SetupProfileViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value
    SetupProfileScreen(
        state = state,
        onProfileSelected = viewModel::onProfileSelected,
        onContinue = viewModel::onContinueClicked,
    )
    LaunchedEffect(viewModel) {
        viewModel.goToNextPage.collectLatest { navigateToNextScreen(it) }
    }
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETUP_PROFILE)
}

@Composable
fun SetupProfileScreen(
    state: SetupProfileViewState,
    onProfileSelected: (Profile) -> Unit,
    onContinue: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
        OnboardingStepIndicator(
            currentStep = 0,
            modifier = Modifier.padding(bottom = MaterialTheme.dimension.space32),
        )
        Text(
            text = stringResource(Res.string.onboarding_profile_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-1.0).sp,
            ),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
        Text(
            text = stringResource(Res.string.onboarding_profile_subtitle),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space24))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
            modifier = Modifier.weight(1f),
        ) {
            items(state.profiles) { profile ->
                val isSelected = profile == state.selectedProfile
                val meta = profileMeta[profile]
                Card(
                    onClick = { onProfileSelected(profile) },
                    modifier = Modifier.aspectRatio(1f / 0.55f),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                    ),
                    border = if (isSelected) {
                        null
                    } else {
                        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    },
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(MaterialTheme.dimension.space12),
                    ) {
                        if (meta != null) {
                            Text(
                                text = meta.emoji,
                                fontSize = 22.sp,
                                modifier = Modifier.align(Alignment.TopStart),
                            )
                        }
                        Column(modifier = Modifier.align(Alignment.BottomStart)) {
                            Text(
                                text = profile.label.replace("\n", " "),
                                color = if (isSelected) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onBackground
                                },
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                ),
                                textAlign = TextAlign.Start,
                            )
                            if (meta != null) {
                                Text(
                                    text = meta.sub,
                                    color = if (isSelected) {
                                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))
        PrimaryButton(
            text = stringResource(Res.string.onboarding_profile_cta),
            onClick = onContinue,
            enabled = state.canContinue(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))
    }
}

@Preview
@Composable
private fun SetupProfileScreenPreview_Light() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        SetupProfileScreen(
            state = SetupProfileViewState(
                profiles = Profile.entries.toPersistentList(),
                selectedProfile = Profile.MOBILE_ENGINEER,
            ),
            onProfileSelected = {},
            onContinue = {},
        )
    }
}

@Preview
@Composable
private fun SetupProfileScreenPreview_Dark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        SetupProfileScreen(
            state = SetupProfileViewState(
                profiles = Profile.entries.toPersistentList(),
                selectedProfile = null,
            ),
            onProfileSelected = {},
            onContinue = {},
        )
    }
}
