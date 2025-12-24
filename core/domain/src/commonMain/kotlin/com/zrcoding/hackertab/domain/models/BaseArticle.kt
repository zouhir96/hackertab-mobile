package com.zrcoding.hackertab.domain.models

abstract class BaseArticle {
    abstract val id: String
    abstract val title: String
    open val url: String = ""
    open val bookmarked: Boolean = false
}