package com.zrcoding.hackertab.home.presentation.cards.indiehackers

import com.zrcoding.hackertab.domain.models.IndieHackers
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.home.presentation.cards.CardWithoutTopicFilterViewModel

class IndieHackersCardViewModel(
    articleRepository: ArticleRepository,
) : CardWithoutTopicFilterViewModel<IndieHackers>(articleRepository) {

    override val getArticles: suspend ArticleRepository.() -> Resource<List<IndieHackers>, NetworkErrors>
        get() = { getIndieHackersArticles() }
}