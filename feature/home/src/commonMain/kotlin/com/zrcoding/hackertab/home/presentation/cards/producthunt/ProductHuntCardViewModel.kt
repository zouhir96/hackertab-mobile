package com.zrcoding.hackertab.home.presentation.cards.producthunt

import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.home.presentation.cards.CardWithoutTopicFilterViewModel

class ProductHuntCardViewModel(
    articleRepository: ArticleRepository,
) : CardWithoutTopicFilterViewModel<ProductHunt>(articleRepository) {

    override val getArticles: suspend ArticleRepository.() -> Resource<List<ProductHunt>, NetworkErrors>
        get() = { getProductHuntProducts() }
}