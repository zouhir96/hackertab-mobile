package com.zrcoding.hackertab.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.zrcoding.hackertab.database.daos.BookmarkedArticleDao
import com.zrcoding.hackertab.database.entities.BookmarkedArticleEntity

@Database(
    entities = [BookmarkedArticleEntity::class],
    exportSchema = true,
    version = 2,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun bookmarkedArticleDao(): BookmarkedArticleDao
}

interface DB {
    fun clearAllTables() {}
}

val Migration_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            "ALTER TABLE bookmarked_articles ADD COLUMN read INTEGER NOT NULL DEFAULT 0"
        )
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}