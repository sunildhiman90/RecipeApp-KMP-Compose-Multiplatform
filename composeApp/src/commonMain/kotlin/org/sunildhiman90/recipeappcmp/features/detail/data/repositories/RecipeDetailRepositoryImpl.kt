package org.sunildhiman90.recipeappcmp.features.detail.data.repositories

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem
import org.sunildhiman90.recipeappcmp.features.detail.data.datasources.RecipeDetailLocalDataSource
import org.sunildhiman90.recipeappcmp.features.detail.data.datasources.RecipeDetailRemoteDataSource
import org.sunildhiman90.recipeappcmp.features.detail.repositories.RecipeDetailRepository

class RecipeDetailRepositoryImpl(
    private val recipeDetailLocalDataSource: RecipeDetailLocalDataSource,
    private val recipeDetailRemoteDataSource: RecipeDetailRemoteDataSource
): RecipeDetailRepository {

    override suspend fun getRecipesDetail(id: Long): Result<RecipeItem> {
        return try {
            val recipeDetailCache = recipeDetailLocalDataSource.getRecipeDetail(id)
            return if (recipeDetailCache != null) {
                val isFav = recipeDetailLocalDataSource.isFavorite(recipeId = id)
                Result.success(recipeDetailCache.copy(
                    isFavorite = isFav
                ))
            } else {
                val recipeDetailApiResponse = recipeDetailRemoteDataSource.getRecipeDetail(id)
                    ?: return Result.failure(Exception("Recipe not found!"))
                recipeDetailLocalDataSource.saveRecipe(recipeDetailApiResponse)
                Result.success(recipeDetailApiResponse)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addFavorite(recipeId: Long) {
        recipeDetailLocalDataSource.addFavorite(recipeId)
    }

    override suspend fun removeFavorite(recipeId: Long) {
        recipeDetailLocalDataSource.removeFavorite(recipeId)
    }
}