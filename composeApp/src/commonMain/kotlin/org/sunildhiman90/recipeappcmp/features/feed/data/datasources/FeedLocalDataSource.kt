package org.sunildhiman90.recipeappcmp.features.feed.data.datasources

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

interface FeedLocalDataSource {
    suspend fun getRecipesList(): List<RecipeItem>
    suspend fun saveRecipesList(recipes: List<RecipeItem>)
}