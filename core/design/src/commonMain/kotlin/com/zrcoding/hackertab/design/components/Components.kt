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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
import com.zrcoding.hackertab.design.resources.failed_to_load_source
import com.zrcoding.hackertab.design.resources.ic_ellipse
import com.zrcoding.hackertab.design.resources.ic_freecodecamp
import com.zrcoding.hackertab.design.resources.ic_github
import com.zrcoding.hackertab.design.resources.ic_reddit
import com.zrcoding.hackertab.design.resources.loading
import com.zrcoding.hackertab.design.theme.HackertabTheme
import com.zrcoding.hackertab.design.theme.TextLink
import com.zrcoding.hackertab.design.theme.dimension
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class ChipData(
    val id: String,
    val name: String,
    val image: DrawableResource? = null,
    val selected: Boolean = false
)

@Composable
fun Chip(
    chipData: ChipData,
    isSelected: Boolean = false,
    onClick: (ChipData) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(MaterialTheme.dimension.big),
        backgroundColor = if (isSelected) TextLink else MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick(chipData) }
                .padding(horizontal = MaterialTheme.dimension.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            chipData.image?.let {
                Icon(
                    modifier = Modifier
                        .padding(end = MaterialTheme.dimension.medium)
                        .size(MaterialTheme.dimension.big),
                    painter = painterResource(it),
                    contentDescription = null,
                    tint = if (isSelected) {
                        MaterialTheme.colors.surface
                    } else MaterialTheme.colors.onBackground
                )
            }

            Text(
                modifier = Modifier.padding(vertical = MaterialTheme.dimension.medium),
                text = chipData.name,
                style = MaterialTheme.typography.body2,
                color = if (isSelected) {
                    MaterialTheme.colors.surface
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
    val chips =  remember {
        mutableStateListOf(
            ChipData(id = "1", name = "chip 1"),
            ChipData(id = "2", name = "chip 2"),
            ChipData(id = "3", name = "chip 3"),
            ChipData(id = "2", name = "Reddit", Res.drawable.ic_reddit),
            ChipData(id = "4", name = "chip 4"),
            ChipData(id = "5", name = "chip 4", selected = true),
            ChipData(id = "6", name = "chip 4"),
            ChipData(id = "7", name = "chip 4"),
            ChipData(id = "8", name = "chip 4"),
            ChipData(id = "1", name = "Github repositories", image = Res.drawable.ic_github),
            ChipData(id = "9", name = "chip 4"),
            ChipData(id = "42", name = "chip 4"),
            ChipData(
                id = "3",
                name = "FreeCodeCamo",
                Res.drawable.ic_freecodecamp,
                selected = true
            ),
        )
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
    text: StringResource,
    vararg args: Any,
    textStyle: TextStyle = MaterialTheme.typography.body1
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(text, args),
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
            Res.string.failed_to_load_source,
            "github",
            textStyle = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun ErrorMsgWithBtn(
    text: StringResource,
    vararg args: String,
    btnText: StringResource,
    onBtnClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimension.screenPaddingHorizontal)
    ) {
        Text(
            text = stringResource(text, args),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onBackground
        )
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

@Preview()
@Composable
private fun ErrorMsgWithBtnPreview() {
    HackertabTheme {
        ErrorMsgWithBtn(
            text = Res.string.failed_to_load_source,
            "github",
            btnText = Res.string.common_retry
        ){}
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

fun String.getTagColor(): Color {
    for ((tag, color) in tags) {
        if (this.equals(other = tag, ignoreCase = true)) return color
    }

    return Color.DarkGray
}
