package org.sunildhiman90.recipeappcmp.features.profile.data

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val myRecipeCount: Int,
    val favoriteRecipeCount: Int,
    val followers: Int,
)