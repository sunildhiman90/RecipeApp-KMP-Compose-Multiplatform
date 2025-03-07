package org.sunildhiman90.recipeappcmp.preferences

import com.russhwolf.settings.Settings

expect class MultiplatformSettingsFactory {
    fun getSettings():  Settings
}