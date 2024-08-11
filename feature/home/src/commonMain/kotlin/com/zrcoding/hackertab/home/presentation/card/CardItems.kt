package com.zrcoding.hackertab.home.presentation.card

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.TextWithStartIcon
import com.zrcoding.hackertab.design.components.getTagColor
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.claps
import com.zrcoding.hackertab.design.resources.comments
import com.zrcoding.hackertab.design.resources.forks
import com.zrcoding.hackertab.design.resources.ic_arrow_drop_up
import com.zrcoding.hackertab.design.resources.ic_baseline_fork
import com.zrcoding.hackertab.design.resources.ic_baseline_star
import com.zrcoding.hackertab.design.resources.ic_claps
import com.zrcoding.hackertab.design.resources.ic_comment
import com.zrcoding.hackertab.design.resources.ic_ellipse
import com.zrcoding.hackertab.design.resources.ic_like
import com.zrcoding.hackertab.design.resources.ic_location
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.resources.reactions
import com.zrcoding.hackertab.design.resources.score
import com.zrcoding.hackertab.design.resources.stars
import com.zrcoding.hackertab.design.resources.subreddit
import com.zrcoding.hackertab.design.theme.Flamingo
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.TextLink
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.home.domain.models.BaseModel
import com.zrcoding.hackertab.home.domain.models.Conference
import com.zrcoding.hackertab.home.domain.models.Devto
import com.zrcoding.hackertab.home.domain.models.FreeCodeCamp
import com.zrcoding.hackertab.home.domain.models.GithubRepo
import com.zrcoding.hackertab.home.domain.models.HackerNews
import com.zrcoding.hackertab.home.domain.models.Hashnode
import com.zrcoding.hackertab.home.domain.models.IndieHackers
import com.zrcoding.hackertab.home.domain.models.Lobster
import com.zrcoding.hackertab.home.domain.models.Medium
import com.zrcoding.hackertab.home.domain.models.ProductHunt
import com.zrcoding.hackertab.home.domain.models.Reddit
import com.zrcoding.hackertab.home.domain.usecases.BuildConferenceDisplayedDateUseCase
import com.zrcoding.hackertab.home.presentation.utils.openUrlInBrowser
import com.zrcoding.hackertab.home.presentation.utils.timeAgo
import com.zrcoding.hackertab.settings.domain.models.SourceName
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SourceName.ToCardItem(model: BaseModel) = when (this) {
    SourceName.GITHUB -> GithubItem(post = model as GithubRepo)
    SourceName.HACKER_NEWS -> HackerNewsItem(new = model as HackerNews)
    SourceName.REDDIT -> RedditItem(reddit = model as Reddit)
    SourceName.FREE_CODE_CAMP -> FreeCodeCampItem(post = model as FreeCodeCamp)
    SourceName.CONFERENCES -> ConferenceItem(conf = model as Conference)
    SourceName.DEVTO -> DevtoItem(devto = model as Devto)
    SourceName.HASH_NODE -> HashnodeItem(hashnode = model as Hashnode)
    SourceName.PRODUCTHUNT -> ProductHuntItem(product = model as ProductHunt)
    SourceName.INDIE_HACKERS -> IndieHackersItem(indieHackers = model as IndieHackers)
    SourceName.LOBSTERS -> LobstersItem(lobster = model as Lobster)
    SourceName.MEDIUM -> MediumItem(medium = model as Medium)
}

@Composable
fun GithubItem(post: GithubRepo) {
    SourceItemTemplate(
        title = "${post.owner}/${post.name}",
        description = post.description.trim().ifEmpty { null },
        primaryInfoSection = {
            TextWithStartIcon(
                icon = Res.drawable.ic_ellipse,
                tint = post.programmingLanguage.getTagColor(),
                text = post.programmingLanguage
            )
            TextWithStartIcon(
                icon = Res.drawable.ic_baseline_star,
                text = stringResource(Res.string.stars, post.stars)
            )

            TextWithStartIcon(
                icon = Res.drawable.ic_baseline_fork,
                text = stringResource(Res.string.forks, post.forks)
            )
        },
        url = post.url,
        titleColor = TextLink,
    )
}

@Preview()
@Composable
private fun GithubItemPreview() {
    HackertabTheme {
        GithubItem(
            post = GithubRepo(
                id = "habeo",
                name = "Jetpack compose",
                description = "This is a fake repo for preview",
                owner = "Celina Wells",
                url = "https://www.google.com/#q=propriae",
                programmingLanguage = "Kotlin",
                stars = 20,
                forks = 15
            )
        )
    }
}

