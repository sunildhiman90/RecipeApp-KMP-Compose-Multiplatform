package org.sunildhiman90.recipeappcmp.features.search.data.repositories

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem
import org.sunildhiman90.recipeappcmp.features.search.data.datasources.SearchRecipeLocalDataSource
import org.sunildhiman90.recipeappcmp.features.search.domain.repositories.SearchRecipeRepository

class SearchRecipeRepositoryImpl(
    private val searchRecipeLocalDataSource: SearchRecipeLocalDataSource
): SearchRecipeRepository {
    override suspend fun searchRecipesByText(query: String): Result<List<RecipeItem>> {
        return try {
            val resultList = searchRecipeLocalDataSource.searchRecipesByText(query)
            Result.success(resultList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}