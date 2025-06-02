package com.zrcoding.hackertab.home.presentation.cards.producthunt

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.ic_arrow_drop_up
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.home.presentation.cards.CardItemTags
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductHuntItem(product: ProductHunt) {
    val uriHandler = LocalUriHandler.current
    Row(
        modifier = Modifier
            .clickable {
                uriHandler.openUri(product.url)
            }
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.dimension.default,
                vertical = MaterialTheme.dimension.medium
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.small)
    ) {
        with(product) {

            KamelImage({ asyncPainterResource(data = imageUrl) },
                modifier = Modifier.size(52.dp),
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                contentAlignment = Alignment.Center,
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.small)
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2
                )
                Text(
                    text = description,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2
                )
                Row {
                    TextWithStartIcon(
                        text = stringResource( Res.string.comments, commentsCount),
                        icon = Res.drawable.ic_comment,
                    )
                    CardItemTags(tags = tags)
                }
            }
            Column(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colors.background, MaterialTheme.shapes.small)
                    .padding(
                        horizontal = MaterialTheme.dimension.small,
                        vertical = MaterialTheme.dimension.medium
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_drop_up),
                    contentDescription = null
                )
                Text(
                    text = "$reactions",
                    color = Color.Gray,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview()
@Composable
private fun ProductHuntItemPreview() {
    HackertabTheme {
        ProductHuntItem(
            product = ProductHunt(
                id = "elementum",
                title = "Hackertab = All sources in one app",
                description = "Hackertab is the best product ranking",
                imageUrl = "http://www.bing.com/search?q=maecenas",
                commentsCount = 7250,
                reactions = 4827,
                url = "https://www.google.com/#q=vivendo",
                tags = listOf("Kotlin")
            )
        )
    }
}