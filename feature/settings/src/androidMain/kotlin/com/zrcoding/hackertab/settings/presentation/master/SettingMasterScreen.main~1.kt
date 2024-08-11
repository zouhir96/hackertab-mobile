package com.zrcoding.hackertab.settings.presentation.master

actual fun contactSupport() {
    /*try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("mailto:rajdaouizouhir.pro@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions pour Hackertab team")
        intent.putExtra(Intent.EXTRA_TEXT, menuContactMessageTemplate(context))
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        AlertDialog.Builder(context)
            .setTitle(R.string.support_no_apps_title)
            .setMessage(R.string.support_no_apps_description)
            .setNeutralButton(R.string.common_ok) { dialog, _ -> dialog.dismiss() }
            .show()
    }*/
}

private fun menuContactMessageTemplate(): String {
    return buildString {
        /*append(("\n\n\n\n"))
        append("----------------------\n")
        append("----------------------\n")
        append(context.getString(R.string.support_support_footer_message))
        append("\n")
        append(context.getString(R.string.support_device_os_version))
        append(Build.VERSION.RELEASE)
        append("\n")
        append(context.getString(R.string.support_device_model))
        append(Build.MANUFACTURER)
        append(" ").append(Build.DEVICE)
        append(" ").append(Build.MODEL)
        append("\n")
        append(context.getString(R.string.support_application_version))
        append(BuildConfig.VERSION_NAME)*/
    }
}