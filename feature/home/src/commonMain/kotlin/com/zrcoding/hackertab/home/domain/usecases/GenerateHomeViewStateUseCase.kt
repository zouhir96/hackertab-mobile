package com.zrcoding.hackertab.home.domain.usecases

import com.zrcoding.hackertab.domain.models.BaseModel
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.home.presentation.CardViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest

class GenerateHomeViewStateUseCase(
    private val settingRepository: SettingRepository,
    private val articleRepository: ArticleRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<CardViewState>> {
        return combine(
            flow = settingRepository.observeSelectedTopics(),
            flow2 = settingRepository.observeSavedSources(),
            transform = ::Pair
        ).mapLatest { pair ->
            val topics = pair.first.ifEmpty { listOf(Topic.global) }
            pair.second.map { source ->
                when (source) {
                    Source.GITHUB -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            getTags = { githubValues.orEmpty() },
                            getArticles = { articleRepository.getGithubRepositories(it) },
                        )
                    )

                    Source.HACKER_NEWS -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            supportTags = false,
                            getTags = { emptyList() },
                            getArticles = { articleRepository.getHackerNewsArticles() },
                        )
                    )

                    Source.REDDIT -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            getTags = { redditValues },
                            getArticles = { articleRepository.getRedditArticles(it) },
                        )
                    )

                    Source.FREE_CODE_CAMP -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            getTags = { freecodecampValues },
                            getArticles = { articleRepository.getFreeCodeCampArticles(it) },
                        )
                    )

                    Source.CONFERENCES -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            getTags = { confsValues.orEmpty() },
                            getArticles = { articleRepository.getConferences(it) },
                        )
                    )

                    Source.DEVTO -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            getTags = { devtoValues },
                            getArticles = { articleRepository.getDevtoArticles(it) },
                        )
                    )

                    Source.HASH_NODE -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            getTags = { hashnodeValues },
                            getArticles = { articleRepository.getHashnodeArticles(it) },
                        )
                    )

                    Source.PRODUCTHUNT -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            supportTags = false,
                            getTags = { emptyList() },
                            getArticles = { articleRepository.getProductHuntProducts() },
                        )
                    )

                    Source.INDIE_HACKERS -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            supportTags = false,
                            getTags = { emptyList() },
                            getArticles = { articleRepository.getIndieHackersArticles() },
                        )
                    )

                    Source.LOBSTERS -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            supportTags = false,
                            getTags = { emptyList() },
                            getArticles = { articleRepository.getLobstersArticles() },
                        )
                    )

                    Source.MEDIUM -> CardViewState(
                        source = source,
                        state = createCardFlow(
                            source = source,
                            topics = topics,
                            getTags = { mediumValues },
                            getArticles = { articleRepository.getMediumArticles(it) },
                        )
                    )
                }
            }
        }
    }

    private inline fun <Model : BaseModel> createCardFlow(
        source: Source,
        topics: List<Topic>,
        supportTags: Boolean = true,
        crossinline getTags: Topic.() -> List<String>,
        crossinline getArticles: suspend (String) -> Resource<List<Model>, NetworkErrors>,
    ): Flow<CardViewState.State> = flow {
        emit(CardViewState.State.Loading)
        if (supportTags.not()) {
            when (val result = getArticles("")) {
                is Resource.Success -> emit(CardViewState.State.Success(result.data))
                is Resource.Failure -> emit(result.error.toStateError(source.label))
            }
            return@flow
        }
        val articles = mutableListOf<Model>()
        var noInternet = false
        var failedToLoad = false
        topics.forEach { topic ->
            topic.getTags().forEach {
                when (val result = getArticles(it)) {
                    is Resource.Success -> articles.addAll(result.data)
                    is Resource.Failure -> when(result.error){
                        NetworkErrors.NO_INTERNET,
                        NetworkErrors.REQUEST_TIMEOUT -> noInternet = true
                        NetworkErrors.UNKNOWN -> failedToLoad = true
                    }
                }
            }
        }
        if (articles.isEmpty() && failedToLoad) {
            emit(CardViewState.State.Error("Failed to load ${source.label} articles"))
        } else if (articles.isEmpty() && noInternet) {
            emit(CardViewState.State.VerifyConnectionAndRefresh)
        } else emit(CardViewState.State.Success(articles.distinctBy { it.id }))
    }

    private fun NetworkErrors.toStateError(sourceName: String): CardViewState.State = when (this) {
        NetworkErrors.NO_INTERNET,
        NetworkErrors.REQUEST_TIMEOUT -> CardViewState.State.VerifyConnectionAndRefresh

        NetworkErrors.UNKNOWN -> CardViewState.State.Error("Failed to load $sourceName articles")
    }
}