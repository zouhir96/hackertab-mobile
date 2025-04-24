package com.zrcoding.hackertab.analytics.impl

import androidx.core.bundle.Bundle
import com.zrcoding.hackertab.analytics.models.Param


/**
 * Converts a set of [Param] objects into a [Bundle].
 *
 * This extension function iterates over each [Param] in the set and adds it to the
 * [Bundle] as a key-value pair, where the key is the param's name and the value is
 * its corresponding value.
 *
 * @return A [Bundle] containing all the parameters as key-value pairs.
 */
fun Set<Param>.toMap() = mutableMapOf<String, String>().apply {
    this@toMap.forEach { param ->
        set(param.key, param.value)
    }
}