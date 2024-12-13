package com.zrcoding.hackertab.domain.models

sealed interface Error

enum class NetworkErrors: Error {
    NO_INTERNET,
    REQUEST_TIMEOUT,
    UNKNOWN
}