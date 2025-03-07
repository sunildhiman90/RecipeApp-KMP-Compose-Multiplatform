package org.sunildhiman90.recipeappcmp.features.detail.ui


data class RecipeDetailUpdateIsFavUiState(
    val isSuccess: Boolean? = null,
    val isUpdating: Boolean = true,
    val error: String? = null,
)
