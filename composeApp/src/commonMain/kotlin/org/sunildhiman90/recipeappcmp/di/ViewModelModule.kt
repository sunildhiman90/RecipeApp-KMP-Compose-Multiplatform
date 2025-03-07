package org.sunildhiman90.recipeappcmp.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.sunildhiman90.recipeappcmp.features.detail.ui.RecipeDetailViewModel
import org.sunildhiman90.recipeappcmp.features.favorites.ui.FavoritesScreenViewModel
import org.sunildhiman90.recipeappcmp.features.feed.ui.FeedViewModel
import org.sunildhiman90.recipeappcmp.features.login.ui.LoginViewModel
import org.sunildhiman90.recipeappcmp.features.profile.ui.ProfileViewModel
import org.sunildhiman90.recipeappcmp.features.search.ui.SearchViewModel

fun viewModelModule()  = module {

    viewModel {
        FeedViewModel(get())
    }

    viewModel {
        RecipeDetailViewModel(get())
    }

    viewModel {
        FavoritesScreenViewModel(get())
    }

    viewModel {
        ProfileViewModel()
    }

    viewModel {
        LoginViewModel()
    }
    viewModel {
        SearchViewModel(get())
    }

}