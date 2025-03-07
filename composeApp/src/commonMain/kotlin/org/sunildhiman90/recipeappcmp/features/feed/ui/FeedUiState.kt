package org.sunildhiman90.recipeappcmp.features.feed.ui

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

data class FeedUiState(
    val recipesList: List<RecipeItem>? = null,
    val recipesListIsLoading: Boolean = true,
    val recipesListError: String? = null,
)