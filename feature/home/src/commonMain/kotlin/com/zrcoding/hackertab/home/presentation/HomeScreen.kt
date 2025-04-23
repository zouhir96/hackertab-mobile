package com.zrcoding.hackertab.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zrcoding.hackertab.design.components.ErrorMsgWithBtn
import com.zrcoding.hackertab.design.components.icon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.app_title
import com.zrcoding.hackertab.design.resources.common_settings
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Source
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    viewModel: HomeScreenViewModel = koinViewModel(),
    isExpandedScreen: Boolean,
    onNavigateToSettings: () -> Unit,
    onNavigateToSourcesSettings: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    HomeScreen(
        modifier = Modifier,
        isExpandedScreen = isExpandedScreen,
        viewState = viewState,
        onSettingBtnClick = onNavigateToSettings,
        onNavigateToSourcesSettings = onNavigateToSourcesSettings
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean,
    viewState: HomeScreenViewState,
    onSettingBtnClick: () -> Unit,
    onNavigateToSourcesSettings: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            HomeScreenTopAppBar(
                onNavigationBtnClick = {
                    scope.launch { drawerState.open() }
                },
                onSettingBtnClick = onSettingBtnClick
            )
        },
        drawerContent = {
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            HomeScreenDrawer(
                enabledSources = viewState.enabledSources,
                currentRoute = currentRoute,
                onItemClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(it) {
                        popUpTo(viewState.enabledSources.first().id)
                        launchSingleTop = true
                    }
                }
            )
        },
        drawerShape = RoundedCornerShape(
            topEnd = MaterialTheme.dimension.medium,
            bottomEnd = MaterialTheme.dimension.medium
        )
    ) {
        val startDestination = viewState.enabledSources.firstOrNull()?.id
        if (startDestination != null) {
            HomeScreenNavHost(
                navController = navController,
                startDestination = startDestination,
                enabledSourcesIds = viewState.enabledSources.map { it.id }
            )
        } else if (viewState.isLoading.not()) {
            ErrorMsgWithBtn(
                text = "You didn't follow any source, you can follow your favorite sources in settings !!",
                btnText = Res.string.common_settings,
                onBtnClicked = onNavigateToSourcesSettings
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeScreenTopAppBar(
    onNavigationBtnClick: () -> Unit,
    onSettingBtnClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.app_title),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Visible,
                maxLines = 1
            )
        },
        navigationIcon = {
            Card(
                onClick = onNavigationBtnClick,
                shape = CircleShape,
            ) {
                IconButton(
                    onClick = onNavigationBtnClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Navigation button to show drawer",
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        },
        actions = {
            Card(
                onClick = onSettingBtnClick,
                shape = CircleShape,
            ) {
                IconButton(
                    onClick = onSettingBtnClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "settings button to open settings",
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = MaterialTheme.dimension.none
    )
}

@Preview
@Composable
private fun HomeScreenTopAppBarPreview() {
    HackertabTheme {
        HomeScreenTopAppBar({}, {})
    }
}

@Composable
private fun HomeScreenDrawer(
    enabledSources: List<Source>,
    currentRoute: String?,
    onItemClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(MaterialTheme.dimension.big),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.large)
    ) {
        Text(text = "Hackertab", style = MaterialTheme.typography.h5)
        Divider()
        Column {
            enabledSources.forEach {
                HomeScreenDrawerItem(
                    icon = it.icon,
                    title = it.label,
                    selected = currentRoute == it.id,
                    onClick = { onItemClick(it.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeScreenDrawerItem(
    icon: DrawableResource,
    title: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
        elevation = 0.4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimension.medium)
        ) {
            Image(
                modifier = Modifier.size(MaterialTheme.dimension.bigger),
                painter = painterResource(icon),
                contentDescription = "Source icon",
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimension.large))
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(MaterialTheme.dimension.bigger),
                imageVector = Icons.AutoMirrored.Default.ArrowRight,
                contentDescription = "Arrow forward",
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    HackertabTheme {
        HomeScreen(
            isExpandedScreen = false,
            viewState = HomeScreenViewState(isLoading = true),
            onSettingBtnClick = {},
            onNavigateToSourcesSettings = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenEmptyPreview() {
    HackertabTheme {
        HomeScreen(
            isExpandedScreen = false,
            viewState = HomeScreenViewState(emptyList()),
            onSettingBtnClick = {},
            onNavigateToSourcesSettings = {}
        )
    }
}



