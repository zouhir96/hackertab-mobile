package com.zrcoding.hackertab.analytics.impl

import com.zrcoding.hackertab.analytics.models.Param

fun Set<Param>.toMap() = mutableMapOf<String, String>().apply {
    this@toMap.forEach { param ->
        set(param.key, param.value)
    }
}