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
import com.zrcoding.hackertab.domain.models.SourceLoadState
import com.zrcoding.hackertab.domain.models.Topic
import com.zrcoding.hackertab.domain.repositories.AggregatedFeedResult
import com.zrcoding.hackertab.domain.repositories.ArticleRepository
import com.zrcoding.hackertab.domain.repositories.BookmarkRepository
import com.zrcoding.hackertab.domain.repositories.SettingRepository
import com.zrcoding.hackertab.domain.usecases.GetAggregatedFeedUseCase
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedSourcesUseCase
import com.zrcoding.hackertab.domain.usecases.ObserveSelectedTopicsUseCase
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class HomeViewModel(
    private val observeSelectedSourcesUseCase: ObserveSelectedSourcesUseCase,
    private val observeSelectedTopicsUseCase: ObserveSelectedTopicsUseCase,
    private val bookmarkRepository: BookmarkRepository,
    private val articleRepository: ArticleRepository,
    private val settingRepository: SettingRepository,
    private val getAggregatedFeedUseCase: GetAggregatedFeedUseCase,
    private val analyticsHelper: AnalyticsHelper,
) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeViewState())
    val viewState = _viewState.asStateFlow()

    private val refreshTrigger = MutableSharedFlow<Unit>()

    init {
        viewModelScope.launch {
            combine(
                observeSelectedSourcesUseCase(),
                observeSelectedTopicsUseCase(),
            ) { sources, topics -> sources to topics }
                .collectLatest { (sources, topics) ->
                    _viewState.update { state ->
                        val newSelectedTopic = when {
                            state.selectedTopic != null && state.selectedTopic in topics -> state.selectedTopic
                            topics.isNotEmpty() -> topics.first()
                            else -> null
                        }
                        val newActiveSourceId = when {
                            state.activeSourceId == "all" -> "all"
                            sources.any { it.id == state.activeSourceId } -> state.activeSourceId
                            else -> "all"
                        }
                        state.copy(
                            activeSourceId = newActiveSourceId,
                            enabledSources = sources.toPersistentList(),
                            canAddSource = sources.size < Source.entries.size,
                            enabledTopics = topics.toPersistentList(),
                            selectedTopic = newSelectedTopic,
                            canAddTopic = topics.size < settingRepository.getTopics().size,
                            articlesByDay = if (sources.isEmpty() || topics.isEmpty()) {
                                kotlinx.collections.immutable.persistentMapOf()
                            } else state.articlesByDay,
                            isLoading = false,
                        )
                    }
                }
        }

        viewModelScope.launch {
            val fetchFlow: Flow<List<BaseArticle>> = combine(
                refreshTrigger.onStart { emit(Unit) },
                _viewState.map { it.activeSourceId }.distinctUntilChanged(),
                _viewState.map { it.selectedTopic }.distinctUntilChanged(),
                _viewState.map { it.enabledSources }.distinctUntilChanged(),
            ) { _, sourceId, topic, sources ->
                FetchParams(sourceId, topic, sources)
            }.flatMapLatest { params ->
                if (params.sources.isEmpty() ||
                    (params.sourceId != "all" && params.topic == null)
                ) {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            perSourceLoadState = persistentMapOf(),
                            isPartialReveal = false,
                        )
                    }
                    return@flatMapLatest flow { emit(emptyList<BaseArticle>()) }
                }

                _viewState.update {
                    it.copy(
                        isLoading = true,
                        error = null,
                        perSourceLoadState = persistentMapOf(),
                        isPartialReveal = params.sourceId == "all",
                    )
                }

                if (params.sourceId == "all") {
                    observeAggregatedFlow(params.sources, params.topic)
                } else {
                    flow {
                        when (val result = fetchSingleSource(params.sourceId, params.topic)) {
                            is FetchResult.Success -> {
                                _viewState.update { state ->
                                    state.copy(
                                        isLoading = false,
                                        error = if (result.articles.isEmpty()) {
                                            "No items found, try adjusting your filter or choosing a different source."
                                        } else null,
                                        canRefresh = false,
                                        isPartialReveal = false,
                                    )
                                }
                                emit(result.articles)
                            }
                            is FetchResult.Failure -> {
                                _viewState.update {
                                    it.copy(
                                        articlesByDay = persistentMapOf(),
                                        isLoading = false,
                                        error = "Something went wrong, please verify your internet connection and try again.",
                                        canRefresh = true,
                                        isPartialReveal = false,
                                    )
                                }
                                emit(emptyList())
                            }
                        }
                    }
                }
            }

            combine(
                fetchFlow,
                bookmarkRepository.observeBookmarkedIds(),
            ) { articles, bookmarkedIds -> articles to bookmarkedIds }
                .collectLatest { (articles, bookmarkedIds) ->
                    val patched = articles.map { article ->
                        val bookmarked = bookmarkedIds.contains(article.id)
                        patchBookmarkFlag(article, bookmarked)
                    }
                    _viewState.update { state ->
                        state.copy(
                            articlesByDay = groupByDay(patched),
                        )
                    }
                }
        }
    }

    private data class FetchParams(
        val sourceId: String,
        val topic: Topic?,
        val sources: PersistentList<Source>,
    )

    private fun observeAggregatedFlow(
        sources: PersistentList<Source>,
        topic: Topic?,
    ): Flow<List<BaseArticle>> = getAggregatedFeedUseCase(
        sources = sources,
        topic = topic,
    ).map { result: AggregatedFeedResult ->
        val allDone = result.perSourceState.values.none { it is SourceLoadState.Loading }
        val anySucceeded = result.perSourceState.values.any { it is SourceLoadState.Loaded }
        val allFailed = result.perSourceState.values.all {
            it is SourceLoadState.Failed || it is SourceLoadState.Idle
        } && result.perSourceState.isNotEmpty()

        _viewState.update { state ->
            state.copy(
                isLoading = !allDone,
                isPartialReveal = result.isPartialReveal,
                perSourceLoadState = result.perSourceState.toPersistentMap(),
                error = when {
                    allDone && allFailed -> "Something went wrong, please verify your internet connection and try again."
                    allDone && result.articles.isEmpty() && anySucceeded -> "No items found, try adjusting your filter or choosing a different source."
                    else -> null
                },
                canRefresh = allDone && allFailed,
            )
        }

        if (allDone && result.articles.isNotEmpty()) {
            settingRepository.setLastVisitedAt(
                Clock.System.now().toEpochMilliseconds(),
            )
        }
        result.articles
    }

    fun onSourceSelected(sourceId: String) {
        if (_viewState.value.activeSourceId == sourceId) return
        _viewState.update { it.copy(activeSourceId = sourceId) }
        logSourceFilterChanged(sourceId)
    }

    fun onTopicSelected(topic: Topic) {
        if (_viewState.value.selectedTopic == topic) return
        _viewState.update { it.copy(selectedTopic = topic) }
        logTopicFilterChanged(topic)
    }

    fun refresh() {
        viewModelScope.launch { refreshTrigger.emit(Unit) }
    }

    fun onRefreshBtnClick() = refresh()

    fun toggleBookmark(article: BaseArticle) {
        viewModelScope.launch {
            val isBookmarked = bookmarkRepository.isBookmarked(article.id)
            if (isBookmarked) {
                bookmarkRepository.removeBookmark(article.id)
            } else {
                val source = when {
                    _viewState.value.isAllSourcesMode -> {
                        (article as? Article)?.source?.name ?: "unknown"
                    }
                    else -> _viewState.value.activeSource?.name ?: return@launch
                }
                bookmarkRepository.bookmarkArticle(article, source)
            }
        }
    }

    fun markRead(articleId: String) {
        viewModelScope.launch {
            val isBookmarked = bookmarkRepository.isBookmarked(articleId)
            if (!isBookmarked) {
                _viewState.update { state ->
                    state.copy(
                        seenArticleIds = (state.seenArticleIds + articleId).toPersistentList(),
                    )
                }
            }
            // TODO Wave 3H: if bookmarked, call bookmarkRepository.markRead(articleId)
        }
    }

    fun onLongPress(article: BaseArticle) {
        _viewState.update { it.copy(longPressedArticle = article) }
    }

    fun dismissLongPressSheet() {
        _viewState.update { it.copy(longPressedArticle = null) }
    }

    private sealed interface FetchResult {
        data class Success(val articles: List<BaseArticle>) : FetchResult
        data object Failure : FetchResult
    }

    private suspend fun fetchSingleSource(
        sourceId: String,
        topic: Topic?,
    ): FetchResult {
        val source = Source.fromId(sourceId) ?: return FetchResult.Failure
        return when (val result = getArticlesForSource(source, topic)) {
            is Resource.Success -> FetchResult.Success(result.data)
            is Resource.Failure -> FetchResult.Failure
        }
    }

    private suspend fun getArticlesForSource(
        source: Source,
        topic: Topic?,
    ): Resource<List<BaseArticle>, NetworkErrors> {
        val topicValue = topic?.value ?: Topic.global.value
        return when (source) {
            Source.GITHUB -> articleRepository.getGithubRepositories(topicValue)
            Source.CONFERENCES -> articleRepository.getConferences(topicValue)
            Source.PRODUCTHUNT -> articleRepository.getProductHuntProducts()
            else -> articleRepository.getSourceArticles(source, topicValue)
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun groupByDay(
        articles: List<BaseArticle>,
    ): kotlinx.collections.immutable.PersistentMap<DayBucket, PersistentList<BaseArticle>> {
        val tz = TimeZone.currentSystemDefault()
        val now = Clock.System.now().toLocalDateTime(tz)

        return DayBucket.entries.associateWith { bucket ->
            articles
                .filter { article -> bucket == bucketOf(article, now, tz) }
                .toPersistentList()
        }.filter { (_, v) -> v.isNotEmpty() }.toPersistentMap()
    }

    @OptIn(ExperimentalTime::class)
    private fun bucketOf(
        article: BaseArticle,
        now: LocalDateTime,
        tz: TimeZone,
    ): DayBucket {
        val publishedAt = (article as? Article)?.publishedAt ?: return DayBucket.OLDER

        val nowInstant = now.toInstant(tz)
        val articleInstant = publishedAt.toInstant(tz)
        val diffDays = (nowInstant - articleInstant).inWholeDays

        return when {
            diffDays < 1 -> DayBucket.TODAY
            diffDays < 2 -> DayBucket.YESTERDAY
            diffDays < 7 -> DayBucket.EARLIER_THIS_WEEK
            else -> DayBucket.OLDER
        }
    }

    private fun patchBookmarkFlag(article: BaseArticle, bookmarked: Boolean): BaseArticle {
        return when (article) {
            is GithubRepo -> article.copy(bookmarked = bookmarked)
            is Conference -> article.copy(bookmarked = bookmarked)
            is ProductHunt -> article.copy(bookmarked = bookmarked)
            is Article -> article.copy(bookmarked = bookmarked)
            else -> article
        }
    }

    private fun logSourceFilterChanged(sourceId: String) {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                name = AnalyticsEvent.Types.SOURCE_FILTER_CHANGED,
                properties = setOf(
                    Param(
                        key = AnalyticsEvent.ParamKeys.VALUE,
                        value = sourceId,
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
                        value = topic.value,
                    )
                )
            ),
        )
    }
}
