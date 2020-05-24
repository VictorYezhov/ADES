package io.ades.service.storage.impl

import android.content.Context
import io.ades.data.User


import io.ades.service.storage.UserPreferences



class UserPreferencesImpl(context: Context) : InternalMemoryStorage(context), UserPreferences {

    companion object {
        private const val TAG = "UserPreferencesImpl"
        private const val STATE_OF_LOGIN = "state_of_login_test"
        private const val ACCESS_KEY = "access_key_test"
        private const val USER_INFO = "user_info_key_test"
        private const val SEARCH_CONDITION = "search_condition_key_test"
        private const val ANONIMUS_ID = "anonymous_user_id"
    }

    override fun saveUserInfo(user: User) {

        putObject(USER_INFO, user)
    }

    override fun getUserInfo(): User {
        return getObject(USER_INFO, User::class.java) ?: User.NO_USER
    }

    override fun setUserLoggedIn(state: Boolean) {
        putBoolean(STATE_OF_LOGIN, state)
    }

    override fun isUserLoggedIn(): Boolean {
        return getBoolean(STATE_OF_LOGIN, false)
    }

    override fun setAccessKey(key: String) {
        setUserLoggedIn(true)
        putString(ACCESS_KEY, key)
    }

    override fun getAccessKey(): String {
        return getString(ACCESS_KEY, "")
    }

    override fun logout() {
        putBoolean(STATE_OF_LOGIN, false)
        putObject(USER_INFO, User.NO_USER)
        putString(ACCESS_KEY, "")
        clean()
    }

    override fun saveAnonimusUserId(tokenId: String) {
        putString(ANONIMUS_ID, tokenId)
    }

    override fun getAnonimusUserId(): String? {
       return getString(ANONIMUS_ID)
    }

    override fun checkUserIsFirstTimer(id: String) = getBoolean(id, true)


    override fun markUserAsNotFirstTimer(id: String)  = putBoolean(id, false)

}