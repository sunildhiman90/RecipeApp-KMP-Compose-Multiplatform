package org.sunildhiman90.recipeappcmp.features.favorites.ui

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

data class FavoritesScreenUiState(
    val itemsList: List<RecipeItem>? = null,
    val itemsListIsLoading: Boolean = true,
    val itemsListError: String? = null,
)