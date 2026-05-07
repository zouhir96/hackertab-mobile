package com.zrcoding.hackertab.home.presentation.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.popoverPresentationController

class IOSShareManager : ShareManager {

    @OptIn(ExperimentalForeignApi::class)
    override fun share(data: ShareData) {
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        val activityViewController = UIActivityViewController(
            activityItems = listOf(generateShareText(data)),
            applicationActivities = null
        )

        // Configure for iPad - required to prevent crash
        activityViewController.popoverPresentationController?.apply {
            sourceView = rootViewController?.view
            sourceRect = rootViewController?.view?.bounds ?: platform.CoreGraphics.CGRectZero.readValue()
            permittedArrowDirections = 0u // No arrow
        }

        rootViewController?.presentViewController(
            viewControllerToPresent = activityViewController,
            animated = true,
            completion = null
        )
    }
}

