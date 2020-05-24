package io.nta.w_lease.service.storage

interface Storage {
    fun putBoolean(key : String, value : Boolean)
    fun getBoolean(key : String, defaultValue: Boolean) : Boolean

    fun putString(key : String, value : String)
    fun getString(key : String, defaultValue: String): String
    fun getString(key : String): String?

    fun putLong(key : String, value : Long)
    fun getLong(key : String, defaultValue: Long): Long

    fun putObject(key: String, value: Any?)
    fun <T> getObject(key: String, objClass: Class<T>) : T?

    fun clean()
}