package com.zrcoding.hackertab.settings.presentation.common

import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_conferences
import com.zrcoding.hackertab.design.resources.ic_devto
import com.zrcoding.hackertab.design.resources.ic_freecodecamp
import com.zrcoding.hackertab.design.resources.ic_github
import com.zrcoding.hackertab.design.resources.ic_hackernews
import com.zrcoding.hackertab.design.resources.ic_hashnode
import com.zrcoding.hackertab.design.resources.ic_indie_hackers
import com.zrcoding.hackertab.design.resources.ic_lobsters
import com.zrcoding.hackertab.design.resources.ic_medium
import com.zrcoding.hackertab.design.resources.ic_product_hunt
import com.zrcoding.hackertab.design.resources.ic_reddit
import com.zrcoding.hackertab.settings.domain.models.Source
import com.zrcoding.hackertab.settings.domain.models.SourceName

val Source.icon
    get() = when(name) {
        SourceName.GITHUB -> Res.drawable.ic_github
        SourceName.HACKER_NEWS -> Res.drawable.ic_hackernews
        SourceName.CONFERENCES -> Res.drawable.ic_conferences
        SourceName.DEVTO -> Res.drawable.ic_devto
        SourceName.PRODUCTHUNT -> Res.drawable.ic_product_hunt
        SourceName.REDDIT -> Res.drawable.ic_reddit
        SourceName.LOBSTERS -> Res.drawable.ic_lobsters
        SourceName.HASH_NODE -> Res.drawable.ic_hashnode
        SourceName.FREE_CODE_CAMP -> Res.drawable.ic_freecodecamp
        SourceName.INDIE_HACKERS -> Res.drawable.ic_indie_hackers
        SourceName.MEDIUM -> Res.drawable.ic_medium
    }