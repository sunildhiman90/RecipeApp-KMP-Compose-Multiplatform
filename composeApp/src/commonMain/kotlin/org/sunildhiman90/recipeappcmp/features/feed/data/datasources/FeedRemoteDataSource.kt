package org.sunildhiman90.recipeappcmp.features.feed.data.datasources

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

interface FeedRemoteDataSource {
    suspend fun getRecipesList(): List<RecipeItem>
}