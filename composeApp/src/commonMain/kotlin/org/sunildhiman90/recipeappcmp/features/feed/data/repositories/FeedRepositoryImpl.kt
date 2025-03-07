package org.sunildhiman90.recipeappcmp.features.feed.data.repositories

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem
import org.sunildhiman90.recipeappcmp.features.feed.data.datasources.FeedLocalDataSource
import org.sunildhiman90.recipeappcmp.features.feed.data.datasources.FeedRemoteDataSource
import org.sunildhiman90.recipeappcmp.features.feed.domain.repositories.FeedRepository

class FeedRepositoryImpl(
    private val feedLocalDataSource: FeedLocalDataSource,
    private val feedRemoteDataSource: FeedRemoteDataSource,
): FeedRepository {

    override suspend fun getRecipesList(): Result<List<RecipeItem>> {
        return try {
            val recipeListCache = feedLocalDataSource.getRecipesList()
            val count = recipeListCache.count()
            return if (count > 0) {
                Result.success(recipeListCache)
            } else {
                val recipeListApiResponse = feedRemoteDataSource.getRecipesList()
                feedLocalDataSource.saveRecipesList(recipeListApiResponse)
                Result.success(recipeListApiResponse)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}