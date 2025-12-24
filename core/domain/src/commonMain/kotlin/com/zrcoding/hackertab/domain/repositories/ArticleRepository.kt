package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source

interface ArticleRepository {
    suspend fun getGithubRepositories(tag: String): Resource<List<GithubRepo>, NetworkErrors>

    suspend fun getSourceArticles(
        source: Source,
        topicId: String
    ): Resource<List<Article>, NetworkErrors>

    suspend fun getConferences(tag: String): Resource<List<Conference>, NetworkErrors>

    suspend fun getProductHuntProducts(): Resource<List<ProductHunt>, NetworkErrors>
}