package org.sunildhiman90.recipeappcmp.features.search.data.datasources

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

interface SearchRecipeLocalDataSource {
    suspend fun searchRecipesByText(query: String): List<RecipeItem>
}