package com.zrcoding.hackertab.home.presentation.cards.mediun

import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.domain.models.Medium
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedTopicsUseCase
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterViewModel

class MediumCardViewModel(
    articleRepository: ArticleRepository,
    observeSelectedTopicsUseCase: ObserveSelectedTopicsUseCase,
    analyticsHelper: AnalyticsHelper
) : CardWithTopicFilterViewModel<Medium>(
    articleRepository,
    observeSelectedTopicsUseCase,
    analyticsHelper
) {

    override val source: Source
        get() = Source.MEDIUM

    override val sourceTag: (Topic) -> String?
        get() = { topic -> topic.mediumValues.firstOrNull() }

    override val getArticles: suspend ArticleRepository.(String) -> Resource<List<Medium>, NetworkErrors>
        get() = { tag -> getMediumArticles(tag) }
}