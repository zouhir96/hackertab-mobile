package com.zrcoding.hackertab.home.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.home.presentation.cards.github.GithubCard

@Composable
fun HomeScreenNavHost(
    navController: NavHostController,
    enabledSourcesIds: List<String>
) {
    if (enabledSourcesIds.isNotEmpty()) {
        NavHost(navController = navController, startDestination = Source.GITHUB.id) {
            enabledSourcesIds.forEach { sourceId ->
                when (sourceId) {
                    Source.GITHUB.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.HACKER_NEWS.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.CONFERENCES.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.DEVTO.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.PRODUCTHUNT.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.REDDIT.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.LOBSTERS.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.HASH_NODE.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.FREE_CODE_CAMP.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.INDIE_HACKERS.id -> composable(route = sourceId) {
                        GithubCard()
                    }

                    Source.MEDIUM.id -> composable(route = sourceId) {
                        GithubCard()
                    }
                }
            }
        }
    }
}