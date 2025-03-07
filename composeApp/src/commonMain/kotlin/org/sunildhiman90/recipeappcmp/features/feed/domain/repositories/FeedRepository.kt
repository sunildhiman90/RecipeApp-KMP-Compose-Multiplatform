package org.sunildhiman90.recipeappcmp.features.feed.domain.repositories

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

interface FeedRepository {

    suspend fun getRecipesList(): Result<List<RecipeItem>>
}