package com.zrcoding.hackertab.onboarding.sources

import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.ChipGroup
import com.zrcoding.hackertab.design.components.PrimaryButton
import com.zrcoding.hackertab.design.theme.dimension
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SetupSourcesRoute(
    navigateToNextScreen: () -> Unit,
    viewModel: SetupSourcesViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal)
            .safeDrawingPadding()
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space40))
        Text(
            text = "Sources",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space4))
        Text(
            text = "Your feed will be tailored by your followed sources",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))
        Box(
            modifier = Modifier.border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.3f),
                shape = MaterialTheme.shapes.medium
            ).padding(MaterialTheme.dimension.space8)
        ) {
            ChipGroup(
                chips = state.sources,
                onChipClicked = viewModel::onChipClicked
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimension.space4))
            Text(
                text = "You still can change this in settings",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space12))
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Finish",
            enabled = state.canContinue(),
            trailingIcon = Icons.AutoMirrored.Default.ArrowRight,
            onClick = viewModel::onContinueClicked
        )
    }
    LaunchedEffect(viewModel) {
        viewModel.goToNextPage.collectLatest {
            navigateToNextScreen()
        }
    }
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETUP_SOURCES)
}
