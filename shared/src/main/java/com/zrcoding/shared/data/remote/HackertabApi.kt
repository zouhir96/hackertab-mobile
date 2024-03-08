package com.zrcoding.shared.data.remote

import com.zrcoding.shared.data.remote.dtos.ArticleDto
import com.zrcoding.shared.data.remote.dtos.GithubDto
import retrofit2.http.GET
import retrofit2.http.Path

interface HackertabApi {
    companion object {
        private const val PATH_TAG = "tag"
        private const val PATH_DATE_RANGE = "date_range"
    }

    @GET("hackernews.json?")
    suspend fun fetchHackerNewsArticles(): List<ArticleDto>

    @GET("reddit/{$PATH_TAG}.json")
    suspend fun fetchRedditArticles(
        @Path(PATH_TAG) tag: String
    ): List<ArticleDto>

    @GET("freecodecamp/{$PATH_TAG}.json")
    suspend fun fetchFreeCodeCampArticles(
        @Path(PATH_TAG) tag: String
    ): List<ArticleDto>

    @GET("github/{$PATH_TAG}/{$PATH_DATE_RANGE}.json")
    suspend fun fetchGithubArticles(
        @Path(PATH_TAG) tag: String,
        @Path(PATH_DATE_RANGE) dateRange: String = "daily",
    ): List<GithubDto>
}