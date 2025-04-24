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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
    viewModel: SetupSourcesViewModel = koinViewModel(),
    navigateToNextScreen: () -> Unit,
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
            text = "Sources",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.small))
        Text(
            text = "Your feed will be tailored by your followed sources",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.big))
        Box(
            modifier = Modifier.border(
                width = 1.dp,
                color = MaterialTheme.colors.onBackground.copy(0.3f),
                shape = MaterialTheme.shapes.medium
            ).padding(MaterialTheme.dimension.medium)
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
                tint = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimension.small))
            Text(
                text = "You still can change this in settings",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.large))
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