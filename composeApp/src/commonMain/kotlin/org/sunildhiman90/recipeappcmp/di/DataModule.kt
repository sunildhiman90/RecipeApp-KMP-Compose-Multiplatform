package org.sunildhiman90.recipeappcmp.di

import org.koin.dsl.module
import org.sunildhiman90.recipeappcmp.features.detail.data.datasources.RecipeDetailLocalDataSource
import org.sunildhiman90.recipeappcmp.features.detail.data.datasources.RecipeDetailLocalDataSourceImpl
import org.sunildhiman90.recipeappcmp.features.detail.data.datasources.RecipeDetailRemoteDataSource
import org.sunildhiman90.recipeappcmp.features.detail.data.datasources.RecipeDetailRemoteDataSourceImpl
import org.sunildhiman90.recipeappcmp.features.detail.data.repositories.RecipeDetailRepositoryImpl
import org.sunildhiman90.recipeappcmp.features.detail.repositories.RecipeDetailRepository
import org.sunildhiman90.recipeappcmp.features.favorites.data.FavoriteRecipeLocalDataSource
import org.sunildhiman90.recipeappcmp.features.favorites.data.FavoriteRecipeLocalDataSourceImpl
import org.sunildhiman90.recipeappcmp.features.favorites.data.FavoriteRecipeRepositoryImpl
import org.sunildhiman90.recipeappcmp.features.favorites.domain.FavoriteRecipeRepository
import org.sunildhiman90.recipeappcmp.features.feed.data.datasources.FeedLocalDataSource
import org.sunildhiman90.recipeappcmp.features.feed.data.datasources.FeedLocalDataSourceImpl
import org.sunildhiman90.recipeappcmp.features.feed.data.datasources.FeedRemoteDataSource
import org.sunildhiman90.recipeappcmp.features.feed.data.datasources.FeedRemoteDataSourceImpl
import org.sunildhiman90.recipeappcmp.features.feed.data.repositories.FeedRepositoryImpl
import org.sunildhiman90.recipeappcmp.features.feed.domain.repositories.FeedRepository
import org.sunildhiman90.recipeappcmp.features.search.data.datasources.SearchRecipeLocalDataSource
import org.sunildhiman90.recipeappcmp.features.search.data.datasources.SearchRecipeLocalDataSourceImpl
import org.sunildhiman90.recipeappcmp.features.search.data.repositories.SearchRecipeRepositoryImpl
import org.sunildhiman90.recipeappcmp.features.search.domain.repositories.SearchRecipeRepository
import org.sunildhiman90.recipeappcmp.preferences.AppPreferences
import org.sunildhiman90.recipeappcmp.preferences.AppPreferencesImpl

fun dataModule()  = module {

    single<AppPreferences> { AppPreferencesImpl(get()) }

    single<FeedLocalDataSource> { FeedLocalDataSourceImpl(get()) }
    single<FeedRemoteDataSource> { FeedRemoteDataSourceImpl(get()) }

    single<RecipeDetailLocalDataSource> { RecipeDetailLocalDataSourceImpl(get(), get()) }
    single<RecipeDetailRemoteDataSource> { RecipeDetailRemoteDataSourceImpl(get()) }

    single<FavoriteRecipeLocalDataSource> { FavoriteRecipeLocalDataSourceImpl(get()) }

    single<FeedRepository> { FeedRepositoryImpl(get(), get()) }
    single<RecipeDetailRepository> { RecipeDetailRepositoryImpl(get(), get()) }
    single<FavoriteRecipeRepository> { FavoriteRecipeRepositoryImpl(get()) }

    single<SearchRecipeLocalDataSource> { SearchRecipeLocalDataSourceImpl(get()) }
    single<SearchRecipeRepository> { SearchRecipeRepositoryImpl(get()) }
}