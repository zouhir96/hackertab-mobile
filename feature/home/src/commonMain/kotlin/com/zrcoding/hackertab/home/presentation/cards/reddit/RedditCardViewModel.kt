package com.zrcoding.hackertab.home.presentation.cards.reddit

import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Reddit
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterViewModel

class RedditCardViewModel(
    articleRepository: ArticleRepository,
    settingRepository: SettingRepository
) : CardWithTopicFilterViewModel<Reddit>(articleRepository, settingRepository) {

    override val source: Source
        get() = Source.REDDIT

    override val sourceTag: (Topic) -> String?
        get() = { topic -> topic.redditValues.firstOrNull() }

    override val getArticles: suspend ArticleRepository.(String) -> Resource<List<Reddit>, NetworkErrors>
        get() = { tag -> getRedditArticles(tag) }
}