package org.sunildhiman90.recipeappcmp.features.feed.data.datasources

import org.sunildhiman90.recipeappcmp.features.common.data.database.daos.RecipeDao
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

class FeedLocalDataSourceImpl(
    private val recipeDao: RecipeDao
): FeedLocalDataSource {
    override suspend fun getRecipesList(): List<RecipeItem> {
       return recipeDao.getAllRecipes()
    }

    override suspend fun saveRecipesList(recipes: List<RecipeItem>) {
        recipeDao.insertRecipesBulk(recipes)
    }
}