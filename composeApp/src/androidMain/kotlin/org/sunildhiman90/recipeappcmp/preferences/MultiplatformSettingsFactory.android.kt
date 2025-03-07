package org.sunildhiman90.recipeappcmp.preferences

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

private const val ANDROID_PREF_NAME = "android.pref"

actual class MultiplatformSettingsFactory(private val context: Context) {
    actual fun getSettings(): Settings {
        val delegate = context.getSharedPreferences(ANDROID_PREF_NAME, Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate)
    }
}