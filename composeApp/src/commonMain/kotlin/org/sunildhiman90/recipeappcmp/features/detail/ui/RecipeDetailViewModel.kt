package org.sunildhiman90.recipeappcmp.features.detail.ui

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sunildhiman90.recipeappcmp.features.detail.repositories.RecipeDetailRepository

class RecipeDetailViewModel(
    private val recipeDetailRepository: RecipeDetailRepository
): ViewModel() {

    private var _detailUiState = MutableStateFlow(RecipeDetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    private var _updateIsFavUiState = MutableStateFlow(RecipeDetailUpdateIsFavUiState())
    val updateIsFavUiState = _updateIsFavUiState.asStateFlow()

    suspend fun getRecipeDetail(id: Long) {
        viewModelScope.launch {
            val recipeDetailRes = recipeDetailRepository.getRecipesDetail(id)
            if (recipeDetailRes.isSuccess) {
                _detailUiState.value = _detailUiState.value.copy(
                    recipesDetail = recipeDetailRes.getOrNull(),
                    recipesDetailIsLoading = false
                )
            } else {
                _detailUiState.value = _detailUiState.value.copy(
                    recipesDetailError = recipeDetailRes.exceptionOrNull()?.message,
                    recipesDetailIsLoading = false
                )
            }
        }
    }

    fun updateIsFavorite(recipeId: Long, isAdding: Boolean) {
        viewModelScope.launch {
            try {
                _updateIsFavUiState.value = _updateIsFavUiState.value.copy(isUpdating = true)

                if (isAdding) {
                    recipeDetailRepository.addFavorite(recipeId)
                } else {
                    recipeDetailRepository.removeFavorite(recipeId)
                }

                //refresh detail
                _detailUiState.value = _detailUiState.value.copy(
                    recipesDetail = _detailUiState.value.recipesDetail?.copy(isFavorite = isAdding)
                )
                _updateIsFavUiState.value =
                    _updateIsFavUiState.value.copy(isSuccess = true, isUpdating = false)

            } catch (e: Exception) {
                _updateIsFavUiState.value =
                    _updateIsFavUiState.value.copy(error = e.message, isUpdating = false)
            }
        }
    }

}