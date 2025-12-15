package com.zrcoding.hackertab.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.zrcoding.hackertab.database.daos.BookmarkedArticleDao
import com.zrcoding.hackertab.database.entities.BookmarkedArticleEntity

@Database(
    entities = [BookmarkedArticleEntity::class],
    exportSchema = true,
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun bookmarkedArticleDao(): BookmarkedArticleDao

}

interface DB {
    fun clearAllTables() {}
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}