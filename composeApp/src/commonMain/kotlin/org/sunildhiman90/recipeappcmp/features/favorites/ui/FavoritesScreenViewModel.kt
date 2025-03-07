package org.sunildhiman90.recipeappcmp.features.favorites.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sunildhiman90.recipeappcmp.features.detail.ui.RecipeDetailUpdateIsFavUiState
import org.sunildhiman90.recipeappcmp.features.favorites.domain.FavoriteRecipeRepository

class FavoritesScreenViewModel(
    private val favoriteRecipeRepository: FavoriteRecipeRepository
): ViewModel() {

    private var _favoritesScreenUiState = MutableStateFlow(FavoritesScreenUiState())
    val favoritesScreenUiState = _favoritesScreenUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getRecipesList()
        }
    }

    suspend fun getRecipesList() {
        val recipesList = favoriteRecipeRepository.getAllFavoriteRecipes()
        if (recipesList.isSuccess) {
            _favoritesScreenUiState.value = _favoritesScreenUiState.value.copy(
                itemsList = recipesList.getOrDefault(emptyList()),
                itemsListIsLoading = false
            )
        } else {
            _favoritesScreenUiState.update {
                it.copy(
                    itemsListError = recipesList.exceptionOrNull()?.message,
                    itemsListIsLoading = false
                )
            }
        }
    }
}