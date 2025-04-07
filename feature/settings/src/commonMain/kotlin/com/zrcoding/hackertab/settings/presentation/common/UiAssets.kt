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
import com.zrcoding.hackertab.domain.models.Source

val Source.icon
    get() = when(this) {
        Source.GITHUB -> Res.drawable.ic_github
        Source.HACKER_NEWS -> Res.drawable.ic_hackernews
        Source.CONFERENCES -> Res.drawable.ic_conferences
        Source.DEVTO -> Res.drawable.ic_devto
        Source.PRODUCTHUNT -> Res.drawable.ic_product_hunt
        Source.REDDIT -> Res.drawable.ic_reddit
        Source.LOBSTERS -> Res.drawable.ic_lobsters
        Source.HASH_NODE -> Res.drawable.ic_hashnode
        Source.FREE_CODE_CAMP -> Res.drawable.ic_freecodecamp
        Source.INDIE_HACKERS -> Res.drawable.ic_indie_hackers
        Source.MEDIUM -> Res.drawable.ic_medium
    }