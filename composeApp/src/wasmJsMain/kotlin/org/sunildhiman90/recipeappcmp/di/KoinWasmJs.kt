package org.sunildhiman90.recipeappcmp.di

import org.koin.dsl.module
import org.sunildhiman90.recipeappcmp.dbFactory.DatabaseFactory
import org.sunildhiman90.recipeappcmp.preferences.MultiplatformSettingsFactory

val wasmJsModules = module {
    single { DatabaseFactory() }
    single { MultiplatformSettingsFactory() }
}

fun initKoinWasmJs() = initKoin(additionalModules = listOf(wasmJsModules))
