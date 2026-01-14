package com.zrcoding.hackertab.home.presentation.utils

data class ShareData(
    val title: String,
    val url: String
)

interface ShareManager {
    fun share(data: ShareData)

    fun generateShareText(data: ShareData): String {
        return """
            Hello, check out this article: ${data.title}
            ${data.url}

            Shared via HackerTab 🚀, Get all your tech news in one place!
            Download: https://apps.apple.com/us/app/hackertab-unofficial/id6746347807
        """.trimIndent()
    }
}

