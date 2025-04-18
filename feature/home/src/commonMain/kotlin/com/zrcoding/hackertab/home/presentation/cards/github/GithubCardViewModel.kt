package com.zrcoding.hackertab.home.presentation.cards.github

import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.home.presentation.cards.CardWithTopicFilterViewModel

class GithubCardViewModel(
    articleRepository: ArticleRepository,
    settingRepository: SettingRepository
) : CardWithTopicFilterViewModel<GithubRepo>(articleRepository, settingRepository) {

    override val source: Source
        get() = Source.GITHUB

    override val sourceTag: (Topic) -> String?
        get() = { topic -> topic.githubValues?.firstOrNull() }

    override val getArticles: suspend ArticleRepository.(String) -> Resource<List<GithubRepo>, NetworkErrors>
        get() = { tag -> getGithubRepositories(tag) }
}