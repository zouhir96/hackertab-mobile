package com.zrcoding.hackertab.onboarding

import kotlinx.serialization.Serializable

@Serializable
data class SetupProfileScreen(val newUser: Boolean)

@Serializable
data object SetupTopicsScreen

@Serializable
data object SetupSourcesScreen