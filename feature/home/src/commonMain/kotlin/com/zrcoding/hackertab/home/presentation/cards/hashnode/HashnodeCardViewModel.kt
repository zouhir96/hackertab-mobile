package com.zrcoding.hackertab.home.presentation.cards.hashnode

import com.zrcoding.hackertab.domain.models.Hashnode
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedTopicsUseCase
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterViewModel

class HashnodeCardViewModel(
    articleRepository: ArticleRepository,
    observeSelectedTopicsUseCase: ObserveSelectedTopicsUseCase
) : CardWithTopicFilterViewModel<Hashnode>(articleRepository, observeSelectedTopicsUseCase) {

    override val source: Source
        get() = Source.HASH_NODE

    override val sourceTag: (Topic) -> String?
        get() = { topic -> topic.hashnodeValues.firstOrNull() }

    override val getArticles: suspend ArticleRepository.(String) -> Resource<List<Hashnode>, NetworkErrors>
        get() = { tag -> getHashnodeArticles(tag) }
}