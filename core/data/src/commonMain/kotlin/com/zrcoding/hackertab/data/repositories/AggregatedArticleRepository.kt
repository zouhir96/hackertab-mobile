package com.zrcoding.hackertab.data.repositories

import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.AggregatedArticleRepository
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Wave 3G stub implementation of [AggregatedArticleRepository].
 *
 * Fans out to all [sources] in parallel using [coroutineScope] + [async] /
 * [awaitAll], collects the successful results, merges and sorts them by
 * `publishedAt` descending.
 *
 * Intentional limitations (will be addressed in Wave 5L aggregator polish):
 * - No URL-based de-duplication.
 * - No per-source loading state / partial-load UI.
 * - Failed sources are silently dropped (their items simply don't appear).
 * - Concurrency is unlimited — Wave 5L will cap it to 6 parallel requests.
 */
class AggregatedArticleRepositoryImpl(
    private val articleRepository: ArticleRepository,
) : AggregatedArticleRepository {

    override suspend fun getAggregatedFeed(
        sources: List<Source>,
        topic: Topic?,
    ): List<BaseArticle> = coroutineScope {
        val topicValue = topic?.value ?: Topic.global.value

        val deferreds = sources.map { source ->
            async {
                try {
                    fetchSource(source, topicValue)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emptyList()
                }
            }
        }

        deferreds
            .map { it.await() }
            .flatten()
            .sortedByDescending { article ->
                // Sort by publishedAt descending; non-Article types (Conference, ProductHunt,
                // GithubRepo) sort to the end since they lack a precise timestamp.
                (article as? Article)?.publishedAt?.let {
                    it.year.toLong() * 100000000L +
                        it.monthNumber.toLong() * 1000000L +
                        it.dayOfMonth.toLong() * 10000L +
                        it.hour.toLong() * 100L +
                        it.minute.toLong()
                } ?: 0L
            }
    }

    private suspend fun fetchSource(source: Source, topicValue: String): List<BaseArticle> {
        return when (source) {
            Source.GITHUB -> {
                when (val r = articleRepository.getGithubRepositories(topicValue)) {
                    is Resource.Success -> r.data
                    is Resource.Failure -> emptyList()
                }
            }
            Source.CONFERENCES -> {
                when (val r = articleRepository.getConferences(topicValue)) {
                    is Resource.Success -> r.data
                    is Resource.Failure -> emptyList()
                }
            }
            Source.PRODUCTHUNT -> {
                when (val r = articleRepository.getProductHuntProducts()) {
                    is Resource.Success -> r.data
                    is Resource.Failure -> emptyList()
                }
            }
            else -> {
                when (val r = articleRepository.getSourceArticles(source, topicValue)) {
                    is Resource.Success -> r.data
                    is Resource.Failure -> emptyList()
                }
            }
        }
    }
}
