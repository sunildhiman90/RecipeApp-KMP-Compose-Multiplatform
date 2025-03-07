package org.sunildhiman90.recipeappcmp.preferences

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import kotlinx.browser.window

actual class MultiplatformSettingsFactory {
    actual fun getSettings(): Settings {
        val delegate = window.localStorage
        return StorageSettings(delegate)
    }
}