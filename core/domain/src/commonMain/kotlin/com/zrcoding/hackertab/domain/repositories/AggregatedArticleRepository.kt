package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.SourceLoadState
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.coroutines.flow.Flow

interface AggregatedArticleRepository {

    fun observeAggregatedFeed(
        sources: List<Source>,
        topic: Topic?,
        refresh: Boolean = false,
    ): Flow<AggregatedFeedResult>
}

data class AggregatedFeedResult(
    val articles: List<BaseArticle>,
    val perSourceState: Map<Source, SourceLoadState>,
    val isPartialReveal: Boolean,
)
