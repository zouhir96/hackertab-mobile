package com.zrcoding.hackertab.home.presentation.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.FullScreenViewWithCenterText
import com.zrcoding.hackertab.design.components.Loading
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.getTagColor
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_ellipse
import com.zrcoding.hackertab.design.resources.ic_github
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.resources.loading
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.BaseModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun <T: BaseModel> CardTemplate(
    modifier: Modifier = Modifier,
    cardUiState: CardViewState<T>,
    sourceName: String,
    sourceIcon: DrawableResource,
    cardItem: @Composable (T) -> Unit,
    onRetryBtnClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxHeight().padding(top = MaterialTheme.dimension.large),
        shape = RoundedCornerShape(
            topStart = MaterialTheme.dimension.large,
            topEnd = MaterialTheme.dimension.large
        ),
        elevation = MaterialTheme.dimension.tiny,
    ) {
        Column {
            CardHeader(title = sourceName, icon = sourceIcon)
            when {
                cardUiState.isLoading -> Loading(stringResource(Res.string.loading))

                cardUiState.articles.isNotEmpty() -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.large),
                    contentPadding = PaddingValues(bottom = MaterialTheme.dimension.extraBig)
                ) {
                    items(
                        items = cardUiState.articles,
                        key = { item ->
                            item.id
                        }
                    ) { item ->
                        cardItem(item)
                        Divider(modifier = Modifier.padding(horizontal = MaterialTheme.dimension.large))
                    }
                }

                cardUiState.error != null -> FullScreenViewWithCenterText(text = cardUiState.error)
            }
        }
    }
}

@Preview()
@Composable
fun SourceItemTemplatePreview() {
    HackertabTheme {
        SourceItemTemplate(
            title = "HackerNews",
            description = "this is a lorem ipsum test",
            primaryInfoSection = {
                TextWithStartIcon(text = "il y a 1h", icon = Res.drawable.ic_time_24)
            },
            modifier = Modifier,
            tags = listOf("Java", "Kotlin", "JavaScript", "android development"),
        )
    }
}

@Composable
fun SourceItemTemplate(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = MaterialTheme.colors.onBackground,
    description: String? = null,
    primaryInfoSection: @Composable RowScope.() -> Unit,
    url: String? = null,
    tags: List<String>? = null,
) {
    val localUriHandler = LocalUriHandler.current
    Column(
        modifier = modifier
            .clickable {
                url?.let {
                    localUriHandler.openUri(it)
                }
            }
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.dimension.default,
                vertical = MaterialTheme.dimension.medium
            ),
    ) {

        Text(
            text = title,
            color = titleColor,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 2
        )
        Spacer(modifier = modifier.height(MaterialTheme.dimension.medium))

        if (description.isNullOrBlank().not()) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = description.orEmpty(),
                style = MaterialTheme.typography.body2,
                maxLines = 2
            )
            Spacer(modifier = modifier.height(MaterialTheme.dimension.small))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium)
        ) {
            primaryInfoSection()
        }
        Spacer(modifier = modifier.height(MaterialTheme.dimension.small))

        tags?.let {
            CardItemTags(modifier = Modifier.fillMaxWidth(), tags = it)
        }
    }
}

@Composable
fun CardItemTags(
    modifier: Modifier = Modifier,
    tags: List<String>,
) {
    val isTagsBlank = tags.isEmpty() || (tags.size == 1 && tags.first().isBlank())
    if (isTagsBlank) return
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium)
    ) {
        tags.forEach {
            val color = it.getTagColor()
            TextWithStartIcon(
                text = it,
                icon = Res.drawable.ic_ellipse,
                tint = color
            )
        }
    }
}


@Preview()
@Composable
fun CardHeaderPreview() {
    HackertabTheme {
        CardHeader(title = "HackerNews", icon = Res.drawable.ic_github)
    }
}

@Composable
fun CardHeader(title: String, icon: DrawableResource) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = "card header icon",
            modifier = Modifier.size(MaterialTheme.dimension.bigger)
        )
        Spacer(
            modifier = Modifier.width(MaterialTheme.dimension.large)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
        )
    }
}


