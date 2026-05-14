package com.zrcoding.hackertab.domain.usecases

import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.AggregatedArticleRepository
import com.zrcoding.hackertab.domain.repositories.AggregatedFeedResult
import kotlinx.coroutines.flow.Flow

/**
 * Wraps [AggregatedArticleRepository.observeAggregatedFeed] with standard
 * use-case protocol. Invoked from `HomeViewModel` when `activeSourceId == "all"`.
 */
class GetAggregatedFeedUseCase(
    private val aggregatedArticleRepository: AggregatedArticleRepository,
) {
    operator fun invoke(
        sources: List<Source>,
        topic: Topic?,
        refresh: Boolean = false,
    ): Flow<AggregatedFeedResult> {
        return aggregatedArticleRepository.observeAggregatedFeed(
            sources = sources,
            topic = topic,
            refresh = refresh,
        )
    }
}
