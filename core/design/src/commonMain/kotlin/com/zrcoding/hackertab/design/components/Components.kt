package com.zrcoding.hackertab.design.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
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
import com.zrcoding.hackertab.design.resources.loading
import com.zrcoding.hackertab.design.theme.Blue
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
    val image: DrawableResource? = null,
    val selected: Boolean = false
)

fun Source.toChipData(selected: Boolean = false) = ChipData(
    id = id,
    name = label,
    analyticsTag = analyticsTag,
    image = icon,
    selected = selected
)

fun Topic.toChipData(selected: Boolean = false) = ChipData(
    id = id,
    name = label,
    analyticsTag = id,
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
        shape = RoundedCornerShape(MaterialTheme.dimension.big),
        backgroundColor = if (isSelected) Blue else MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick(chipData) }
                .padding(horizontal = MaterialTheme.dimension.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            chipData.image?.let {
                Image(
                    modifier = Modifier
                        .padding(end = MaterialTheme.dimension.medium)
                        .size(MaterialTheme.dimension.big),
                    painter = painterResource(it),
                    contentDescription = null,
                )
            }

            Text(
                modifier = Modifier.padding(vertical = MaterialTheme.dimension.medium),
                text = chipData.name,
                style = MaterialTheme.typography.body2,
                color = if (isSelected) {
                    Color.White
                } else MaterialTheme.colors.onBackground
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
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.medium),
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
    textStyle: TextStyle = MaterialTheme.typography.caption,
    textDecoration: TextDecoration = TextDecoration.None,
    icon: DrawableResource,
    tint: Color = Color.Gray
) {
    Row(
        modifier = modifier.padding(end = MaterialTheme.dimension.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconSize = if (icon == Res.drawable.ic_ellipse) {
            MaterialTheme.dimension.medium
        } else MaterialTheme.dimension.default
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
    textStyle: TextStyle = MaterialTheme.typography.body1
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
            textStyle = MaterialTheme.typography.body1
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
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onBackground
        )
        if (btnText != null) {
            OutlinedButton(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimension.big),
                onClick = onBtnClicked,
                shape = CircleShape,
                border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colors.onBackground
                ),
            ) {
                Text(
                    text = stringResource(btnText),
                    style = MaterialTheme.typography.button,
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

@Composable
fun Loading(title: String = stringResource(Res.string.loading)) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(MaterialTheme.dimension.bigger),
            color = MaterialTheme.colors.onPrimary
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimension.medium))
        Text(
            text = title,
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview()
@Composable
fun LoadingPreview() {
    HackertabTheme {
        Loading(stringResource(Res.string.loading))
    }
}

fun String.getTagColor(): Color {
    for ((tag, color) in tags) {
        if (this.equals(other = tag, ignoreCase = true)) return color
    }

    return Color.DarkGray
}
