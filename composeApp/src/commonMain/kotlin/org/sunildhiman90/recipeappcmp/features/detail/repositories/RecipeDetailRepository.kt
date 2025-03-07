package org.sunildhiman90.recipeappcmp.features.detail.repositories

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

interface RecipeDetailRepository {

    suspend fun getRecipesDetail(id: Long): Result<RecipeItem>
    suspend fun addFavorite(recipeId: Long)
    suspend fun removeFavorite(recipeId: Long)

}