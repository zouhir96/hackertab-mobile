package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun bookmarkArticle(article: BaseArticle, source: String)
    
    suspend fun removeBookmark(articleId: String)
    
    suspend fun isBookmarked(articleId: String): Boolean
    
    fun observeAllBookmarks(): Flow<List<BookmarkedArticle>>
    
    fun observeBookmarkedIds(): Flow<Set<String>>
}

