package com.zrcoding.hackertab.data.repositories

import com.zrcoding.hackertab.data.mappers.toArticle
import com.zrcoding.hackertab.data.mappers.toConference
import com.zrcoding.hackertab.data.mappers.toGithubRepo
import com.zrcoding.hackertab.data.mappers.toProductHunt
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.network.api.ArticlesNetworkDataSource
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlinx.io.IOException

class ArticleRepositoryImpl(
    private val articlesNetworkDataSource: ArticlesNetworkDataSource,
) : ArticleRepository {

    override suspend fun getGithubRepositories(
        tag: String
    ): Resource<List<GithubRepo>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchGithubRepositories(tag) },
            toDomainModel = { it.toGithubRepo() }
        )
    }

    override suspend fun getSourceArticles(
        source: Source,
        topicId: String
    ): Resource<List<Article>, NetworkErrors> {
        val tag = when (source) {
            Source.HACKER_NEWS, Source.LOBSTERS, Source.INDIE_HACKERS, Source.PRODUCTHUNT -> null
            else -> topicId
        }
        return execute(
            call = { articlesNetworkDataSource.fetchFeeds(source.id, tag) },
            toDomainModel = { it.toArticle() }
        )
    }

    override suspend fun getConferences(
        tag: String
    ): Resource<List<Conference>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchConferences(tag) },
            toDomainModel = { it.toConference() }
        )
    }

    override suspend fun getProductHuntProducts(): Resource<List<ProductHunt>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchProductHuntProducts() },
            toDomainModel = { it.toProductHunt() }
        )
    }

    private inline fun <DomainModel, Dto> execute(
        call: () -> List<Dto>,
        toDomainModel: (Dto) -> DomainModel
    ): Resource<List<DomainModel>, NetworkErrors> {
        return try {
            val response = call()
            Resource.Success(response.map(toDomainModel))
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Failure(NetworkErrors.NO_INTERNET)
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            Resource.Failure(NetworkErrors.REQUEST_TIMEOUT)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(NetworkErrors.UNKNOWN)
        }
    }
}