@Composable
fun HackerNewsItem(new: HackerNews) {
    SourceItemTemplate(
        title = new.title,
        primaryInfoSection = {
            TextWithStartIcon(
                text = stringResource(Res.string.score, new.score),
                textColor = Flamingo,
                icon = Res.drawable.ic_ellipse,
                tint = Flamingo
            )
            TextWithStartIcon(
                icon = Res.drawable.ic_time_24,
                text = new.time.timeAgo()
            )
            TextWithStartIcon(
                text = stringResource(Res.string.comments, new.descendants),
                icon = Res.drawable.ic_comment
            )
        },
        url = new.url,
    )
}

@Preview()
@Composable
fun HackerNewsItemPreview() {
    HackertabTheme {
        HackerNewsItem(
            new = HackerNews(
                id = "",
                title = "React is the best web framework ever React is the best web framework ever",
                url = "https://www.google.com/#q=propriae",
                time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                descendants = 1234,
                score = 1234
            )
        )
    }
}

@Composable
fun RedditItem(reddit: Reddit) {
    SourceItemTemplate(
        title = reddit.title,
        primaryInfoSection = {
            TextWithStartIcon(
                icon = Res.drawable.ic_time_24,
                text = reddit.date.timeAgo()
            )
            TextWithStartIcon(
                text = stringResource( Res.string.score, reddit.score),
                textColor = Flamingo,
                icon = Res.drawable.ic_ellipse,
                tint = Flamingo
            )
            TextWithStartIcon(
                text = stringResource( Res.string.comments, reddit.commentsCount),
                icon = Res.drawable.ic_comment
            )
        },
        url = reddit.url,
        tags = listOf(stringResource( Res.string.subreddit, reddit.subreddit))
    )
}

