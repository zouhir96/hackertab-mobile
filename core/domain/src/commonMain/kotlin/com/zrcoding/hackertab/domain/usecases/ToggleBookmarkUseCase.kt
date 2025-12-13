package com.zrcoding.hackertab.domain.usecases

import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.repositories.BookmarkRepository

class ToggleBookmarkUseCase(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(article: BaseArticle, source: String) {
        if (bookmarkRepository.isBookmarked(article.id)) {
            bookmarkRepository.removeBookmark(article.id)
        } else {
            bookmarkRepository.bookmarkArticle(article, source)
        }
    }
}

