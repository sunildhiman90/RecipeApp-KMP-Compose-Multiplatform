package org.sunildhiman90.recipeappcmp

import org.koin.dsl.module
import org.sunildhiman90.recipeappcmp.dbFactory.DatabaseFactory
import org.sunildhiman90.recipeappcmp.di.initKoin
import org.sunildhiman90.recipeappcmp.preferences.MultiplatformSettingsFactory

val iosModules = module {
    single { DatabaseFactory() }
    single { MultiplatformSettingsFactory() }
}

fun initKoinIOS() = initKoin(additionalModules = listOf(iosModules))
