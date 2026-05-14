package com.zrcoding.hackertab.data.repositories

import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.SourceLoadState
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.AggregatedArticleRepository
import com.zrcoding.hackertab.domain.repositories.AggregatedFeedResult
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Wave 5L aggregator polish — parallel fan-out with:
 *  - Semaphore-capped concurrency (6 in-flight max).
 *  - 5-minute per-source response cache.
 *  - URL-based de-duplication (lowercase, strip query/fragment, trim trailing /).
 *  - Stable source-priority tiebreaker when publishedAt ties / is absent.
 *  - Per-source load state surfaced for partial-reveal UI (Issue 3 / E6).
 *
 * The Flow emits once on subscription (all sources Loading, articles empty),
 * then once per source completion. Each emission reflects all sources that
 * have completed so far.
 */
@OptIn(ExperimentalTime::class)
class AggregatedArticleRepositoryImpl(
    private val articleRepository: ArticleRepository,
) : AggregatedArticleRepository {

    private val cacheMutex = Mutex()
    private val cache = mutableMapOf<CacheKey, Pair<Instant, Resource<List<BaseArticle>, NetworkErrors>>>()

    override fun observeAggregatedFeed(
        sources: List<Source>,
        topic: Topic?,
        refresh: Boolean,
    ): Flow<AggregatedFeedResult> = channelFlow {
        if (sources.isEmpty()) {
            send(AggregatedFeedResult(emptyList(), emptyMap(), isPartialReveal = false))
            awaitClose { /* nothing */ }
            return@channelFlow
        }

        val semaphore = Semaphore(MAX_PARALLEL)
        val stateMutex = Mutex()
        val perSourceState = mutableMapOf<Source, SourceLoadState>().apply {
            sources.forEach { put(it, SourceLoadState.Loading) }
        }
        val resultsBySource = mutableMapOf<Source, List<BaseArticle>>()

        // Initial Loading emission so the UI can show the skeleton immediately.
        send(
            AggregatedFeedResult(
                articles = emptyList(),
                perSourceState = perSourceState.toMap(),
                isPartialReveal = true,
            ),
        )

        coroutineScope {
            sources.map { source ->
                async {
                    semaphore.withPermit {
                        val result = fetchOrCached(source, topic, refresh)
                        val newState = stateMutex.withLock {
                            when (result) {
                                is Resource.Success -> {
                                    resultsBySource[source] = result.data
                                    perSourceState[source] = SourceLoadState.Loaded(result.data.size)
                                }
                                is Resource.Failure -> {
                                    perSourceState[source] = SourceLoadState.Failed(result.error)
                                }
                            }
                            val snapshot = perSourceState.toMap()
                            val merged = computeMergedArticles(resultsBySource.toMap())
                            val loaded = snapshot.values.count { it !is SourceLoadState.Loading }
                            AggregatedFeedResult(
                                articles = merged,
                                perSourceState = snapshot,
                                isPartialReveal = loaded < (sources.size + 1) / 2,
                            )
                        }
                        send(newState)
                    }
                }
            }.awaitAll()
        }
    }

    private suspend fun fetchOrCached(
        source: Source,
        topic: Topic?,
        refresh: Boolean,
    ): Resource<List<BaseArticle>, NetworkErrors> {
        val key = CacheKey(source, topic?.value)
        val now = Clock.System.now()

        if (!refresh) {
            val hit = cacheMutex.withLock {
                cache[key]?.takeIf { (now - it.first) < CACHE_TTL }
            }
            if (hit != null) return hit.second
        }

        val fresh = fetchForSource(source, topic)
        // Only cache successes; failures should be re-tried on next observe.
        if (fresh is Resource.Success) {
            cacheMutex.withLock {
                cache[key] = now to fresh
            }
        }
        return fresh
    }

    private suspend fun fetchForSource(
        source: Source,
        topic: Topic?,
    ): Resource<List<BaseArticle>, NetworkErrors> {
        val topicValue = topic?.value ?: Topic.global.value
        @Suppress("UNCHECKED_CAST")
        return when (source) {
            Source.GITHUB -> articleRepository.getGithubRepositories(topicValue)
                as Resource<List<BaseArticle>, NetworkErrors>
            Source.CONFERENCES -> articleRepository.getConferences(topicValue)
                as Resource<List<BaseArticle>, NetworkErrors>
            Source.PRODUCTHUNT -> articleRepository.getProductHuntProducts()
                as Resource<List<BaseArticle>, NetworkErrors>
            else -> articleRepository.getSourceArticles(source, topicValue)
                as Resource<List<BaseArticle>, NetworkErrors>
        }
    }

    private fun computeMergedArticles(
        resultsBySource: Map<Source, List<BaseArticle>>,
    ): List<BaseArticle> {
        val flat = resultsBySource.flatMap { (source, list) -> list.map { source to it } }

        // Dedup by canonical URL — keep first encountered (source priority wins).
        val seen = mutableSetOf<String>()
        val ordered = flat.sortedBy { (source, _) -> SOURCE_PRIORITY[source] ?: Int.MAX_VALUE }
        val deduped = ordered.filter { (_, article) ->
            val key = canonicalize(article.url)
            if (key.isEmpty()) true else seen.add(key)
        }

        // Final sort: publishedAt desc, ties broken by source priority asc.
        return deduped.sortedWith(
            compareByDescending<Pair<Source, BaseArticle>> { (_, article) ->
                publishedAtKey(article)
            }.thenBy { (source, _) -> SOURCE_PRIORITY[source] ?: Int.MAX_VALUE }
        ).map { (_, article) -> article }
    }

    private fun publishedAtKey(article: BaseArticle): Long {
        val pa = (article as? Article)?.publishedAt ?: return 0L
        return pa.year.toLong() * 100_000_000L +
            pa.monthNumber.toLong() * 1_000_000L +
            pa.dayOfMonth.toLong() * 10_000L +
            pa.hour.toLong() * 100L +
            pa.minute.toLong()
    }

    private fun canonicalize(url: String): String {
        if (url.isBlank()) return ""
        return url.lowercase()
            .substringBefore("?")
            .substringBefore("#")
            .trimEnd('/')
    }

    private data class CacheKey(val source: Source, val topicValue: String?)

    companion object {
        private const val MAX_PARALLEL = 6
        private val CACHE_TTL = 5.minutes

        /** Source priority — earlier sources win URL-dedup ties and timestamp ties. */
        private val SOURCE_PRIORITY: Map<Source, Int> = listOf(
            Source.GITHUB, Source.HACKER_NEWS, Source.DEVTO, Source.PRODUCTHUNT,
            Source.REDDIT, Source.LOBSTERS, Source.HASH_NODE, Source.FREE_CODE_CAMP,
            Source.INDIE_HACKERS, Source.MEDIUM, Source.HACKER_NOON, Source.CONFERENCES,
        ).withIndex().associate { (i, s) -> s to i }
    }
}
