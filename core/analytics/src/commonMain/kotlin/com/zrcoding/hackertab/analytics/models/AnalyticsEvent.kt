package com.zrcoding.hackertab.analytics.models

/**
 * Represents an analytics event with a type, name, and associated parameters.
 *
 * @property name The name of the event.
 * @property properties A [Set] of additional parameters associated with the event.
 */
data class AnalyticsEvent(
    val name: String,
    val properties: Set<Param> = emptySet(),
) {
    /**
     * Contains standard event types for analytics.
     */
    object Types {
        const val SCREEN_VIEW = "screen_view"
        const val ELEMENT_CLICKED = "element_clicked"
        const val LOGGED_IN = "logged_in"
        const val LOG_IN = "log_in"
        const val LOG_IN_FACEBOOK = "log_in_facebook"
        const val LOG_IN_GOOGLE = "log_in_google"
        // Add more standard event types here
    }

    /**
     * Contains standard parameter keys for analytics events.
     */
    object ParamKeys {
        const val SCREEN_NAME = "content_type"
        const val ELEMENT_NAME = "element_name"
        const val ELEMENT_SOURCE = "element_source"
        const val PAGE_NAME = "page_name"
        const val LANG = "lang"
        const val EMAIL = "email"
        const val NAME = "name"
        const val PHONE = "phone"
        const val ACCOUNT_TYPE = "account_type"
        const val LOGIN_TYPE = "login_type"
        const val VALUE = "value"
        // Add more standard parameter keys here
    }

    /**
     * Contains standard parameter values for analytics events.
     */
    object ParamValues {
        const val ACCOUNT_TYPE_SHOP = "pro"
        const val ACCOUNT_TYPE_PRIVATE = "private"
        const val CANCEL_ORDER = "cancelorder"
        const val SELECT_STATUS = "select_status"
        const val DELETE_SELECTED = "delete_selected"
        const val FINAL_DELETE = "final_delete"
        const val REACTIVATE = "reactivate"
        const val SHOW_MORE = "show_more"
        const val AD_DETAIL = "ad_detail"
        const val SELECT_ALL = "select_all"
        const val AD_EDIT = "ad_edit"
    }

    /**
     * Contains standard screens names for analytics events.
     */
    object ScreensNames {
        const val LOGIN = "login"
        const val SIGNUP = "signup"
        const val PASSWORD_RECOVERY = "password_recovery"
        const val ACCOUNT = "mysetting"
        const val ACCOUNT_EDIT = "editprofile"
        const val STATISTICS = "mystatistics"
        const val UPDATE_PASSWORD = "update_password"
        const val ORDERS = "myorders"
        const val ACCOUNT_ADS = "myads"
        const val BOOKMARKED_ADS = "mysavedads"
        const val BOOKMARKED_SEARCH = "mysavedsearch"
        // Add more standard parameter keys here
    }
}
