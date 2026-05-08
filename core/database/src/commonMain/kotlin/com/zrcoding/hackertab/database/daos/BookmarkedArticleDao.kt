package com.zrcoding.hackertab.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zrcoding.hackertab.database.entities.BookmarkedArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkedArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmarkedArticleEntity: BookmarkedArticleEntity)

    @Query("SELECT * FROM bookmarked_articles ORDER BY saved_at DESC")
    fun observeAll(): Flow<List<BookmarkedArticleEntity>>

    @Query("SELECT * FROM bookmarked_articles WHERE id = :id")
    suspend fun getById(id: String): BookmarkedArticleEntity?

    @Query("DELETE FROM bookmarked_articles WHERE id = :id")
    suspend fun deleteById(id: String)

    /** Mark a bookmark as read (sets read = 1). Added in schema v2. */
    @Query("UPDATE bookmarked_articles SET read = 1 WHERE id = :id")
    suspend fun markRead(id: String)
}