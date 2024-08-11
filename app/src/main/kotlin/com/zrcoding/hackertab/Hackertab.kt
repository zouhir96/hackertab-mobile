package com.zrcoding.hackertab

import android.app.Application
import android.content.Context
import com.zrcoding.hackertab.shared.di.initKoin
import org.koin.dsl.module

class Hackertab : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            appModule = module {
                single<Context> { this@Hackertab }
            },
        )
    }
}