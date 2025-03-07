package org.sunildhiman90.recipeappcmp.features.search.domain.repositories

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

interface SearchRecipeRepository {
    suspend fun searchRecipesByText(query: String): Result<List<RecipeItem>>
}