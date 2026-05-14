package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.SourceLoadState
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.coroutines.flow.Flow

/**
 * Repository that fans out to all enabled sources and merges results into a
 * single sorted list. Backs the "All" pseudo-source in the Today feed.
 *
 * Wave 5L (aggregator polish): emits a [Flow] of incremental
 * [AggregatedFeedResult] snapshots as each source completes, exposes
 * per-source [SourceLoadState] for partial-reveal UI, and de-dupes by
 * canonical URL.
 */
interface AggregatedArticleRepository {

    /**
     * Observe the aggregated feed for [sources] and [topic]. The flow emits
     * once on subscription with all sources in the Loading state, then once
     * per source completion. The final emission has every source either
     * Loaded or Failed.
     *
     * @param sources Enabled sources to fan out to.
     * @param topic   Optional topic filter; sources that ignore filters
     *                (HN, Lobsters, IndieHackers, ProductHunt) silently drop it.
     * @param refresh When `true`, bypass the 5-minute per-source response cache.
     */
    fun observeAggregatedFeed(
        sources: List<Source>,
        topic: Topic?,
        refresh: Boolean = false,
    ): Flow<AggregatedFeedResult>
}

/**
 * One snapshot of the aggregated feed during fan-out.
 *
 * @property articles            Merged, deduped, sorted articles known so far.
 * @property perSourceState      Live load state for each requested source.
 * @property isPartialReveal     `true` while fewer than 50% of sources have
 *                               completed; the UI shows a skeleton on top.
 */
data class AggregatedFeedResult(
    val articles: List<BaseArticle>,
    val perSourceState: Map<Source, SourceLoadState>,
    val isPartialReveal: Boolean,
)
