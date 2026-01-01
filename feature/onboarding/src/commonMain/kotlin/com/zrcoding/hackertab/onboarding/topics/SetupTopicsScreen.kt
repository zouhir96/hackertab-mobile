package com.zrcoding.hackertab.onboarding.topics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
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
fun SetupTopicsRoute(
    navigateToNextScreen: () -> Unit,
    viewModel: SetupTopicsViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value
    var expandedCategory by remember { mutableStateOf<String?>(viewModel.route.profile.category) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal)
            .safeDrawingPadding()
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.extraBig))
        Text(
            text = "Languages & topics",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.small))
        Text(
            text = "Select the languages & topics you're interested in following.",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.big))
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground.copy(0.3f),
                    shape = MaterialTheme.shapes.medium
                )
                .weight(1f)
                .padding(MaterialTheme.dimension.medium)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium)
            ) {
                state.topics.forEach { (category, chips) ->
                    val expanded by derivedStateOf { expandedCategory == category }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.small)
                    ) {
                        Row(
                            modifier = Modifier.clickable {
                                expandedCategory = if (expandedCategory == category) {
                                    null
                                } else {
                                    category
                                }
                            },
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = category.capitalize(Locale("en")),
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.onBackground
                            )
                            Icon(
                                imageVector = if (expanded) {
                                    Icons.Default.ArrowDropUp
                                } else Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                        AnimatedVisibility(visible = expanded) {
                            ChipGroup(
                                chips = chips,
                                onChipClicked = viewModel::onChipClicked
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.big))
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
            text = "Validate",
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
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETUP_TOPICS)
}