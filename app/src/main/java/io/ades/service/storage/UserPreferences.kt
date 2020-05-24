package io.ades.service.storage


import io.ades.data.User

interface UserPreferences {

    fun saveUserInfo(user : User)
    fun getUserInfo() : User

    fun setUserLoggedIn(state: Boolean)
    fun isUserLoggedIn() : Boolean

    fun setAccessKey(key : String)
    fun getAccessKey(): String

    fun saveAnonimusUserId(tokenId : String)
    fun getAnonimusUserId() : String?

    fun logout()

    fun checkUserIsFirstTimer(id : String) : Boolean

    fun markUserAsNotFirstTimer(id :String)
}