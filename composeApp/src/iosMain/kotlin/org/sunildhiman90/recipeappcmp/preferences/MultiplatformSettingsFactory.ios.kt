package org.sunildhiman90.recipeappcmp.preferences

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

actual class MultiplatformSettingsFactory {
    actual fun getSettings(): Settings {
        val delegate = NSUserDefaults.standardUserDefaults()
        return NSUserDefaultsSettings(delegate)
    }
}