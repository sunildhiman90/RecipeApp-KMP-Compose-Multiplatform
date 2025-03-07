package org.sunildhiman90.recipeappcmp.preferences

import com.russhwolf.settings.Settings

class AppPreferencesImpl(
    private val settingsFactory: MultiplatformSettingsFactory
) : AppPreferences {

    private val settings: Settings by lazy {
        settingsFactory.getSettings()
    }

    override fun clear() {
        settings.clear()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return settings.getInt(key, defaultValue)
    }

    override fun getIntOrNull(key: String): Int? {
        return settings.getIntOrNull(key)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return settings.getLong(key, defaultValue)
    }

    override fun getLongOrNull(key: String): Long? {
        return settings.getLongOrNull(key)
    }

    override fun getString(key: String, defaultValue: String): String {
        return settings.getString(key, defaultValue)
    }

    override fun getStringOrNull(key: String): String? {
        return settings.getStringOrNull(key)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return settings.getBoolean(key, defaultValue)
    }

    override fun getBooleanOrNull(key: String): Boolean? {
        return settings.getBooleanOrNull(key)
    }

    override fun putInt(key: String, value: Int) {
        settings.putInt(key, value)
    }

    override fun putString(key: String, value: String) {
        settings.putString(key, value)
    }

    override fun putLong(key: String, value: Long) {
        settings.putLong(key, value)
    }

    override fun putBoolean(key: String, value: Boolean) {
        settings.putBoolean(key, value)
    }

    override fun hasKey(key: String): Boolean {
        return settings.hasKey(key)
    }

    override fun remove(key: String) {
        settings.remove(key)
    }

}