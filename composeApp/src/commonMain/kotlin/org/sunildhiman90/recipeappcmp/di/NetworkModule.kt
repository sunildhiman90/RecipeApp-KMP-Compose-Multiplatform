package org.sunildhiman90.recipeappcmp.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.sunildhiman90.recipeappcmp.features.common.data.api.httpClient

fun networkModule()  = module {

    single<HttpClient> {
        httpClient
    }
}