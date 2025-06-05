package com.zrcoding.hackertab

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.zrcoding.hackertab.shared.HackertabKmpApp
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        loadKoinModules(
            module {
                // We add Activity to DI because there are places
                // where we need to show AlertDialogs in androidMain like [AndroidAccountMasterScreenIntentsTrigger]
                single<Activity> { this@MainActivity }
            }
        )

        setContent {
            HackertabKmpApp()
        }
    }
}


