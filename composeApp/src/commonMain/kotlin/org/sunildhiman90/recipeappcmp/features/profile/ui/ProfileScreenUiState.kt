package org.sunildhiman90.recipeappcmp.features.profile.ui

import org.sunildhiman90.recipeappcmp.features.profile.data.User


data class ProfileScreenUiState(
    val userInfo: User? = null,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)