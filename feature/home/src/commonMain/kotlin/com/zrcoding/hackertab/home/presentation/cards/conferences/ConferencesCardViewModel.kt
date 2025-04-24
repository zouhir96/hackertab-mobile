package com.zrcoding.hackertab.home.presentation.cards.conferences

import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedTopicsUseCase
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterViewModel

class ConferencesCardViewModel(
    articleRepository: ArticleRepository,
    observeSelectedTopicsUseCase: ObserveSelectedTopicsUseCase,
    analyticsHelper: AnalyticsHelper
) : CardWithTopicFilterViewModel<Conference>(
    articleRepository,
    observeSelectedTopicsUseCase,
    analyticsHelper
) {

    override val source: Source
        get() = Source.CONFERENCES

    override val sourceTag: (Topic) -> String?
        get() = { topic -> topic.confsValues?.firstOrNull() }

    override val getArticles: suspend ArticleRepository.(String) -> Resource<List<Conference>, NetworkErrors>
        get() = { tag -> getConferences(tag) }
}