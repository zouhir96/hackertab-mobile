package com.zrcoding.hackertab.settings.presentation.common

/**
 * `AppConfig` provides the application configuration details common to
 * both Android and iOS platforms in a Kotlin Multiplatform project.
 * We can do this using the BuildKonfig library for this, but yeah i want to it myself :).
 *
 * This interface is intended to be implemented by platform-specific
 * classes to provide platform-dependent configuration values such as
 * the application version.
 *
 * ### Usage:
 * - Implementations for Android and iOS platforms should provide the
 *   version name or any other configuration values required.
 *
 * - On Android, this can be fetched from the `BuildConfig`.
 * - On iOS, this is typically fetched from the `Info.plist` file.
 *
 * @property versionName The version name of the application as a `String`.
 * Platform-specific implementations should provide the actual value:
 * - For Android, the value can be injected via `BuildConfig`.
 * - For iOS, the value is typically sourced from the `Info.plist` file.
 *
 * ### Example:
 * Common usage might involve injecting this interface using a dependency
 * injection framework like Koin, where both Android and iOS provide their
 * respective implementations.
 */
interface AppConfig {
    val versionName: String
}