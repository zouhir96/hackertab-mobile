package com.zrcoding.hackertab.onboarding.topics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zrcoding.hackertab.analytics.TrackScreenViewEvent
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.design.components.ChipData
import com.zrcoding.hackertab.design.components.HackertabFilterChip
import com.zrcoding.hackertab.design.components.OnboardingStepIndicator
import com.zrcoding.hackertab.design.components.buttons.PrimaryButton
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.onboarding_topics_cta
import com.zrcoding.hackertab.design.resources.onboarding_topics_reassurance
import com.zrcoding.hackertab.design.resources.onboarding_topics_selected_count
import com.zrcoding.hackertab.design.resources.onboarding_topics_subtitle
import com.zrcoding.hackertab.design.resources.onboarding_topics_title
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Profile
import com.zrcoding.hackertab.domain.models.ThemeMode
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

// TODO Wave 4: register in MainNavHost — NavKey: OnboardingTopics(profile: Profile)

@Composable
fun SetupTopicsRoute(
    profile: Profile,
    navigateToNextScreen: () -> Unit,
    viewModel: SetupTopicsViewModel = koinViewModel(parameters = { parametersOf(profile) }),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value
    SetupTopicsScreen(
        state = state,
        initialExpandedCategory = profile.category,
        onChipClicked = viewModel::onChipClicked,
        onContinue = viewModel::onContinueClicked,
    )
    LaunchedEffect(viewModel) {
        viewModel.goToNextPage.collectLatest { navigateToNextScreen() }
    }
    TrackScreenViewEvent(screenName = AnalyticsEvent.ScreensNames.SETUP_TOPICS)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetupTopicsScreen(
    state: SetupTopicsViewState,
    initialExpandedCategory: String? = null,
    onChipClicked: (ChipData) -> Unit,
    onContinue: () -> Unit,
) {
    // Which category is currently expanded. Saveable across recompositions.
    var expandedCategory by rememberSaveable { mutableStateOf(initialExpandedCategory) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal)
            .safeDrawingPadding(),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
        OnboardingStepIndicator(
            currentStep = 1,
            modifier = Modifier.padding(bottom = MaterialTheme.dimension.space32),
        )
        Text(
            text = stringResource(Res.string.onboarding_topics_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-1.0).sp,
            ),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space8))
        Text(
            text = stringResource(Res.string.onboarding_topics_subtitle),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space24))

        // Accordion list — each category is a sticky section header + chip flow
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            state.topics.forEach { (category, chips) ->
                val isExpanded = expandedCategory == category
                val selectedCount = chips.count { it.selected }

                stickyHeader(key = "header_$category") {
                    TopicCategoryHeader(
                        category = category,
                        selectedCount = selectedCount,
                        isExpanded = isExpanded,
                        onClick = {
                            expandedCategory = if (isExpanded) null else category
                        },
                    )
                }

                item(key = "chips_$category") {
                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = expandVertically(),
                        exit = shrinkVertically(),
                    ) {
                        TopicChipGrid(
                            chips = chips,
                            onChipClicked = onChipClicked,
                        )
                    }
                }

                item(key = "divider_$category") {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space12))
        Text(
            text = stringResource(Res.string.onboarding_topics_reassurance),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space12))
        PrimaryButton(
            text = stringResource(Res.string.onboarding_topics_cta),
            onClick = onContinue,
            enabled = state.canContinue(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimension.space20))
    }
}

@Composable
private fun TopicCategoryHeader(
    category: String,
    selectedCount: Int,
    isExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .clickable(onClick = onClick)
            .padding(vertical = MaterialTheme.dimension.space12)
            .semantics {
                if (isExpanded) collapse { onClick(); true }
                else expand { onClick(); true }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
        ) {
            Text(
                text = category.capitalize(Locale("en")),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
            )
            if (selectedCount > 0) {
                Text(
                    text = stringResource(Res.string.onboarding_topics_selected_count, selectedCount),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp
            else Icons.Default.KeyboardArrowDown,
            contentDescription = if (isExpanded) "Collapse $category" else "Expand $category",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TopicChipGrid(
    chips: PersistentList<ChipData>,
    onChipClicked: (ChipData) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.dimension.space12),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
    ) {
        chips.forEach { chip ->
            HackertabFilterChip(
                selected = chip.selected,
                onClick = { onChipClicked(chip) },
                label = chip.name,
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Previews
// ---------------------------------------------------------------------------

private fun previewState(): SetupTopicsViewState {
    val kotlinChips = listOf(
        ChipData(id = "kotlin", name = "Kotlin", analyticsTag = "kotlin", selected = true),
        ChipData(id = "android", name = "Android", analyticsTag = "android", selected = true),
        ChipData(id = "compose", name = "Compose", analyticsTag = "compose", selected = false),
    ).toPersistentList()
    val webChips = listOf(
        ChipData(id = "react", name = "React", analyticsTag = "react", selected = false),
        ChipData(id = "typescript", name = "TypeScript", analyticsTag = "typescript", selected = false),
    ).toPersistentList()
    return SetupTopicsViewState(
        topics = listOf(
            "mobile" to kotlinChips,
            "web" to webChips,
        ).toPersistentList(),
    )
}

@Preview
@Composable
private fun SetupTopicsScreenPreview_Light() {
    HackertabTheme(themeMode = ThemeMode.LIGHT) {
        SetupTopicsScreen(
            state = previewState(),
            initialExpandedCategory = "mobile",
            onChipClicked = {},
            onContinue = {},
        )
    }
}

@Preview
@Composable
private fun SetupTopicsScreenPreview_Dark() {
    HackertabTheme(themeMode = ThemeMode.DARK) {
        SetupTopicsScreen(
            state = previewState(),
            initialExpandedCategory = null,
            onChipClicked = {},
            onContinue = {},
        )
    }
}
