package com.zrcoding.hackertab.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zrcoding.hackertab.R
import com.zrcoding.hackertab.ui.theme.HackertabTheme
import com.zrcoding.hackertab.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun CardHeaderPreview() {
    HackertabTheme {
        CardHeader(title = "HackerNews", icon = R.drawable.ic_score)
    }
}

@Composable
fun CardHeader(title: String, icon: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(topEnd = 14.dp, topStart = 14.dp)
            )
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "card header icon",
            modifier = Modifier.padding(start = 24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = Typography.subtitle1,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    HackertabTheme {
        Loading("Loading")
    }
}

@Composable
fun Loading(title: String = "") {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = title, color = Color.Black)
        CircularProgressIndicator(
            color = Color.Black,
            modifier = Modifier
                .size(60.dp)

        )
    }
}