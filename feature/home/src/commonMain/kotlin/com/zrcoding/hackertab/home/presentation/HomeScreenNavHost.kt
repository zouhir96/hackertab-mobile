package com.zrcoding.hackertab.home.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.conferences.ConferencesCard
import com.zrcoding.hackertab.home.presentation.cards.devto.DevtoCard
import com.zrcoding.hackertab.home.presentation.cards.freecodecamp.FreeCodeCampCard
import com.zrcoding.hackertab.home.presentation.cards.github.GithubCard
import com.zrcoding.hackertab.home.presentation.cards.hackernews.HackerNewsCard
import com.zrcoding.hackertab.home.presentation.cards.hashnode.HashnodeCard
import com.zrcoding.hackertab.home.presentation.cards.indiehackers.IndieHackersCard
import com.zrcoding.hackertab.home.presentation.cards.lobsters.LobstersCard
import com.zrcoding.hackertab.home.presentation.cards.mediun.MediumCard
import com.zrcoding.hackertab.home.presentation.cards.producthunt.ProductHuntCard
import com.zrcoding.hackertab.home.presentation.cards.reddit.RedditCard

@Composable
fun HomeScreenNavHost(
    navController: NavHostController,
    startDestination: String,
    enabledSourcesIds: List<String>
) {
    if (enabledSourcesIds.isNotEmpty()) {
        NavHost(navController = navController, startDestination = startDestination) {
            enabledSourcesIds.forEach { sourceId ->
                when (sourceId) {
                    Source.GITHUB.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.HACKER_NEWS.id -> composable(route = sourceId) {
                        HackerNewsCard()
                    }

                    Source.CONFERENCES.id -> composable(route = sourceId) {
                        ConferencesCard()
                    }

                    Source.DEVTO.id -> composable(route = sourceId) {
                        DevtoCard()
                    }

                    Source.PRODUCTHUNT.id -> composable(route = sourceId) {
                        ProductHuntCard()
                    }

                    Source.REDDIT.id -> composable(route = sourceId) {
                        RedditCard()
                    }

                    Source.LOBSTERS.id -> composable(route = sourceId) {
                        LobstersCard()
                    }

                    Source.HASH_NODE.id -> composable(route = sourceId) {
                        HashnodeCard()
                    }

                    Source.FREE_CODE_CAMP.id -> composable(route = sourceId) {
                        FreeCodeCampCard()
                    }

                    Source.INDIE_HACKERS.id -> composable(route = sourceId) {
                        IndieHackersCard()
                    }

                    Source.MEDIUM.id -> composable(route = sourceId) {
                        MediumCard()
                    }
                }
            }
        }
    }
}