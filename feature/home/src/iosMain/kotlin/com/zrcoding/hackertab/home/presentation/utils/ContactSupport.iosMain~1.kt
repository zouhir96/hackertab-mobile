package com.zrcoding.hackertab.home.presentation.utils

import platform.Foundation.NSError
import platform.MessageUI.MFMailComposeResult
import platform.MessageUI.MFMailComposeViewController
import platform.MessageUI.MFMailComposeViewControllerDelegateProtocol
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.darwin.NSObject

class IOSContactSupport : ContactSupport {

    override fun invoke(data: ContactSupportData) {
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        if (MFMailComposeViewController.canSendMail()) {
            val mailComposeViewController = MFMailComposeViewController()
            mailComposeViewController.setToRecipients(listOf(data.email))
            mailComposeViewController.setSubject(data.subject)
            mailComposeViewController.setMessageBody(menuContactMessageTemplate(data), false)

            mailComposeViewController.mailComposeDelegate =
                object : NSObject(), MFMailComposeViewControllerDelegateProtocol {
                    override fun mailComposeController(
                        controller: MFMailComposeViewController,
                        didFinishWithResult: MFMailComposeResult,
                        error: NSError?
                    ) {
                        controller.dismissViewControllerAnimated(true, completion = null)
                    }
                }

            rootViewController?.presentViewController(
                mailComposeViewController,
                animated = true,
                completion = null
            )
        } else {
            val alert = UIAlertController.alertControllerWithTitle(
                title = data.noAppFoundTitle,
                message = data.noAppFoundDescription,
                preferredStyle = UIAlertControllerStyleAlert
            )
            alert.addAction(
                UIAlertAction.actionWithTitle(
                    title = data.noAppFoundOk,
                    style = UIAlertActionStyleDefault,
                    handler = null
                )
            )
            rootViewController?.presentViewController(alert, animated = true, completion = null)
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
        append(UIDevice.currentDevice.let { "${it.systemName} ${it.systemVersion}" })
        append("\n")
        append(data.deviceModel)
        append(UIDevice.currentDevice.model)
        append("\n")
        append(data.appVersion)
        // Append app version if needed
    }
}