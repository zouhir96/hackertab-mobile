package com.zrcoding.hackertab.shared

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIDevice
import platform.UIKit.UIUserInterfaceIdiomPad

fun MainViewController() = ComposeUIViewController {
    HackertabKmpApp(UIDevice.currentDevice().userInterfaceIdiom == UIUserInterfaceIdiomPad)
}