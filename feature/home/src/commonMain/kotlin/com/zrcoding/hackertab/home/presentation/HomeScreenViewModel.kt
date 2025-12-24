package com.zrcoding.hackertab.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.analytics.AnalyticsHelper
import com.zrcoding.hackertab.analytics.models.AnalyticsEvent
import com.zrcoding.hackertab.analytics.models.Param
import com.zrcoding.hackertab.domain.models.Article
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.ProductHunt
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
                        articles = if (sources.isEmpty() || topics.isEmpty()) {
                            persistentListOf()
                        } else state.articles,
                        isLoading = false
                    )
                }
                if (_viewState.value.articles.isEmpty()) {
                    loadArticles(bookmarkedIds)
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

    private fun loadArticles(bookmarkedIds: Set<String> = emptySet()) {
        val selectedSource = _viewState.value.selectedSource
        val selectedTopic = _viewState.value.selectedTopic
        if (selectedSource != null && selectedTopic != null) {
            viewModelScope.launch {
                _viewState.update { it.copy(isLoading = true) }
                when (val result = getArticles(selectedSource, selectedTopic)) {
                    is Resource.Success -> {
                        _viewState.update { state ->
                            state.copy(
                                articles = result.data.map {
                                    val bookmarked = it.id in bookmarkedIds
                                    when (it) {
                                        is GithubRepo -> it.copy(bookmarked = bookmarked)
                                        is Conference -> it.copy(bookmarked = bookmarked)
                                        is ProductHunt -> it.copy(bookmarked = bookmarked)
                                        is Article -> it.copy(bookmarked = bookmarked)
                                        else -> it
                                    }
                                }.toPersistentList(),
                                isLoading = false,
                                error = if (result.data.isEmpty()) {
                                    "No items found, try adjusting your filter or choosing a different tag."
                                } else null,
                                canRefresh = false
                            )
                        }
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
                }
            }
        }
    }

    private suspend fun getArticles(
        source: Source,
        topic: Topic
    ): Resource<List<BaseArticle>, NetworkErrors> {
        return when (source) {
            Source.GITHUB -> articleRepository.getGithubRepositories(topic.value)
            Source.CONFERENCES -> articleRepository.getConferences(topic.value)
            Source.PRODUCTHUNT -> articleRepository.getProductHuntProducts()
            else -> articleRepository.getSourceArticles(source, topic.value)
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
                        value = topic.value
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
            } else {
                val source = _viewState.value.selectedSource?.name ?: return@launch
                bookmarkRepository.bookmarkArticle(article, source)
            }
        }
    }
}
