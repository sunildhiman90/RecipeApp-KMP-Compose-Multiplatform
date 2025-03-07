package org.sunildhiman90.recipeappcmp.features.favorites.data

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

interface FavoriteRecipeLocalDataSource {
    suspend fun getAllFavoriteRecipes(): List<RecipeItem>
    suspend fun addFavorite(recipeId: Long)
    suspend fun removeFavorite(recipeId: Long)
}