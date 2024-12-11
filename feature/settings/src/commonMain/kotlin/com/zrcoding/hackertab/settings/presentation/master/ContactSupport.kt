package com.zrcoding.hackertab.settings.presentation.master

data class ContactSupportData(
    val email: String,
    val subject: String,
    val footerMessage: String,
    val osVersion: String,
    val deviceModel: String,
    val appVersion: String,
    val noAppFoundTitle: String,
    val noAppFoundDescription: String,
    val noAppFoundOk: String,
)

interface ContactSupport {
    fun invoke(data: ContactSupportData)
}