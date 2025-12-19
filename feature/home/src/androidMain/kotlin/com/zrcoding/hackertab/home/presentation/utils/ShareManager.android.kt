package com.zrcoding.hackertab.home.presentation.utils

import android.app.Activity
import android.content.Intent

class AndroidShareManager(private val context: Activity) : ShareManager {

    override fun share(data: ShareData) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, generateShareText(data))
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
}

