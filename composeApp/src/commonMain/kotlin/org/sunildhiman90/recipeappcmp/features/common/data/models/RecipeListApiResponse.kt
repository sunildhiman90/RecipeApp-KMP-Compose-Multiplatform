package org.sunildhiman90.recipeappcmp.features.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeListApiResponse(
    @SerialName("meals")
    val meals: List<RecipeApiItem>
)