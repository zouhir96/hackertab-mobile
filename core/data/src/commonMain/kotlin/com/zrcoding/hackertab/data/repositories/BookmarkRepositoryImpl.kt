package com.zrcoding.hackertab.data.repositories

import com.zrcoding.hackertab.data.mappers.toBookmarkedArticle
import com.zrcoding.hackertab.data.mappers.toBookmarkedArticleEntity
import com.zrcoding.hackertab.database.AppDatabase
import com.zrcoding.hackertab.domain.models.BaseArticle
import com.zrcoding.hackertab.domain.models.BookmarkedArticle
import com.zrcoding.hackertab.domain.repositories.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkRepositoryImpl(
    private val database: AppDatabase
) : BookmarkRepository {
    

    override suspend fun bookmarkArticle(article: BaseArticle, source: String) {
        val entity = article.toBookmarkedArticleEntity(source)
        database.bookmarkedArticleDao().insert(entity)
    }
    
    override suspend fun removeBookmark(articleId: String) {
        database.bookmarkedArticleDao().deleteById(articleId)
    }
    
    override suspend fun isBookmarked(articleId: String): Boolean {
        return database.bookmarkedArticleDao().getById(articleId) != null
    }
    
    override fun observeAllBookmarks(): Flow<List<BookmarkedArticle>> {
        return database.bookmarkedArticleDao().observeAll().map { entities ->
            entities.map { it.toBookmarkedArticle() }
        }
    }
    
    override fun observeBookmarkedIds(): Flow<Set<String>> {
        return database.bookmarkedArticleDao().observeAll().map { entities ->
            entities.map { it.id }.toSet()
        }
    }
}

