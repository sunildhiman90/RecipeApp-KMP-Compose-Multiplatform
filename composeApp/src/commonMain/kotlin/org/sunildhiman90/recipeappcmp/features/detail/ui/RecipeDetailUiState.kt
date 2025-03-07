package org.sunildhiman90.recipeappcmp.features.detail.ui

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

data class RecipeDetailUiState(
    val recipesDetail: RecipeItem? = null,
    val recipesDetailIsLoading: Boolean = true,
    val recipesDetailError: String? = null,
)
