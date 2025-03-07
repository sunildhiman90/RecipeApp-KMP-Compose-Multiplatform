package org.sunildhiman90.recipeappcmp.preferences

interface AppPreferences {

    fun clear()

    fun getInt(key: String, defaultValue: Int): Int

    fun getIntOrNull(key: String): Int?

    fun getLong(key: String, defaultValue: Long): Long

    fun getLongOrNull(key: String): Long?

    fun getString(key: String, defaultValue: String): String

    fun getStringOrNull(key: String): String?

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun getBooleanOrNull(key: String): Boolean?

    fun putInt(key: String, value: Int)

    fun putString(key: String, value: String)

    fun putLong(key: String, value: Long)

    fun putBoolean(key: String, value: Boolean)

    fun hasKey(key: String): Boolean

    fun remove(key: String)

}