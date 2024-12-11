package com.zrcoding.hackertab.network

import com.zrcoding.hackertab.network.api.ArticlesNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

const val BASE_URL = "https://api.hackertab.dev/data/v2/"

val networkModule = module {
    factory<HttpClient> {
        HttpClient {
            defaultRequest {
                url(BASE_URL)
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging){
                logger = Logger.SIMPLE
                level = LogLevel.BODY
            }
        }
    }
    factory<ArticlesNetworkDataSource> {
        ArticlesNetworkDataSource(get())
    }
}