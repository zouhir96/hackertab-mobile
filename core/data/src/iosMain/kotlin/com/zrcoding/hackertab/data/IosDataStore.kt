package com.zrcoding.hackertab.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zrcoding.hackertab.data.datastore.dataStoreFileName
import com.zrcoding.hackertab.data.datastore.getDataStore
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
    fun create(): DataStore<Preferences> = getDataStore(
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
