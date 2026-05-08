package com.zrcoding.hackertab.design.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.components.SourceTag
import com.zrcoding.hackertab.design.theme.codeMedium
import com.zrcoding.hackertab.design.theme.codeSmall
import com.zrcoding.hackertab.domain.models.Conference
import com.zrcoding.hackertab.domain.models.Source
import kotlinx.datetime.LocalDate

private val ConferencesPurple = Color(0xFF6E56CF)

/** Conference card. Calendar-block date + location row + tags. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConferenceCard(
    conference: Conference,
    timeAgo: String,
    isBookmarked: Boolean,
    isFresh: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onMoreClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val (day, month) = conference.dayMonth()
    CardShell(
        isFresh = isFresh,
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = "Conference ${conference.title}. ${conference.locationLabel()}. " +
                "${conference.dateLabel()}. Tap to read."
        },
    ) {
        SourceTag(source = Source.CONFERENCES, timeAgo = timeAgo, isFresh = isFresh)
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.Top,
        ) {
            // Calendar block
            Column(
                modifier = Modifier
                    .width(62.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(12.dp),
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ConferencesPurple)
                        .padding(vertical = 3.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = month,
                        style = codeSmall,
                        color = Color.White,
                        fontWeight = FontWeight.W700,
                    )
                }
                Text(
                    text = day,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(top = 6.dp, bottom = 8.dp),
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = conference.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        imageVector = if (conference.online) Icons.Outlined.Public else Icons.Outlined.Place,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(13.dp),
                    )
                    Text(
                        text = conference.locationLabel(),
                        style = codeMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(Modifier.height(3.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(13.dp),
                    )
                    Text(
                        text = conference.dateLabel(),
                        style = codeMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
        if (conference.tags.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                conference.tags.take(6).forEach { tag ->
                    Text(
                        text = "#$tag",
                        style = codeSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 7.dp, vertical = 2.dp),
                    )
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(Modifier.weight(1f))
            CardActions(
                isBookmarked = isBookmarked,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onMoreClick,
            )
        }
    }
}

private fun Conference.dayMonth(): Pair<String, String> {
    val date: LocalDate = startDate ?: return "??" to "TBD"
    val month = date.month.name.take(3).uppercase()
    return date.day.toString() to month
}

private fun Conference.locationLabel(): String = when {
    online -> "Online"
    !city.isNullOrBlank() && !country.isNullOrBlank() -> "$city, $country"
    !country.isNullOrBlank() -> country!!
    !city.isNullOrBlank() -> city!!
    else -> "Location TBD"
}

private fun Conference.dateLabel(): String {
    val start = startDate ?: return "Date TBD"
    val end = endDate
    val month = start.month.name.lowercase()
        .replaceFirstChar { it.uppercase() }
        .take(3)
    return when {
        end == null || end == start -> "$month ${start.day}"
        end.month == start.month -> "$month ${start.day} – ${end.day}"
        else -> {
            val endMonth = end.month.name.lowercase()
                .replaceFirstChar { it.uppercase() }
                .take(3)
            "$month ${start.day} – $endMonth ${end.day}"
        }
    }
}
