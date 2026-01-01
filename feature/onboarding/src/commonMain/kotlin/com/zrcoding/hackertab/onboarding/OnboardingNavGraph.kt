package com.zrcoding.hackertab.onboarding

import com.zrcoding.hackertab.domain.models.Profile
import kotlinx.serialization.Serializable

@Serializable
data class SetupProfileScreen(val newUser: Boolean)

@Serializable
data class SetupTopicsScreen(val profile: Profile)

@Serializable
data object SetupSourcesScreen