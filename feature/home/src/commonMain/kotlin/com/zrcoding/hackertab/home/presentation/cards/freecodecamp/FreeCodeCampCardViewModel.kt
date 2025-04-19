package com.zrcoding.hackertab.home.presentation.cards.freecodecamp

import com.zrcoding.hackertab.domain.models.FreeCodeCamp
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedTopicsUseCase
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterViewModel

class FreeCodeCampCardViewModel(
    articleRepository: ArticleRepository,
    observeSelectedTopicsUseCase: ObserveSelectedTopicsUseCase
) : CardWithTopicFilterViewModel<FreeCodeCamp>(articleRepository, observeSelectedTopicsUseCase) {

    override val source: Source
        get() = Source.FREE_CODE_CAMP

    override val sourceTag: (Topic) -> String?
        get() = { topic -> topic.freecodecampValues.firstOrNull() }

    override val getArticles: suspend ArticleRepository.(String) -> Resource<List<FreeCodeCamp>, NetworkErrors>
        get() = { tag -> getFreeCodeCampArticles(tag) }
}