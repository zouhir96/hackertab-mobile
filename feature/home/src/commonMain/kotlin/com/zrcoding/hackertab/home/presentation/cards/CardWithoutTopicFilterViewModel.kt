package com.zrcoding.hackertab.home.presentation.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.models.BaseModel
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class CardWithoutTopicFilterViewModel<out T : BaseModel>(
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    abstract val getArticles: suspend ArticleRepository.() -> Resource<List<T>, NetworkErrors>

    private val _viewState = MutableStateFlow(CardWithoutTopicFilterViewState<T>())
    val viewState = _viewState.asStateFlow()

    init {
        loadArticles()
    }

    fun onRefresh() {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            val articleResults = articleRepository.getArticles()
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
                    )
                }
            }
        }
    }
}
