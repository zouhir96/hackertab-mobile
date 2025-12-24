package com.zrcoding.hackertab.domain.models

data class GithubRepo(
    override val id: String,
    override val url: String,
    override val title: String,
    val description: String,
    val owner: String,
    val programmingLanguage: String,
    val stars: Int,
    val forks: Int
) : BaseArticle()