package io.ades.service.storage




interface AppPreferences {

    fun allowPushNotifications()
    fun forbidPushNotification()
    fun isPushNotificatinsAllowed() : Boolean

    fun setLocale(countryCode : String)
    fun getAppLocale() : String
}