@Preview()
@Composable
fun RedditItemPreview() {
    HackertabTheme {
        RedditItem(
            reddit = Reddit(
                id = "similique",
                title = "React is the best web framework ever React is the best web framework ever",
                subreddit = "reactDevs",
                url = "https://www.google.com/#q=propriae",
                score = 118,
                commentsCount = 30,
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
    }
}

@Composable
fun FreeCodeCampItem(post: FreeCodeCamp) {
    SourceItemTemplate(
        title = post.title.trim(),
        description = null,
        primaryInfoSection = {
            TextWithStartIcon(
                icon = Res.drawable.ic_time_24,
                text = post.isoDate.timeAgo()
            )
        },
        url = post.url,
        tags = post.categories,
    )
}

@Preview()
@Composable
private fun FreeCodeCampItemPreview() {
    HackertabTheme {
        FreeCodeCampItem(
            post = FreeCodeCamp(
                id = "similique",
                title = "React is the best web framework ever React is the best web framework ever",
                url = "https://www.google.com/#q=propriae",
                isoDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                categories = listOf()
            )
        )
    }
}

@Composable
fun ConferenceItem(conf: Conference) {
    val date = BuildConferenceDisplayedDateUseCase(conf)
    val location = if (conf.online) {
        "\uD83C\uDF10 Online"
    } else {
        "${conf.country} ${conf.city.orEmpty()}"
    }
    SourceItemTemplate(
        title = conf.title.trim(),
        description = null,
        primaryInfoSection = {
            TextWithStartIcon(
                icon = Res.drawable.ic_location,
                text = location
            )
            TextWithStartIcon(
                icon = Res.drawable.ic_time_24,
                text = date
            )
        },
        url = conf.url,
        tags = listOf(conf.tag),
    )
}

@Preview()
@Composable
private fun ConferenceItemPreview() {
    HackertabTheme {
        ConferenceItem(
            conf = Conference(
                id = "aliquam",
                url = "https://www.google.com/#q=metus",
                title = "KotlinConf",
                startDate = LocalDateTime(2025, 10, 13, 0, 0, 0),
                endDate = LocalDateTime(2025, 10, 15, 0, 0, 0),
                tag = "Kotlin",
                online = true,
                city = "Berlin",
                country = "Germany"
            )
        )
    }
}

@Composable
fun DevtoItem(devto: Devto) {
    with(devto) {
        SourceItemTemplate(
            title = title.trim(),
            description = null,
            primaryInfoSection = {
                TextWithStartIcon(
                    text = date.timeAgo(),
                    icon = Res.drawable.ic_time_24,
                )
                TextWithStartIcon(
                    text = stringResource( Res.string.comments, commentsCount),
                    icon = Res.drawable.ic_comment,
                )
                TextWithStartIcon(
                    text = stringResource( Res.string.reactions, reactions),
                    icon = Res.drawable.ic_like
                )
            },
            url = url,
            tags = tags,
        )
    }
}

@Preview()
@Composable
private fun DevtoItemPreview() {
    HackertabTheme {
        DevtoItem(
            devto = Devto(
                id = "convenire",
                title = "Jetpack compose is the best",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 7527,
                reactions = 6147,
                url = "https://search.yahoo.com/search?p=mnesarchum",
                tags = listOf("Kotlin")
            )
        )
    }
}

@Composable
fun HashnodeItem(hashnode: Hashnode) {
    with(hashnode) {
        SourceItemTemplate(
            title = title.trim(),
            description = null,
            primaryInfoSection = {
                TextWithStartIcon(
                    text = date.timeAgo(),
                    icon = Res.drawable.ic_time_24,
                )
                TextWithStartIcon(
                    text = stringResource( Res.string.comments, commentsCount),
                    icon = Res.drawable.ic_comment,
                )
                TextWithStartIcon(
                    text = stringResource( Res.string.reactions, reactions),
                    icon = Res.drawable.ic_like
                )
            },
            url = url,
            tags = tags,
        )
    }
}

@Preview()
@Composable
private fun HashnodeItemPreview() {
    HackertabTheme {
        HashnodeItem(
            hashnode = Hashnode(
                id = "reque",
                title = "Just migrate your app to jetpack compose",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 4459,
                reactions = 4022,
                url = "http://www.bing.com/search?q=vocent",
                tags = listOf("Kotlin")
            )
        )
    }
}

@Composable
fun ProductHuntItem(product: ProductHunt) {
    Row(
        modifier = Modifier
            .clickable {
                openUrlInBrowser(url = product.url)
            }
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.dimension.default,
                vertical = MaterialTheme.dimension.medium
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.small)
    ) {
        with(product) {
            KamelImage(
                modifier = Modifier.size(52.dp),
                resource = asyncPainterResource(imageUrl),
                contentDescription = null
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

@Composable
fun IndieHackersItem(indieHackers: IndieHackers) {
    with(indieHackers) {
        SourceItemTemplate(
            title = title,
            url = url,
            primaryInfoSection = {
                TextWithStartIcon(
                    text = stringResource( Res.string.score, reactions),
                    textColor = Color(0xFF4799eb),
                    icon = Res.drawable.ic_arrow_drop_up,
                    tint = Color(0xFF4799eb)
                )
                TextWithStartIcon(
                    text = date.timeAgo(),
                    icon = Res.drawable.ic_time_24,
                )
                TextWithStartIcon(
                    text = stringResource( Res.string.comments, commentsCount),
                    icon = Res.drawable.ic_comment,
                )
            }
        )
    }
}

@Preview()
@Composable
private fun IndieHackersItemPreview() {
    HackertabTheme {
        IndieHackersItem(
            indieHackers = IndieHackers(
                id = "fastidii",
                title = "Hackertab will pay someday",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 1349,
                reactions = 1634,
                url = "http://www.bing.com/search?q=dicam"
            )
        )
    }
}

@Composable
fun LobstersItem(lobster: Lobster) {
    with(lobster) {
        SourceItemTemplate(
            title = title,
            url = url,
            primaryInfoSection = {
                TextWithStartIcon(
                    text = stringResource( Res.string.score, reactions),
                    textColor = Flamingo,
                    icon = Res.drawable.ic_arrow_drop_up,
                    tint = Flamingo
                )
                TextWithStartIcon(
                    text = date.timeAgo(),
                    icon = Res.drawable.ic_time_24,
                )
                TextWithStartIcon(
                    modifier = Modifier.clickable {
                        openUrlInBrowser(commentsUrl)
                    },
                    text = stringResource( Res.string.comments, commentsCount),
                    textColor = Color(0xFF4799eb),
                    textDecoration = TextDecoration.Underline,
                    icon = Res.drawable.ic_comment,
                    tint = Color(0xFF4799eb)
                )
            }
        )
    }
}

@Preview()
@Composable
private fun LobstersItemPreview() {
    HackertabTheme {
        LobstersItem(
            lobster = Lobster(
                id = "feugiat",
                title = "XML based apps are worst",
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 4849,
                reactions = 8948,
                url = "https://search.yahoo.com/search?p=habitasse",
                commentsUrl = "http://www.bing.com/search?q=brute"
            )
        )
    }
}

@Composable
fun MediumItem(medium: Medium) {
    with(medium) {
        SourceItemTemplate(
            title = title,
            url = url,
            primaryInfoSection = {
                TextWithStartIcon(
                    text = stringResource( Res.string.claps, claps),
                    icon = Res.drawable.ic_claps,
                    tint = MaterialTheme.colors.onBackground
                )
                TextWithStartIcon(
                    text = stringResource( Res.string.comments, commentsCount),
                    icon = Res.drawable.ic_comment,
                )
                TextWithStartIcon(
                    text = date.timeAgo(),
                    icon = Res.drawable.ic_time_24,
                )
            }
        )
    }
}

@Preview()
@Composable
private fun MediumItemPreview() {
    HackertabTheme {
        MediumItem(
            medium = Medium(
                id = "porttitor",
                title = "Coroutines explained in a simple way",
                date =Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                commentsCount = 9783,
                claps = 9145,
                url = "https://duckduckgo.com/?q=minim"
            )
        )
    }
}