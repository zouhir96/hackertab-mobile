package com.zrcoding.hackertab.shared

import androidx.compose.ui.window.ComposeUIViewController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import platform.UIKit.UIDevice
import platform.UIKit.UIUserInterfaceIdiomPad

fun MainViewController() = ComposeUIViewController {
    HackertabKmpApp(UIDevice.currentDevice().userInterfaceIdiom == UIUserInterfaceIdiomPad)
}

fun initializeFirebase() {
    Firebase.initialize()
    Napier.base(DebugAntilog())
}