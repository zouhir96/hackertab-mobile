package com.zrcoding.hackertab.shared

import androidx.compose.ui.window.ComposeUIViewController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun MainViewController() = ComposeUIViewController {
    HackertabKmpApp()
}

fun initializeFirebase() {
    Firebase.initialize()
    Napier.base(DebugAntilog())
}