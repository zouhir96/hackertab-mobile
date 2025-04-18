package com.zrcoding.hackertab.home.presentation.cards.lobsters

import com.zrcoding.hackertab.domain.models.Lobster
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.home.presentation.cards.CardWithoutTopicFilterViewModel

class LobstersViewModel(
    articleRepository: ArticleRepository,
) : CardWithoutTopicFilterViewModel<Lobster>(articleRepository) {

    override val getArticles: suspend ArticleRepository.() -> Resource<List<Lobster>, NetworkErrors>
        get() = { getLobstersArticles() }
}