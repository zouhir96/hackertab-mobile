package com.zrcoding.hackertab.onboarding.sources

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.Icon
import com.zrcoding.hackertab.design.components.OnboardingStepIndicator
import com.zrcoding.hackertab.design.components.buttons.PrimaryButton
import com.zrcoding.hackertab.design.components.toChipData
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.onboarding_sources_cta
import com.zrcoding.hackertab.design.resources.onboarding_sources_selected_count
import com.zrcoding.hackertab.design.resources.onboarding_sources_subtitle
import com.zrcoding.hackertab.design.resources.onboarding_sources_title
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.ThemeMode
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.material3.Icon as M3Icon

@Composable
fun SetupSourcesRoute(
    navigateToNextScreen: () -> Unit,
    viewModel: SetupSourcesViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value
    SetupSourcesScreen(
        state = state,
        onSourceClicked = viewModel::onChipClicked,
        onContinue = viewModel::onContinueClicked,
    )
    LaunchedEffect(viewModel) {
        viewModel.goToNextPage.collectLatest { navigateToNextScreen() }
    }
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETUP_SOURCES)
}

@Composable
fun SetupSourcesScreen(
    state: SetupSourcesViewState,
    onSourceClicked: (ChipData) -> Unit,
    onContinue: () -> Unit,
) {
    val selectedCount = state.sources.count { it.selected }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
        OnboardingStepIndicator(
            currentStep = 2,
            modifier = Modifier.padding(bottom = MaterialTheme.dimension.space32),
        )
        Text(
            text = stringResource(Res.string.onboarding_sources_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-1.0).sp,
            ),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
        Text(
            text = if (selectedCount > 0) {
                stringResource(Res.string.onboarding_sources_selected_count, selectedCount)
            } else {
                stringResource(Res.string.onboarding_sources_subtitle)
            },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space24))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
            modifier = Modifier.weight(1f),
        ) {
            items(state.sources, key = { it.id }) { chip ->
                SourceTile(
                    chip = chip,
                    onClick = { onSourceClicked(chip) },
                )
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))
        PrimaryButton(
            text = stringResource(Res.string.onboarding_sources_cta),
            onClick = onContinue,
            enabled = state.canContinue(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))
    }
}

@Composable
private fun SourceTile(
    chip: ChipData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val source = Source.entries.find { it.id == chip.id }
    val isSelected = chip.selected

    Box(
        modifier = modifier
            .aspectRatio(1.4f)
            .clip(MaterialTheme.shapes.large)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface,
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outlineVariant,
                shape = MaterialTheme.shapes.large,
            )
            .clickable(onClick = onClick)
            .padding(MaterialTheme.dimension.space12)
            .semantics {
                role = Role.Checkbox
                selected = isSelected
            },
    ) {
        // Check badge — top-end corner
        if (isSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                M3Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(12.dp),
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Circular brand-icon block
            if (source != null) {
                source.Icon(size = 40.dp)
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
            Text(
                text = chip.name,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Previews
// ---------------------------------------------------------------------------

@Preview
@Composable
private fun SetupSourcesScreenPreview_Light() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        SetupSourcesScreen(
            state = SetupSourcesViewState(
                sources = Source.entries.mapIndexed { i, s ->
                    s.toChipData(selected = i < 3)
                }.toPersistentList(),
            ),
            onSourceClicked = {},
            onContinue = {},
        )
    }
}

@Preview
@Composable
private fun SetupSourcesScreenPreview_Dark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        SetupSourcesScreen(
            state = SetupSourcesViewState(),
            onSourceClicked = {},
            onContinue = {},
        )
    }
}
