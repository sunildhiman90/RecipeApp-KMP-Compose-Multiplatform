package org.sunildhiman90.recipeappcmp.di

import org.koin.dsl.module
import org.sunildhiman90.recipeappcmp.dbFactory.DatabaseFactory
import org.sunildhiman90.recipeappcmp.preferences.MultiplatformSettingsFactory

val jvmModules = module {
    single { DatabaseFactory() }
    single { MultiplatformSettingsFactory() }
}

fun initKoinJvm() = initKoin(additionalModules = listOf(jvmModules))
