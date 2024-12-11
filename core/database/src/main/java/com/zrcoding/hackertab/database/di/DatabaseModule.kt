package com.zrcoding.hackertab.database.di

import android.content.Context
import androidx.room.Room
import com.zrcoding.hackertab.database.HackertabDatabase
import org.koin.dsl.module

private const val DATABASE_NAME = "hackertab-db"

val databaseModule = module {
    single<HackertabDatabase> {
        val context: Context = get()
        Room.databaseBuilder(
            context,
            HackertabDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}