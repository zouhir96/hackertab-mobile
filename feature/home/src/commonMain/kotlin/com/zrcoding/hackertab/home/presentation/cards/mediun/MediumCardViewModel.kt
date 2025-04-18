package com.zrcoding.hackertab.home.presentation.cards.mediun

import com.zrcoding.hackertab.domain.models.Medium
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterViewModel

class MediumCardViewModel(
    articleRepository: ArticleRepository,
    settingRepository: SettingRepository
) : CardWithTopicFilterViewModel<Medium>(articleRepository, settingRepository) {

    override val source: Source
        get() = Source.MEDIUM

    override val sourceTag: (Topic) -> String?
        get() = { topic -> topic.mediumValues.firstOrNull() }

    override val getArticles: suspend ArticleRepository.(String) -> Resource<List<Medium>, NetworkErrors>
        get() = { tag -> getMediumArticles(tag) }
}