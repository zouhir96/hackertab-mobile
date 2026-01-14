package com.zrcoding.hackertab.onboarding.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.PrimaryButton
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Profile
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SetupProfileRoute(
    navigateToNextScreen: (Profile) -> Unit,
    viewModel: SetupProfileViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal)
            .safeDrawingPadding()
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.extraBig))
        Text(
            text = "Hi, \uD83D\uDC4B Welcome to Hackertab",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.small))
        Text(
            text = "Let's customize your Hackertab experience!",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.big))
        Text(
            text = "Let’s get to know you, please choose your profile",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.medium))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium),
        ) {
            items(state.profiles) {
                Card(
                    onClick = {
                        viewModel.onProfileSelected(it)
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    border = if (it == state.selectedProfile) {
                        BorderStroke(MaterialTheme.dimension.tiny, MaterialTheme.colors.primary)
                    } else BorderStroke(1.dp, MaterialTheme.colors.primary.copy(alpha = 0.1f)),
                    elevation = if (it == state.selectedProfile) 1.dp else 0.dp
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.dimension.default)
                            .fillMaxWidth(),
                        text = it.label,
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Next",
            enabled = state.canContinue(),
            trailingIcon = Icons.AutoMirrored.Default.ArrowRight,
            onClick = viewModel::onContinueClicked
        )
    }
    LaunchedEffect(viewModel) {
        viewModel.goToNextPage.collectLatest {
            navigateToNextScreen(it)
        }
    }
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETUP_PROFILE)
}