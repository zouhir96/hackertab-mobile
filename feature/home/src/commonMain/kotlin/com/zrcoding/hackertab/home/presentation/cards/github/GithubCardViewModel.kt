package com.zrcoding.hackertab.home.presentation.cards.github

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.Resource
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.home.presentation.cards.CardViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GithubCardViewModel(
    private val articleRepository: ArticleRepository,
    private val settingRepository: SettingRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(CardViewState<GithubRepo>())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            settingRepository.observeSavedTopicsIds().collectLatest { ids ->
                val articleResults = coroutineScope {
                    ids.map { id -> async { articleRepository.getGithubRepositories(id) } }
                        .awaitAll()
                }
                when {
                    articleResults.none { it is Resource.Success } -> {
                        _viewState.update {
                            CardViewState(
                                isLoading = false,
                                error = "Something went wrong, please verify your internet connection and try again"
                            )
                        }
                    }

                    else -> {
                        val articles =
                            articleResults.filterIsInstance<Resource.Success<List<GithubRepo>>>()
                                .flatMap { it.data }
                        _viewState.update {
                            CardViewState<GithubRepo>(
                                isLoading = false,
                                articles = articles
                            )
                        }
                    }
                }
            }
        }
    }
}