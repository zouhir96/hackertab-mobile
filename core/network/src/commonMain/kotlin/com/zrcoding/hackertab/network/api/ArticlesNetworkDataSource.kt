package com.zrcoding.hackertab.network.api

import com.zrcoding.hackertab.network.dtos.ArticleDto
import com.zrcoding.hackertab.network.dtos.ConferenceDto
import com.zrcoding.hackertab.network.dtos.GithubDto
import com.zrcoding.hackertab.network.dtos.IndieHackersDto
import com.zrcoding.hackertab.network.dtos.ProductHuntDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ArticlesNetworkDataSource(private val httpClient: HttpClient) {

    companion object {
        private const val DAILY_DATE_RANGE = "daily"
    }

    suspend fun fetchHackerNewsArticles(): List<ArticleDto> {
        return httpClient.get("hackernews.json").body()
    }

     suspend fun fetchRedditArticles(tag: String): List<ArticleDto> {
        return httpClient.get("reddit/$tag.json").body()
    }

     suspend fun fetchFreeCodeCampArticles(tag: String): List<ArticleDto> {
        return httpClient.get("freecodecamp/$tag.json").body()
    }

     suspend fun fetchGithubRepositories(tag: String): List<GithubDto> {
        return httpClient.get("github/$tag/$DAILY_DATE_RANGE.json").body()
    }

     suspend fun fetchConferences(tag: String): List<ConferenceDto> {
        return httpClient.get("conferences/$tag.json").body()
    }

     suspend fun fetchDevtoArticles(tag: String): List<ArticleDto> {
        return httpClient.get("devto/$tag.json").body()
    }

     suspend fun fetchHashnodeArticles(tag: String): List<ArticleDto> {
        return httpClient.get("hashnode/$tag.json").body()
    }

     suspend fun fetchProductHuntProducts(): List<ProductHuntDto> {
        return httpClient.get("producthunt.json").body()
    }

     suspend fun fetchIndieHackersArticles(): List<IndieHackersDto> {
        return httpClient.get("indiehackers.json").body()
    }

     suspend fun fetchLobstersArticles(): List<ArticleDto> {
        return httpClient.get("lobsters.json").body()
    }

    suspend fun fetchMediumArticles(tag: String): List<ArticleDto> {
        return httpClient.get("medium/$tag.json").body()
    }
}