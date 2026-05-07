package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.common_retry
import com.zrcoding.hackertab.design.resources.ic_ellipse
import com.zrcoding.hackertab.design.resources.ic_github
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.dimension
import com.zrcoding.hackertab.domain.models.Source
import com.zrcoding.hackertab.domain.models.Topic
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class ChipData(
    val id: String,
    val name: String,
    val analyticsTag: String,
    val image: (@Composable () -> Unit)? = null,
    val selected: Boolean = false
)

fun Source.toChipData(selected: Boolean = false) = ChipData(
    id = id,
    name = label,
    analyticsTag = analyticsTag,
    image = { Icon(size = 24.dp) },
    selected = selected
)

fun Topic.toChipData(selected: Boolean = false) = ChipData(
    id = value,
    name = label,
    analyticsTag = value,
    image = null,
    selected = selected
)

object ChipStateHandler {

    fun handleMonoSelect(
        currentState: PersistentList<ChipData>,
        clickedChip: ChipData
    ): PersistentList<ChipData> {
        return currentState.map {
            if (it != clickedChip) {
                it.copy(selected = false)
            } else {
                it.copy(selected = !it.selected)
            }
        }.toPersistentList()
    }

    fun handleMultiSelect(
        currentState: PersistentList<ChipData>,
        clickedChip: ChipData
    ): PersistentList<ChipData> {
        val indexOfItem = currentState.indexOf(clickedChip)
        if (indexOfItem == -1) throw IllegalArgumentException("Item doesn't exist in the list")

        return currentState.toMutableList()
            .apply {
                val oldChip = get(indexOfItem)
                val newChip = oldChip.copy(selected = !oldChip.selected)
                set(indexOfItem, newChip)
            }
            .toPersistentList()
    }
}

@Composable
fun Chip(
    chipData: ChipData,
    isSelected: Boolean = false,
    onClick: (ChipData) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(MaterialTheme.dimension.space20),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick(chipData) }
                .padding(horizontal = MaterialTheme.dimension.space12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            chipData.image?.let {
                it.invoke()
                Spacer(modifier = Modifier.width(MaterialTheme.dimension.space4))
            }
            Text(
                modifier = Modifier.padding(vertical = MaterialTheme.dimension.space8),
                text = chipData.name,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) {
                    Color.White
                } else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipGroup(
    modifier: Modifier = Modifier,
    chips: List<ChipData>,
    onChipClicked: (ChipData) -> Unit,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space8),
    ) {
        chips.forEach { chip ->
            Chip(
                chipData = chip,
                isSelected = chip.selected,
                onClick = onChipClicked
            )
        }
    }
}

@Preview()
@Composable
fun ChipGroupPreview() {
    val chips = remember {
        Source.entries.map { it.toChipData() }.toPersistentList()
    }

    HackertabTheme {
        ChipGroup(
            chips = chips,
        ) { chip ->
            val index = chips.indexOf(chip)
            chips.set(index, chip.copy(selected = chip.selected.not()))
        }
    }
}

@Composable
fun TextWithStartIcon(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Gray,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    textDecoration: TextDecoration = TextDecoration.None,
    icon: DrawableResource,
    tint: Color = Color.Gray
) {
    Row(
        modifier = modifier.padding(end = MaterialTheme.dimension.space8),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.space4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconSize = if (icon == Res.drawable.ic_ellipse) {
            MaterialTheme.dimension.space8
        } else MaterialTheme.dimension.space16
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            tint = tint,
            modifier = Modifier.size(iconSize)
        )
        Text(
            text = text,
            color = textColor,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textDecoration = textDecoration
        )
    }
}

@Preview()
@Composable
private fun TextWithStartIconPreview() {
    HackertabTheme {
        TextWithStartIcon(
            text = "Some text",
            icon = Res.drawable.ic_github
        )
    }
}

@Composable
fun FullScreenViewWithCenterText(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = text,
            style = textStyle,
            textAlign = TextAlign.Center
        )

    }
}

@Preview()
@Composable
private fun FullScreenViewWithCenterTextPreview() {
    HackertabTheme {
        FullScreenViewWithCenterText(
            text = "github",
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ErrorMsgWithBtn(
    modifier: Modifier = Modifier,
    text: String,
    btnText: StringResource?,
    onBtnClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (btnText != null) {
            OutlinedButton(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.space20),
                onClick = onBtnClicked,
                shape = CircleShape,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
            ) {
                Text(
                    text = stringResource(btnText),
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview()
@Composable
private fun ErrorMsgWithBtnPreview() {
    HackertabTheme {
        ErrorMsgWithBtn(
            text = "Failed to load articles found for Github !!",
            btnText = Res.string.common_retry
        ) {}
    }
}

fun String.getTagColor(): Color {
    for ((tag, color) in tags) {
        if (this.equals(other = tag, ignoreCase = true)) return color
    }

    return Color.DarkGray
}
