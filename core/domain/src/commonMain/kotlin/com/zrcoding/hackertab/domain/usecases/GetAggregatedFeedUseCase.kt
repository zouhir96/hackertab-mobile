package com.zrcoding.hackertab.domain.usecases

import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.AggregatedArticleRepository

/**
 * Wraps [AggregatedArticleRepository.getAggregatedFeed] with standard use-case
 * protocol. Invoked from [HomeViewModel] when `activeSourceId == "all"`.
 */
class GetAggregatedFeedUseCase(
    private val aggregatedArticleRepository: AggregatedArticleRepository,
) {
    suspend operator fun invoke(
        sources: List<Source>,
        topic: Topic?,
    ): List<BaseArticle> {
        return aggregatedArticleRepository.getAggregatedFeed(
            sources = sources,
            topic = topic,
        )
    }
}
