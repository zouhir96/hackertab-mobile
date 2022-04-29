package com.zrcoding.shared.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zrcoding.shared.data.local.entities.HackerNewsEntity

@Dao
interface HackerNewsDao: BaseDao<HackerNewsEntity> {
    @Query("SELECT * FROM hacker_news")
    suspend fun getAll(): List<HackerNewsEntity>

    @Query("SELECT COUNT(id) <= 0 FROM hacker_news")
    suspend fun isEmpty():Boolean

    @Query("DELETE FROM hacker_news")
    suspend fun clear()
}