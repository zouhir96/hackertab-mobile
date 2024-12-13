package com.zrcoding.hackertab.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zrcoding.hackertab.settings.data.datastore.dataStoreFileName
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

/**
 * iOS implementation of [DataStore].
 */
internal class IosDataStore {

    /**
     * Gets the [DataStore] instance.
     *
     * @return the [DataStore] instance
     */
    @OptIn(ExperimentalForeignApi::class)
    fun getDataStore(): DataStore<Preferences> = com.zrcoding.hackertab.settings.data.datastore.getDataStore(
        producePath = {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/$dataStoreFileName"
        },
    )
}
