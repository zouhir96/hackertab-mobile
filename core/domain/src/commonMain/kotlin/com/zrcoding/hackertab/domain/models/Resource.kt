package com.zrcoding.hackertab.domain.models

typealias RootError = Error

sealed interface Resource<out T, out E : RootError> {
    data class Success<out T>(val data: T) : Resource<T, Nothing>
    data class Failure<out E : RootError>(val error: E) : Resource<Nothing, E>
}