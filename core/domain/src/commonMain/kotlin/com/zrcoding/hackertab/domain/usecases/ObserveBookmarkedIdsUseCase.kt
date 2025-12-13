package com.zrcoding.hackertab.domain.usecases

import com.zrcoding.hackertab.domain.repositories.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class ObserveBookmarkedIdsUseCase(
    private val bookmarkRepository: BookmarkRepository
) {
    operator fun invoke(): Flow<Set<String>> {
        return bookmarkRepository.observeBookmarkedIds()
    }
}

