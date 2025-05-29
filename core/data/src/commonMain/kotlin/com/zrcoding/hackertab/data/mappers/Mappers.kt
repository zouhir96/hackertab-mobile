package com.zrcoding.hackertab.data.mappers

import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.Devto
import com.zrcoding.hackertab.domain.models.FreeCodeCamp
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.HackerNews
import com.zrcoding.hackertab.domain.models.Hashnode
import com.zrcoding.hackertab.domain.models.IndieHackers
import com.zrcoding.hackertab.domain.models.Lobster
import com.zrcoding.hackertab.domain.models.Medium
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Reddit
import com.zrcoding.hackertab.network.dtos.ArticleDto
import com.zrcoding.hackertab.network.dtos.ConferenceDto
import com.zrcoding.hackertab.network.dtos.GithubDto
import com.zrcoding.hackertab.network.dtos.IndieHackersDto
import com.zrcoding.hackertab.network.dtos.ProductHuntDto
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toZonedLocalDate(): LocalDateTime = Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(TimeZone.currentSystemDefault())

fun Long?.orEmpty() = this ?: 0

fun ArticleDto.toFreeCodeCamp() = FreeCodeCamp(
    id = id,
    title = title,
    url = url,
    isoDate = publishedAt.toZonedLocalDate(),
    categories = tags.orEmpty()
)

fun GithubDto.toGithubRepo() = GithubRepo(
    id = id,
    name = title,
    description = description,
    owner = owner,
    url = url,
    programmingLanguage = programmingLanguage,
    stars = stars.toInt(),
    forks = forks.toInt()
)

fun ArticleDto.toHackerNews() = HackerNews(
    id = id,
    title = title,
    url = url,
    time = publishedAt.toZonedLocalDate(),
    descendants = comments?.toLong().orEmpty(),
    score = reactions?.toLong().orEmpty(),
)

fun ArticleDto.toReddit() = Reddit(
    id = id,
    title = title,
    subreddit = subreddit.orEmpty(),
    url = url,
    score = reactions?.toLong().orEmpty(),
    commentsCount = comments?.toLong().orEmpty(),
    date = publishedAt.toZonedLocalDate()
)

fun ConferenceDto.toConference() = Conference(
    id = id,
    url = url,
    title = title,
    startDate = startDate?.toZonedLocalDate(),
    endDate = endDate?.toZonedLocalDate(),
    tag = tag,
    online = online,
    city = city,
    country = country
)

fun ArticleDto.toDevto() = Devto(
    id = id,
    title = title,
    date = publishedAt.toZonedLocalDate(),
    commentsCount = comments?.toLong().orEmpty(),
    reactions = reactions?.toLong().orEmpty(),
    url = url,
    tags = tags.orEmpty()
)

fun ArticleDto.toHashnode() = Hashnode(
    id = id,
    title = title,
    date = publishedAt.toZonedLocalDate(),
    commentsCount = comments?.toLong().orEmpty(),
    reactions = reactions?.toLong().orEmpty(),
    url = url,
    tags = tags.orEmpty().take(3)
)

fun ProductHuntDto.toProductHunt() = ProductHunt(
    id = id,
    title = title,
    description = description.orEmpty(),
    imageUrl = imageUrl?.split("?")?.firstOrNull().orEmpty(),
    commentsCount = comments?.toLong().orEmpty(),
    reactions = reactions?.toLong().orEmpty(),
    url = url,
    tags = tags.orEmpty().take(1)
)

fun IndieHackersDto.toIndieHackers() = IndieHackers(
    id = id ?: Clock.System.now().toEpochMilliseconds().toString(),
    title = title,
    date = publishedAt.toZonedLocalDate(),
    commentsCount = comments?.toLongOrNull().orEmpty(),
    reactions = reactions?.toLongOrNull().orEmpty(),
    url = url,
)

fun ArticleDto.toLobster() = Lobster(
    id = id,
    title = title,
    date = publishedAt.toZonedLocalDate(),
    commentsCount = comments?.toLong().orEmpty(),
    reactions = reactions?.toLong().orEmpty(),
    url = url,
    commentsUrl = commentsUrl.orEmpty()
)

fun ArticleDto.toMedium() = Medium(
    id = id,
    title = title,
    date = publishedAt.toZonedLocalDate(),
    commentsCount = comments?.toLong().orEmpty(),
    claps = reactions?.toLong().orEmpty(),
    url = url,
)