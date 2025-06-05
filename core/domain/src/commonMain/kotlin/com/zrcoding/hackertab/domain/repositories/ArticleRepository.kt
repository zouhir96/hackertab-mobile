package com.zrcoding.hackertab.domain.repositories

import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.Devto
import com.zrcoding.hackertab.domain.models.FreeCodeCamp
import com.zrcoding.hackertab.domain.models.GithubRepo
import com.zrcoding.hackertab.domain.models.HackerNews
import com.zrcoding.hackertab.domain.models.Hashnode
import com.zrcoding.hackertab.domain.models.IndieHackers
import com.zrcoding.hackertab.domain.models.Lobster
import com.zrcoding.hackertab.domain.models.Medium
import com.zrcoding.hackertab.domain.models.NetworkErrors
import com.zrcoding.hackertab.domain.models.ProductHunt
import com.zrcoding.hackertab.domain.models.Reddit
import com.zrcoding.hackertab.domain.models.Resource

interface ArticleRepository {
    suspend fun getHackerNewsArticles(): Resource<List<HackerNews>, NetworkErrors>

    suspend fun getRedditArticles(tag: String): Resource<List<Reddit>, NetworkErrors>

    suspend fun getFreeCodeCampArticles(tag: String): Resource<List<FreeCodeCamp>, NetworkErrors>

    suspend fun getGithubRepositories(tag: String): Resource<List<GithubRepo>, NetworkErrors>

    suspend fun getConferences(tag: String): Resource<List<Conference>, NetworkErrors>

    suspend fun getDevtoArticles(tag: String): Resource<List<Devto>, NetworkErrors>

    suspend fun getHashnodeArticles(tag: String): Resource<List<Hashnode>, NetworkErrors>

    suspend fun getProductHuntProducts(): Resource<List<ProductHunt>, NetworkErrors>

    suspend fun getIndieHackersArticles(): Resource<List<IndieHackers>, NetworkErrors>

    suspend fun getLobstersArticles(): Resource<List<Lobster>, NetworkErrors>

    suspend fun getMediumArticles(tag: String): Resource<List<Medium>, NetworkErrors>
}