package com.zrcoding.hackertab.data.mappers

import com.zrcoding.hackertab.database.entities.BookmarkedArticleEntity
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.network.dtos.ArticleDto
import com.zrcoding.hackertab.network.dtos.ConferenceDto
import com.zrcoding.hackertab.network.dtos.GithubDto
import com.zrcoding.hackertab.network.dtos.ProductHuntDto
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun Long.toZonedLocalDate(): LocalDateTime = Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(TimeZone.currentSystemDefault())

fun Long?.orEmpty() = this ?: 0

fun ArticleDto.toArticle() = Article(
    id = id,
    title = title,
    url = url,
    publishedAt = publishedAt.toZonedLocalDate(),
    commentsCount = commentsCount?.toLong().orEmpty(),
    reactions = pointsCount?.toLong().orEmpty(),
    tags = tags.orEmpty(),
    canonicalUrl = canonicalUrl,
    imageUrl = imageUrl,
    source = Source.fromId(source),
)

fun GithubDto.toGithubRepo() = GithubRepo(
    id = id,
    title = name,
    url = url,
    description = description,
    owner = owner,
    programmingLanguage = programmingLanguage,
    stars = stars,
    forks = forks
)

fun ConferenceDto.toConference() = Conference(
    id = id,
    url = url,
    title = title,
    startDate = startDate?.let { LocalDate.parse(it) },
    endDate = endDate?.let { LocalDate.parse(it) },
    tags = tags,
    online = online,
    city = city,
    country = country
)

fun ProductHuntDto.toProductHunt() = ProductHunt(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl,
    commentsCount = comments.toLong(),
    reactions = reactions.toLong(),
    url = url,
    tags = tags
)

// Bookmark mappers
@OptIn(ExperimentalTime::class)
fun BookmarkedArticleEntity.toBookmarkedArticle() = BookmarkedArticle(
    id = id,
    title = title,
    url = url,
    savedAt = Instant.fromEpochMilliseconds(savedAt)
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    source = source
)

@OptIn(ExperimentalTime::class)
fun BaseArticle.toBookmarkedArticleEntity(source: String): BookmarkedArticleEntity {
    return BookmarkedArticleEntity(
        id = this.id,
        title = title,
        url = url,
        savedAt = Clock.System.now().toEpochMilliseconds(),
        source = source
    )
}