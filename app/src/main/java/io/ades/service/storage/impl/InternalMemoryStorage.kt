package io.ades.service.storage.impl

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import io.nta.w_lease.service.storage.Storage

open class InternalMemoryStorage(context: Context) : Storage {
    companion object {
        private const val TAG = "InternalMemoryStorage"
    }
    private val prefs: SharedPreferences = context.getSharedPreferences(javaClass.simpleName, Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun putBoolean(key: String, value: Boolean) {
        edit {
            putBoolean(key, value)
        }
    }

    override fun getBoolean(key: String, defaultValue: Boolean) : Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    override fun putString(key: String, value: String) {
        edit {
            putString(key, value)
        }
    }

    override fun getString(key: String, defaultValue: String) : String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    private fun edit(action: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
        prefs.edit().action().apply()
    }

    override fun putLong(key: String, value: Long) {
        edit {
            putLong(key, value)
        }
    }

    override fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return prefs.getLong(key, defaultValue)
    }

    override fun putObject(key: String, value: Any?) {
        edit {
            if(value == null){
                putString(key, "")
            }else {
                val json = gson.toJson(value)
                putString(key, json)
            }
        }
    }

    override fun <T> getObject(key: String, objClass: Class<T>): T? {
        if(!prefs.contains(key)){
            return null
        }
        val json = prefs.getString(key, "")
        if(json.isNullOrEmpty()){
            return null
        }
        val obj = gson.fromJson<T>(json, objClass)
        return obj as T
    }

    override fun clean() {
        prefs.edit().clear().apply()
    }
}