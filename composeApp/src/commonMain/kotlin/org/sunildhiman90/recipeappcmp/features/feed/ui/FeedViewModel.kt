package org.sunildhiman90.recipeappcmp.features.feed.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sunildhiman90.recipeappcmp.features.feed.domain.repositories.FeedRepository

class FeedViewModel(
    private val feedRepository: FeedRepository
) : ViewModel() {

    private var _feedUiState = MutableStateFlow(FeedUiState())
    val feedUiState = _feedUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getRecipesList()
        }
    }

    private suspend fun getRecipesList() {
        val recipesList = feedRepository.getRecipesList()
        if (recipesList.isSuccess) {
            _feedUiState.value = _feedUiState.value.copy(
                recipesList = recipesList.getOrDefault(emptyList()),
                recipesListIsLoading = false
            )
        } else {
            _feedUiState.update {
                it.copy(
                    recipesListError = recipesList.exceptionOrNull()?.message,
                    recipesListIsLoading = false
                )
            }
        }
    }

}