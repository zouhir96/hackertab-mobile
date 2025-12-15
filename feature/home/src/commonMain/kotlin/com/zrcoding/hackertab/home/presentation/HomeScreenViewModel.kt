package com.zrcoding.hackertab.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.Param
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.repositories.BookmarkRepository
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedSourcesUseCase
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedTopicsUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val observeSelectedSourcesUseCase: ObserveSelectedSourcesUseCase,
    private val observeSelectedTopicsUseCase: ObserveSelectedTopicsUseCase,
    private val bookmarkRepository: BookmarkRepository,
    private val articleRepository: ArticleRepository,
    private val settingRepository: SettingRepository,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeScreenViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                observeSelectedSourcesUseCase(),
                observeSelectedTopicsUseCase(),
                bookmarkRepository.observeBookmarkedIds()
            ) { sources, topics, bookmarkedIds ->
                Triple(sources, topics, bookmarkedIds)
            }.collectLatest { (sources, topics, bookmarkedIds) ->
                _viewState.update { state ->
                    val newSelectedSource = when {
                        // If current selection is still available, keep it
                        state.selectedSource != null && state.selectedSource in sources -> state.selectedSource
                        // Otherwise, select the first available source
                        sources.isNotEmpty() -> sources.first()
                        // No sources available
                        else -> null
                    }
                    val newSelectedTopic = when {
                        // If current selection is still available, keep it
                        state.selectedTopic != null && state.selectedTopic in topics -> state.selectedTopic
                        // Otherwise, select the first available topic
                        topics.isNotEmpty() -> topics.first()
                        // No topics available
                        else -> null
                    }

                    state.copy(
                        enabledSources = sources.toPersistentList(),
                        selectedSource = newSelectedSource,
                        canAddSource = sources.size < Source.entries.size,
                        enabledTopics = topics.toPersistentList(),
                        selectedTopic = newSelectedTopic,
                        canAddTopic = topics.size < settingRepository.getTopics().size,
                        bookmarkedIds = bookmarkedIds.toPersistentSet(),
                        articles = if (sources.isEmpty() || topics.isEmpty()) {
                            persistentListOf()
                        } else state.articles,
                        isLoading = false
                    )
                }
                if (_viewState.value.articles.isEmpty()) {
                    loadArticles()
                }
            }
        }
    }

    fun onSourceSelected(source: Source) {
        if (_viewState.value.selectedSource == source) return
        _viewState.update {
            it.copy(selectedSource = source)
        }
        loadArticles()
        logSourceFilterChanged(source)
    }

    fun onTopicSelected(topic: Topic) {
        if (_viewState.value.selectedTopic == topic) return
        _viewState.update {
            it.copy(selectedTopic = topic)
        }
        loadArticles()
        logTopicFilterChanged(topic)
    }

    fun onRefreshBtnClick() {
        loadArticles()
    }

    private fun loadArticles() {
        val selectedSource = _viewState.value.selectedSource
        val selectedTopic = _viewState.value.selectedTopic
        if (selectedSource != null && selectedTopic != null) {
            viewModelScope.launch {
                _viewState.update { it.copy(isLoading = true) }
                when (val result = getArticles(selectedSource, selectedTopic)) {
                    is Resource.Success -> _viewState.update {
                        it.copy(
                            articles = result.data.toPersistentList(),
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Failure -> {
                        _viewState.update {
                            it.copy(
                                articles = persistentListOf(),
                                isLoading = false,
                                error = "Something went wrong, please verify your internet connection and try again",
                                canRefresh = true
                            )
                        }
                    }

                    null -> {
                        _viewState.update {
                            it.copy(
                                articles = persistentListOf(),
                                isLoading = false,
                                error = "${selectedSource.label} doesn't support ${selectedTopic.label}, \n please select another source or topic",
                                canRefresh = false
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun getArticles(
        source: Source,
        topic: Topic
    ): Resource<List<BaseArticle>, NetworkErrors>? {
        return when (source) {
            Source.CONFERENCES -> {
                val tag = topic.confsValues?.firstOrNull() ?: return null
                articleRepository.getConferences(tag)
            }

            Source.DEVTO -> {
                val tag = topic.devtoValues.firstOrNull() ?: return null
                articleRepository.getDevtoArticles(tag)
            }

            Source.GITHUB -> {
                val tag = topic.githubValues?.firstOrNull() ?: return null
                articleRepository.getGithubRepositories(tag)
            }

            Source.MEDIUM -> {
                val tag = topic.mediumValues.firstOrNull() ?: return null
                articleRepository.getMediumArticles(tag)
            }

            Source.REDDIT -> {
                val tag = topic.redditValues.firstOrNull() ?: return null
                articleRepository.getRedditArticles(tag)
            }

            Source.HASH_NODE -> {
                val tag = topic.hashnodeValues.firstOrNull() ?: return null
                articleRepository.getHashnodeArticles(tag)
            }

            Source.FREE_CODE_CAMP -> {
                val tag = topic.freecodecampValues.firstOrNull() ?: return null
                articleRepository.getFreeCodeCampArticles(tag)
            }

            Source.HACKER_NEWS -> articleRepository.getHackerNewsArticles()

            Source.PRODUCTHUNT -> articleRepository.getProductHuntProducts()

            Source.LOBSTERS -> articleRepository.getLobstersArticles()

            Source.INDIE_HACKERS -> articleRepository.getIndieHackersArticles()
        }
    }

    private fun logSourceFilterChanged(source: Source) {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                name = AnalyticsEvent.Types.SOURCE_FILTER_CHANGED,
                properties = setOf(
                    Param(
                        key = AnalyticsEvent.ParamKeys.VALUE,
                        value = source.analyticsTag
                    )
                )
            ),
        )
    }

    private fun logTopicFilterChanged(topic: Topic) {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                name = AnalyticsEvent.Types.SOURCE_FILTER_CHANGED,
                properties = setOf(
                    Param(
                        key = AnalyticsEvent.ParamKeys.VALUE,
                        value = topic.id
                    )
                )
            ),
        )
    }

    fun toggleBookmark(article: BaseArticle) {
        viewModelScope.launch {
            val isBookmarked = bookmarkRepository.isBookmarked(article.id)
            if (isBookmarked) {
                bookmarkRepository.removeBookmark(article.id)
                _viewState.update {
                    it.copy(bookmarkedIds = (it.bookmarkedIds - article.id).toPersistentSet())
                }
            } else {
                val source = _viewState.value.selectedSource?.name ?: return@launch
                bookmarkRepository.bookmarkArticle(article, source)
            }
        }
    }
}
