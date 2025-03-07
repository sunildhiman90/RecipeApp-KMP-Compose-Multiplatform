package org.sunildhiman90.recipeappcmp.features.detail.data.datasources

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

interface RecipeDetailRemoteDataSource {

    suspend fun getRecipeDetail(id: Long): RecipeItem?

}