package com.zrcoding.hackertab.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.ErrorMsgWithBtn
import com.zrcoding.hackertab.design.components.Loading
import com.zrcoding.hackertab.design.components.RoundedIconButton
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.app_title
import com.zrcoding.hackertab.design.resources.common_settings
import com.zrcoding.hackertab.design.resources.ic_baseline_arrow_back_ios
import com.zrcoding.hackertab.design.resources.ic_baseline_arrow_forward
import com.zrcoding.hackertab.design.resources.ic_refresh
import com.zrcoding.hackertab.design.resources.ic_settings
import com.zrcoding.hackertab.design.resources.no_source_selected
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.home.presentation.card.CardTemplate
import com.zrcoding.hackertab.home.presentation.card.ToCardItem
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun HomeRoute(
    viewModel: HomeScreenViewModel = koinInject(),
    isExpandedScreen: Boolean,
    onNavigateToSettings: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsState().value
    HomeScreen(
        modifier = Modifier,
        isExpandedScreen = isExpandedScreen,
        viewState = viewState,
        onRefreshBtnClick = viewModel::loadData,
        onSettingBtnClick = onNavigateToSettings
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean,
    viewState: HomeScreenViewState,
    onRefreshBtnClick: () -> Unit,
    onSettingBtnClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = MaterialTheme.dimension.none
            ) {
                HomeScreenTopAppBar(
                    onRefreshBtnClick = onRefreshBtnClick,
                    onSettingBtnClick = onSettingBtnClick
                )
            }
        },
    ) {
        when (viewState) {
            HomeScreenViewState.Loading -> Loading()
            is HomeScreenViewState.Cards -> if (viewState.cardViewStates.isEmpty()) {
                ErrorMsgWithBtn(
                    text = Res.string.no_source_selected,
                    btnText = Res.string.common_settings,
                    onBtnClicked = onSettingBtnClick
                )
            } else {
                HomeScreenCardsPager(
                    modifier = Modifier.padding(it),
                    pageSize = if (isExpandedScreen) PageSize.Fixed(400.dp) else PageSize.Fill,
                    cardViewStates = viewState.cardViewStates,
                    onRefreshBtnClick = onRefreshBtnClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    HackertabTheme {
        HomeScreen(
            isExpandedScreen = false,
            viewState = HomeScreenViewState.Loading,
            onRefreshBtnClick = {},
            onSettingBtnClick = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenEmptyPreview() {
    HackertabTheme {
        HomeScreen(
            isExpandedScreen = false,
            viewState = HomeScreenViewState.Cards(emptyList()),
            onRefreshBtnClick = {},
            onSettingBtnClick = {}
        )
    }
}

@Composable
private fun HomeScreenTopAppBar(
    onRefreshBtnClick: () -> Unit,
    onSettingBtnClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(start = MaterialTheme.dimension.large),
    ) {
        Text(
            text = stringResource(Res.string.app_title),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onRefreshBtnClick() },
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(MaterialTheme.dimension.none),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.primaryVariant
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_refresh),
                contentDescription = "refresh button",
                tint = MaterialTheme.colors.onBackground
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.dimension.small))
        Button(
            onClick = { onSettingBtnClick() },
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(MaterialTheme.dimension.none),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.primaryVariant
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_settings),
                contentDescription = "settings button",
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Preview()
@Composable
private fun HomeScreenTopAppBarPreview() {
    HackertabTheme {
        HomeScreenTopAppBar({}, {})
    }
}

@Composable
private fun HomeScreenCardsPager(
    modifier: Modifier = Modifier,
    pageSize: PageSize,
    cardViewStates: List<CardViewState>,
    onRefreshBtnClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { cardViewStates.size }
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            contentPadding = PaddingValues(
                start = MaterialTheme.dimension.default,
                end = MaterialTheme.dimension.medium
            ),
            pageSize = pageSize
        ) { page ->
            cardViewStates.getOrNull(page)?.let { state ->
                CardTemplate(
                    cardUiState = state,
                    cardItem = { sourceName, model ->
                        sourceName.ToCardItem(model = model)
                    },
                    onRetryBtnClick = onRefreshBtnClick
                )
            }
        }
        if(pagerState.currentPage > 0) {
            RoundedIconButton(
                modifier = Modifier
                    .alpha(0.6f)
                    .padding(end = MaterialTheme.dimension.default)
                    .align(Alignment.CenterStart),
                size = 60.dp,
                icon = Res.drawable.ic_baseline_arrow_back_ios
            ) {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            }
        }
        if(pagerState.currentPage < pagerState.pageCount - 1) {
            RoundedIconButton(
                modifier = Modifier
                    .alpha(0.6f)
                    .padding(end = MaterialTheme.dimension.default)
                    .align(Alignment.CenterEnd),
                size = 60.dp,
                icon = Res.drawable.ic_baseline_arrow_forward
            ) {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }
    }
}



