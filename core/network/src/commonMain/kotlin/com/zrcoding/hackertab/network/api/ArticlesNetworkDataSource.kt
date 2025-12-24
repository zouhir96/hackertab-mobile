package com.zrcoding.hackertab.network.api

import com.zrcoding.hackertab.network.dtos.ArticleDto
import com.zrcoding.hackertab.network.dtos.ConferenceDto
import com.zrcoding.hackertab.network.dtos.GithubDto
import com.zrcoding.hackertab.network.dtos.ProductHuntDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class ArticlesNetworkDataSource(private val httpClient: HttpClient) {

    companion object {
        private const val DAILY_DATE_RANGE = "weekly"
    }

    suspend fun fetchGithubRepositories(tag: String): List<GithubDto> {
        return httpClient.get("repos?range=$DAILY_DATE_RANGE&tags=$tag").body()
    }

    suspend fun fetchFeeds(source: String, tag: String?): List<ArticleDto> {
        return httpClient.get("feeds?source=$source${tag?.let { "&tags=$tag" }.orEmpty()}").body()
    }

    suspend fun fetchConferences(tag: String): List<ConferenceDto> {
        return httpClient.get("conferences?tags=$tag").body()
    }

    suspend fun fetchProductHuntProducts(): List<ProductHuntDto> {
        val date = Clock.System.now()
            .toLocalDateTime(TimeZone.UTC)
            .date
            .toString()
        return httpClient.get("products?date=$date").body()
    }
}