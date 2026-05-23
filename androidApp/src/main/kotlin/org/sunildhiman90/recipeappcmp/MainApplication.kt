package org.sunildhiman90.recipeappcmp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.sunildhiman90.recipeappcmp.dbFactory.DatabaseFactory
import org.sunildhiman90.recipeappcmp.di.initKoin
import org.sunildhiman90.recipeappcmp.preferences.MultiplatformSettingsFactory

class MainApplication: Application() {

    private val androidModules = module {
        single { DatabaseFactory(applicationContext) }
        single { MultiplatformSettingsFactory(applicationContext) }
    }

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        initKoin(additionalModules = listOf(androidModules)) {
            androidContext(applicationContext)
        }
    }
}