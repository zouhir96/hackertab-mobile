package com.zrcoding.hackertab.home.data.repositories

import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.home.data.mappers.toConference
import com.zrcoding.hackertab.home.data.mappers.toDevto
import com.zrcoding.hackertab.home.data.mappers.toFreeCodeCamp
import com.zrcoding.hackertab.home.data.mappers.toGithubRepo
import com.zrcoding.hackertab.home.data.mappers.toHackerNews
import com.zrcoding.hackertab.home.data.mappers.toHashnode
import com.zrcoding.hackertab.home.data.mappers.toIndieHackers
import com.zrcoding.hackertab.home.data.mappers.toLobster
import com.zrcoding.hackertab.home.data.mappers.toMedium
import com.zrcoding.hackertab.home.data.mappers.toProductHunt
import com.zrcoding.hackertab.home.data.mappers.toReddit
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
import com.zrcoding.hackertab.home.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.network.api.ArticlesNetworkDataSource
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.utils.io.errors.IOException

class ArticleRepositoryImpl(
    private val articlesNetworkDataSource: ArticlesNetworkDataSource,
) : ArticleRepository {
    override suspend fun getHackerNewsArticles(): Resource<List<HackerNews>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchHackerNewsArticles() },
            toDomainModel = { it.toHackerNews() }
        )
    }

    override suspend fun getRedditArticles(tag: String): Resource<List<Reddit>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchRedditArticles(tag) },
            toDomainModel = { it.toReddit() }
        )
    }

    override suspend fun getFreeCodeCampArticles(
        tag: String
    ): Resource<List<FreeCodeCamp>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchFreeCodeCampArticles(tag) },
            toDomainModel = { it.toFreeCodeCamp() }
        )
    }

    override suspend fun getGithubRepositories(
        tag: String
    ): Resource<List<GithubRepo>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchGithubRepositories(tag) },
            toDomainModel = { it.toGithubRepo() }
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

    override suspend fun getDevtoArticles(
        tag: String
    ): Resource<List<Devto>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchDevtoArticles(tag) },
            toDomainModel = { it.toDevto() }
        )
    }

    override suspend fun getHashnodeArticles(
        tag: String
    ): Resource<List<Hashnode>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchHashnodeArticles(tag) },
            toDomainModel = { it.toHashnode() }
        )
    }

    override suspend fun getProductHuntProducts(): Resource<List<ProductHunt>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchProductHuntProducts() },
            toDomainModel = { it.toProductHunt() }
        )
    }

    override suspend fun getIndieHackersArticles(): Resource<List<IndieHackers>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchIndieHackersArticles() },
            toDomainModel = { it.toIndieHackers() }
        )
    }

    override suspend fun getLobstersArticles(): Resource<List<Lobster>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchLobstersArticles() },
            toDomainModel = { it.toLobster() }
        )
    }

    override suspend fun getMediumArticles(tag: String): Resource<List<Medium>, NetworkErrors> {
        return execute(
            call = { articlesNetworkDataSource.fetchMediumArticles(tag) },
            toDomainModel = { it.toMedium() }
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