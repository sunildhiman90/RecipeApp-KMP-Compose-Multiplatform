package org.sunildhiman90.recipeappcmp.features.detail.data.datasources

import org.sunildhiman90.recipeappcmp.features.common.data.database.daos.FavoriteRecipeDao
import org.sunildhiman90.recipeappcmp.features.common.data.database.daos.RecipeDao
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

class RecipeDetailLocalDataSourceImpl(
    private val recipeDao: RecipeDao,
    private val favoriteRecipeDao: FavoriteRecipeDao
): RecipeDetailLocalDataSource {

    override suspend fun getRecipeDetail(id: Long): RecipeItem? {
        return recipeDao.getRecipeById(id)
    }

    override suspend fun saveRecipe(recipe: RecipeItem) {
        recipeDao.insertRecipe(recipe)
    }

    override suspend fun addFavorite(recipeId: Long) {
        favoriteRecipeDao.addFavorite(recipeId)
    }

    override suspend fun removeFavorite(recipeId: Long) {
        favoriteRecipeDao.removeFavorite(recipeId)
    }

    override suspend fun isFavorite(recipeId: Long): Boolean {
        return favoriteRecipeDao.isFavorite(recipeId)
    }
}