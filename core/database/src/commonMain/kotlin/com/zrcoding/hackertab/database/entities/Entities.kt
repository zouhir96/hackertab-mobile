package com.zrcoding.hackertab.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_articles")
data class BookmarkedArticleEntity(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    @ColumnInfo(name = "saved_at") val savedAt: Long,
    val source: String
)