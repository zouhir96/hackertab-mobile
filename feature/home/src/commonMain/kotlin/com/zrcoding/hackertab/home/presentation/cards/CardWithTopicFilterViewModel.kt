package com.zrcoding.hackertab.home.presentation.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.models.BaseModel
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class CardWithTopicFilterViewModel<out T : BaseModel>(
    private val articleRepository: ArticleRepository,
    private val settingRepository: SettingRepository
) : ViewModel() {

    abstract val source: Source
    abstract val sourceTag: (topic: Topic) -> String?
    abstract val getArticles: suspend ArticleRepository.(String) -> Resource<List<T>, NetworkErrors>


    private val _viewState = MutableStateFlow(
        CardWithTopicFilterViewState<T>(selectedTopic = Topic.global.toCardHeaderTopic())
    )
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            settingRepository.observeSelectedTopics().collectLatest { topics ->
                _viewState.update { state ->
                    state.copy(
                        topics = topics.map { it.toCardHeaderTopic() }
                            .toMutableList().apply {
                                add(0, Topic.global.toCardHeaderTopic())
                            }.toPersistentList()
                    )
                }
            }
        }
        viewModelScope.launch {
            loadSelectedTopicArticles()
        }
    }

    fun onUiEvent(event: CardWithTopicFilterUiEvents) {
        when (event) {
            CardWithTopicFilterUiEvents.Refresh -> loadSelectedTopicArticles()
            is CardWithTopicFilterUiEvents.TopicClick -> {
                _viewState.update { state -> state.copy(selectedTopic = event.topic) }
                loadSelectedTopicArticles()
            }
        }
    }

    private fun loadSelectedTopicArticles() {
        val tag = viewState.value.selectedTopic.sourceTag
        if (tag == null) {
            _viewState.update { state ->
                state.copy(
                    articles = persistentListOf(),
                    isLoading = false,
                    error = "${source.label} doesn't support ${viewState.value.selectedTopic.name}",
                    canRefresh = false
                )
            }
            return
        }
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            val articleResults = articleRepository.getArticles(tag)
            when (articleResults) {
                is Resource.Success -> _viewState.update { state ->
                    state.copy(
                        articles = articleResults.data.toPersistentList(),
                        isLoading = false,
                        error = null,
                    )
                }

                is Resource.Failure -> _viewState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = "Something went wrong, please verify your internet connection and try again",
                        canRefresh = true
                    )
                }
            }
        }
    }

    fun Topic.toCardHeaderTopic() = CardWithTopicFilterHeaderTopic(
        id = id,
        name = label,
        sourceTag = sourceTag(this)
    )
}
