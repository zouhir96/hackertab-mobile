package com.zrcoding.hackertab.home.presentation.utils

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

class IOSShareManager : ShareManager {

    override fun share(data: ShareData) {
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        val activityViewController = UIActivityViewController(
            activityItems = listOf(generateShareText(data)),
            applicationActivities = null
        )

        rootViewController?.presentViewController(
            viewControllerToPresent = activityViewController,
            animated = true,
            completion = null
        )
    }
}

