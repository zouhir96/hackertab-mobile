package com.zrcoding.hackertab.analytics.models

/**
 * Data class representing user properties used for analytics purposes.
 *
 * These properties are typically used to set and update user-specific information
 * such as name, phone number, and email in analytics platforms.
 *
 * @property name The user's name.
 * @property phone The user's phone number.
 * @property email The user's email address.
 */
data class UserProperties(
    val name: String,
    val phone: String,
    val email: String,
)