package org.sunildhiman90.recipeappcmp.features.search.data.datasources

import org.sunildhiman90.recipeappcmp.features.common.data.database.daos.RecipeDao
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

class SearchRecipeLocalDataSourceImpl(
    private val recipeDao: RecipeDao
): SearchRecipeLocalDataSource {
    override suspend fun searchRecipesByText(query: String): List<RecipeItem> {
        return recipeDao.searchRecipesByText(query)
    }
}