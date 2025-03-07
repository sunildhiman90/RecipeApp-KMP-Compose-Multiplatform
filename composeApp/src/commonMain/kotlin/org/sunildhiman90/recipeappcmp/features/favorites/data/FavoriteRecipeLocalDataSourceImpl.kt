package org.sunildhiman90.recipeappcmp.features.favorites.data

import org.sunildhiman90.recipeappcmp.features.common.data.database.daos.FavoriteRecipeDao
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

class FavoriteRecipeLocalDataSourceImpl(
    private val favoriteRecipeDao: FavoriteRecipeDao
): FavoriteRecipeLocalDataSource {
    override suspend fun getAllFavoriteRecipes(): List<RecipeItem> {
        return favoriteRecipeDao.getAllFavoriteRecipes()
    }
    override suspend fun addFavorite(recipeId: Long) {
        favoriteRecipeDao.addFavorite(recipeId)
    }
    override suspend fun removeFavorite(recipeId: Long) {
        favoriteRecipeDao.removeFavorite(recipeId)
    }
}