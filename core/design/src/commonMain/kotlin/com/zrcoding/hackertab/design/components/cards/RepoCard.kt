package com.zrcoding.hackertab.design.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.zrcoding.hackertab.design.components.getTagColor
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.ic_baseline_fork
import com.zrcoding.hackertab.design.resources.ic_baseline_star
import com.zrcoding.hackertab.design.resources.ic_time_24
import com.zrcoding.hackertab.design.theme.codeMedium
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.GithubRepo

@Composable
fun RepoCard(
    repo: GithubRepo,
    timeAgo: String,
    isBookmarked: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onMoreClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    CardShell(
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = "Repo ${repo.owner} slash ${repo.title}. ${repo.programmingLanguage}, " +
                "${repo.stars} stars, ${repo.forks} forks. $timeAgo. Tap to read."
        },
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = repo.owner,
                style = codeMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.W600,
            )
            Text(
                text = "/",
                style = codeMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space2),
            )
            Text(
                text = repo.title,
                style = codeMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.W700,
            )
        }
        if (repo.description.isNotBlank()) {
            Spacer(Modifier.height(MaterialTheme.dimension.space6))
            Text(
                text = repo.description.trim(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                maxLines = 3,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space12),
        ) {
            MetaIconText(
                icon = Res.drawable.ic_time_24,
                text = timeAgo
            )
            MetaDotText(
                color = repo.programmingLanguage.getTagColor(),
                text = repo.programmingLanguage
            )
            MetaIconText(
                icon = Res.drawable.ic_baseline_star,
                text = formatCount(repo.stars)
            )
            MetaIconText(
                icon = Res.drawable.ic_baseline_fork,
                text = formatCount(repo.forks)
            )
            Spacer(Modifier.weight(1f))
            CardActions(
                isBookmarked = isBookmarked,
                onBookmarkClick = onBookmarkClick,
                onMoreClick = onMoreClick,
            )
        }
    }
}

private fun formatCount(n: Int): String = when {
    n >= 1000 -> "${n / 1000}.${(n % 1000) / 100}k"
    else -> n.toString()
}
