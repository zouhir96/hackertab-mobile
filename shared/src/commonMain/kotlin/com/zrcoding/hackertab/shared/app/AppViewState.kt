package com.zrcoding.hackertab.shared.app

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.domain.models.ThemeMode
import com.zrcoding.hackertab.domain.models.ThemePalette
import com.zrcoding.hackertab.domain.usecases.GetStartDestinationUseCase

@Stable
data class AppViewState(
    val setupStatus: GetStartDestinationUseCase.Result? = null,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val themePalette: ThemePalette = ThemePalette.DEFAULT,
)
