package com.zrcoding.hackertab.design.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.nunito_medium
import com.zrcoding.hackertab.design.resources.nunito_regular
import org.jetbrains.compose.resources.Font

val Typography: Typography
    @Composable get() = Typography(
        h4 = TextStyle(
            fontFamily = FontFamily(Font(Res.font.nunito_medium)),
            fontWeight = FontWeight.W900,
            fontSize = 30.sp
        ),
        h5 = TextStyle(
            fontFamily = FontFamily(Font(Res.font.nunito_regular)),
            fontWeight = FontWeight.W900,
            fontSize = 24.sp,
        ),
        h6 = TextStyle(
            fontFamily = FontFamily(Font(Res.font.nunito_medium)),
            fontWeight = FontWeight.W600,
            fontSize = 20.sp
        ),
        subtitle1 = TextStyle(
            fontFamily = FontFamily(Font(Res.font.nunito_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.15.sp
        ),
        subtitle2 = TextStyle(
            fontFamily = FontFamily(Font(Res.font.nunito_medium)),
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            letterSpacing = 0.1.sp
        ),
        body2 = TextStyle(
            fontFamily = FontFamily(Font(Res.font.nunito_medium)),
            fontSize = 14.sp,
            letterSpacing = 0.25.sp
        ),
        button = TextStyle(
            fontFamily = FontFamily(Font(Res.font.nunito_medium)),
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            letterSpacing = 1.25.sp
        ),
        caption = TextStyle(
            fontFamily = FontFamily(Font(Res.font.nunito_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        overline = TextStyle(
            fontFamily = FontFamily(Font(Res.font.nunito_regular)),
            fontWeight = FontWeight.W500,
            fontSize = 12.sp
        )
    )