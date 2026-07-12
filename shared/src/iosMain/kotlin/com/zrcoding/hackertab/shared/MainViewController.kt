package com.zrcoding.hackertab.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.zrcoding.hackertab.shared.app.HackertabApp
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun MainViewController() = ComposeUIViewController {
    HackertabApp()
}

fun initializeFirebase() {
    Firebase.initialize()
    Napier.base(DebugAntilog())
}