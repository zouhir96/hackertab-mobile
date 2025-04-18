package com.zrcoding.hackertab.home.presentation.cards.hackernews

import com.zrcoding.hackertab.domain.models.HackerNews
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.home.presentation.cards.CardWithoutTopicFilterViewModel

class HackerNewsCardViewModel(
    articleRepository: ArticleRepository,
) : CardWithoutTopicFilterViewModel<HackerNews>(articleRepository) {

    override val getArticles: suspend ArticleRepository.() -> Resource<List<HackerNews>, NetworkErrors>
        get() = { getHackerNewsArticles() }
}