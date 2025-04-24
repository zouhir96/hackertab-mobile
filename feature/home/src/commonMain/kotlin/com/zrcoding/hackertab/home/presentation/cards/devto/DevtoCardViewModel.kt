package com.zrcoding.hackertab.home.presentation.cards.devto

import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.domain.models.Devto
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedTopicsUseCase
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterViewModel

class DevtoCardViewModel(
    articleRepository: ArticleRepository,
    observeSelectedTopicsUseCase: ObserveSelectedTopicsUseCase,
    analyticsHelper: AnalyticsHelper
) : CardWithTopicFilterViewModel<Devto>(
    articleRepository,
    observeSelectedTopicsUseCase,
    analyticsHelper
) {

    override val source: Source
        get() = Source.DEVTO

    override val sourceTag: (Topic) -> String?
        get() = { topic -> topic.devtoValues.firstOrNull() }

    override val getArticles: suspend ArticleRepository.(String) -> Resource<List<Devto>, NetworkErrors>
        get() = { tag -> getDevtoArticles(tag) }
}