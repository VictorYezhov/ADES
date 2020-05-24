package io.ades.service.storage.impl


import android.content.Context
import io.ades.service.storage.AppPreferences



class AppPreferencesImpl(context: Context) : InternalMemoryStorage(context), AppPreferences {

    companion object{
        private const val PUSH_NOTIFICATION_TAG = "push_notifications"
        private const val LOCALE_TAG = "locale_tag"
        private const val DEFAULT_LOCALE = "en"
        private const val TIMER_TIME_KEY = ""
    }

    override fun allowPushNotifications() {
        putBoolean(PUSH_NOTIFICATION_TAG, true)
    }

    override fun forbidPushNotification() {
        putBoolean(PUSH_NOTIFICATION_TAG, false)
    }

    override fun isPushNotificatinsAllowed(): Boolean {
        return  getBoolean(PUSH_NOTIFICATION_TAG, true)
    }

    override fun setLocale(countryCode: String) {
        putString(LOCALE_TAG, countryCode)
    }

    override fun getAppLocale(): String {
        return  getString(LOCALE_TAG, DEFAULT_LOCALE)
    }

}