package com.zrcoding.hackertab.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zrcoding.hackertab.settings.data.datastore.dataStoreFileName

/**
 * Android implementation of [DataStore].
 */
internal class AndroidDataStore(private val context: Context) {

    /**
     * Gets the [DataStore] instance. It uses the same path as the Android DataStore, allowing to
     * use the same file when the user is updating the app.
     *
     * @return the [DataStore] instance
     */
    fun getDataStore(): DataStore<Preferences> = com.zrcoding.hackertab.settings.data.datastore.getDataStore(
        producePath = { context.filesDir.resolve("datastore/$dataStoreFileName").absolutePath },
    )
}
