package com.zrcoding.hackertab.onboarding.profile

import androidx.compose.runtime.Stable
import com.zrcoding.hackertab.domain.models.Profile
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class SetupProfileViewState(
    val profiles: PersistentList<Profile> = persistentListOf(),
    val selectedProfile: Profile? = null,
) {
    fun canContinue() = selectedProfile != null
}
