package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Source
import org.jetbrains.compose.resources.painterResource

val Source.icon
    get() = when (this) {
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

@Composable
fun Source.Icon(size: Dp = MaterialTheme.dimension.extraBig) {
    Box(
        modifier = Modifier
            .background(Color.White, CircleShape)
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(size - 2.dp)
                .clip(CircleShape),
            painter = painterResource(icon),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )
    }
}

val tags = mapOf(
    "python" to Color((0XFF3572A5)),
    "javascript" to Color((0XFFF1E05A)),
    "cplusplus" to Color((0XFFF34B7D)),
    "java" to Color((0XFFB07219)),
    "swift" to Color((0XFFFFAC45)),
    "go" to Color((0XFF00ADD8)),
    "kotlin" to Color((0XFFF18E33)),
    "ruby" to Color((0XFF701516)),
    "php" to Color((0XFF4F5E95)),
    "typescript" to Color((0XFF2B7489)),
    "objective-c" to Color((0XFF438EFF)),
    "django" to Color((0XFF0C4B33)),
    "node" to Color((0XFF5B9853)),
    "angular" to Color((0XFFDE0032)),
    "react" to Color((0XFF61DBFB)),
    "postgres" to Color((0XFF346792)),
    "mongodb" to Color((0XFF14AA52)),
    "vue" to Color((0XFF41B884)),
    "ruby-on-rails" to Color((0XFFCC0000)),
    "android" to Color((0XFF30D880)),
    "flutter" to Color((0XFF67B1F1)),
    "dart" to Color((0XFF045797)),
)