package com.zrcoding.hackertab.database.di

import android.content.Context
import androidx.room.Room
import com.zrcoding.hackertab.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual val databaseModule = module {
    single<AppDatabase> {
        val appContext: Context = get()
        val dbFile = appContext.getDatabasePath(DATABASE_NAME)
        Room.databaseBuilder<AppDatabase>(appContext, dbFile.absolutePath)
            .fallbackToDestructiveMigration(true)
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}