package com.zrcoding.hackertab.design.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.zrcoding.hackertab.design.resources.Res
import com.zrcoding.hackertab.design.resources.geist_bold
import com.zrcoding.hackertab.design.resources.geist_medium
import com.zrcoding.hackertab.design.resources.geist_mono_medium
import com.zrcoding.hackertab.design.resources.geist_mono_semibold
import com.zrcoding.hackertab.design.resources.geist_regular
import com.zrcoding.hackertab.design.resources.geist_semibold
import org.jetbrains.compose.resources.Font

val GeistSans: FontFamily
    @Composable get() = FontFamily(
        Font(Res.font.geist_regular, FontWeight.W400),
        Font(Res.font.geist_medium, FontWeight.W500),
        Font(Res.font.geist_semibold, FontWeight.W600),
        Font(Res.font.geist_bold, FontWeight.W700),
    )

val GeistMono: FontFamily
    @Composable get() = FontFamily(
        Font(Res.font.geist_mono_medium, FontWeight.W500),
        Font(Res.font.geist_mono_semibold, FontWeight.W600),
    )

val Typography: Typography
    @Composable get() {
        val sans = GeistSans
        return Typography(
            displayLarge = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W600,
                fontSize = 56.sp,
                lineHeight = 58.24.sp,
                letterSpacing = (-0.04).em,
            ),
            displayMedium = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W600,
                fontSize = 44.sp,
                lineHeight = 46.64.sp,
                letterSpacing = (-0.035).em,
            ),
            displaySmall = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W600,
                fontSize = 34.sp,
                lineHeight = 36.72.sp,
                letterSpacing = (-0.03).em,
            ),
            headlineLarge = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W600,
                fontSize = 28.sp,
                lineHeight = 32.2.sp,
                letterSpacing = (-0.02).em,
            ),
            headlineMedium = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W600,
                fontSize = 22.sp,
                lineHeight = 26.4.sp,
                letterSpacing = (-0.015).em,
            ),
            headlineSmall = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                lineHeight = 22.5.sp,
                letterSpacing = (-0.01).em,
            ),
            titleLarge = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W600,
                fontSize = 17.sp,
                lineHeight = 22.1.sp,
                letterSpacing = (-0.005).em,
            ),
            titleMedium = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W600,
                fontSize = 15.sp,
                lineHeight = 20.25.sp,
            ),
            titleSmall = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W600,
                fontSize = 13.sp,
                lineHeight = 17.55.sp,
            ),
            bodyLarge = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                lineHeight = 23.2.sp,
            ),
            bodyMedium = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                lineHeight = 20.3.sp,
            ),
            bodySmall = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W400,
                fontSize = 13.sp,
                lineHeight = 18.2.sp,
            ),
            labelLarge = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                lineHeight = 16.8.sp,
            ),
            labelMedium = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W500,
                fontSize = 12.sp,
                lineHeight = 14.4.sp,
                letterSpacing = 0.01.em,
            ),
            labelSmall = TextStyle(
                fontFamily = sans,
                fontWeight = FontWeight.W500,
                fontSize = 11.sp,
                lineHeight = 13.2.sp,
                letterSpacing = 0.02.em,
            ),
        )
    }

val codeMedium: TextStyle
    @Composable get() = TextStyle(
        fontFamily = GeistMono,
        fontWeight = FontWeight.W500,
        fontSize = 13.sp,
        lineHeight = 17.55.sp,
        letterSpacing = (-0.01).em,
    )

val codeSmall: TextStyle
    @Composable get() = TextStyle(
        fontFamily = GeistMono,
        fontWeight = FontWeight.W500,
        fontSize = 11.sp,
        lineHeight = 14.3.sp,
    )
