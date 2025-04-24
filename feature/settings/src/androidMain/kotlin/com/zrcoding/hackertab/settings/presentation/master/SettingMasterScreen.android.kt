package com.zrcoding.hackertab.settings.presentation.master

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build

class AndroidContactSupport(private val context: Activity) : ContactSupport{

    override fun invoke(data: ContactSupportData) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("mailto:${data.email}")
            intent.putExtra(Intent.EXTRA_SUBJECT, data.subject)
            intent.putExtra(Intent.EXTRA_TEXT, menuContactMessageTemplate(data))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // TODO Replace by composable alert.
            AlertDialog.Builder(context)
                .setTitle(data.noAppFoundTitle)
                .setMessage(data.noAppFoundDescription)
                .setNeutralButton(data.noAppFoundOk) { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }
}

private fun menuContactMessageTemplate(data: ContactSupportData): String {
    return buildString {
        append(("\n\n\n\n"))
        append("----------------------\n")
        append("----------------------\n")
        append(data.footerMessage)
        append("\n")
        append(data.osVersion)
        append("${Build.DEVICE} ${Build.VERSION.RELEASE}")
        append("\n")
        append(data.deviceModel)
        append(Build.MANUFACTURER)
        append(" ").append(Build.DEVICE)
        append(" ").append(Build.MODEL)
        append("\n")
        append(data.appVersion)
        //b append(BuildConfig.VERSION_NAME)
    }
}