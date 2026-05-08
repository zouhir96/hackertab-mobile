package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic

/**
 * Repository that fans out to all enabled sources and merges results into a
 * single sorted list. This is the backing store for the "All" pseudo-source
 * in the Today feed.
 *
 * Wave 5L (aggregator polish) will add: de-dup by canonical URL, per-source
 * loading state, backoff, partial-load UI (skeleton until ≥50% sources respond).
 * For now the impl is a simple parallel fan-out with no dedup.
 */
interface AggregatedArticleRepository {

    /**
     * Fetch articles from all [sources] in parallel and merge into a flat list
     * sorted by `publishedAt` descending (most recent first).
     *
     * The optional [topic] is forwarded to sources that support filter queries.
     * Sources that don't support filters (HackerNews, Lobsters, IndieHackers,
     * ProductHunt) ignore [topic].
     *
     * @param sources The list of enabled sources to fan out to.
     * @param topic   Optional topic filter. If null, defaults to the source's
     *                "global" / trending endpoint.
     * @return A list of [BaseArticle] merged from all sources.
     */
    suspend fun getAggregatedFeed(
        sources: List<Source>,
        topic: Topic?,
    ): List<BaseArticle>
}
