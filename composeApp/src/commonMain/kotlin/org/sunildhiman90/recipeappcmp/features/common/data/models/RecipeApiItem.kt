package org.sunildhiman90.recipeappcmp.features.common.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

//For api mapping
@Serializable
data class RecipeApiItem(
    @SerialName("dateModified")
    val dateModified: String? = null,
    @SerialName("idMeal")
    val idMeal: String? = null,
    @SerialName("strArea")
    val strArea: String? = null,
    @SerialName("strCategory")
    val strCategory: String? = null,
    @SerialName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String? = null,
    @SerialName("strDrinkAlternate")
    val strDrinkAlternate: String? = null,
    @SerialName("strImageSource")
    val strImageSource: String? = null,
    @SerialName("strIngredient1")
    val strIngredient1: String? = null,
    @SerialName("strIngredient10")
    val strIngredient10: String? = null,
    @SerialName("strIngredient11")
    val strIngredient11: String? = null,
    @SerialName("strIngredient12")
    val strIngredient12: String? = null,
    @SerialName("strIngredient13")
    val strIngredient13: String? = null,
    @SerialName("strIngredient14")
    val strIngredient14: String? = null,
    @SerialName("strIngredient15")
    val strIngredient15: String? = null,
    @SerialName("strIngredient16")
    val strIngredient16: String? = null,
    @SerialName("strIngredient17")
    val strIngredient17: String? = null,
    @SerialName("strIngredient18")
    val strIngredient18: String? = null,
    @SerialName("strIngredient19")
    val strIngredient19: String? = null,
    @SerialName("strIngredient2")
    val strIngredient2: String? = null,
    @SerialName("strIngredient20")
    val strIngredient20: String? = null,
    @SerialName("strIngredient3")
    val strIngredient3: String? = null,
    @SerialName("strIngredient4")
    val strIngredient4: String? = null,
    @SerialName("strIngredient5")
    val strIngredient5: String? = null,
    @SerialName("strIngredient6")
    val strIngredient6: String? = null,
    @SerialName("strIngredient7")
    val strIngredient7: String? = null,
    @SerialName("strIngredient8")
    val strIngredient8: String? = null,
    @SerialName("strIngredient9")
    val strIngredient9: String? = null,
    @SerialName("strInstructions")
    val strInstructions: String? = null,
    @SerialName("strMeal")
    val strMeal: String? = null,
    @SerialName("strMealThumb")
    val strMealThumb: String? = null,
    @SerialName("strMeasure1")
    val strMeasure1: String? = null,
    @SerialName("strMeasure10")
    val strMeasure10: String? = null,
    @SerialName("strMeasure11")
    val strMeasure11: String? = null,
    @SerialName("strMeasure12")
    val strMeasure12: String? = null,
    @SerialName("strMeasure13")
    val strMeasure13: String? = null,
    @SerialName("strMeasure14")
    val strMeasure14: String? = null,
    @SerialName("strMeasure15")
    val strMeasure15: String? = null,
    @SerialName("strMeasure16")
    val strMeasure16: String? = null,
    @SerialName("strMeasure17")
    val strMeasure17: String? = null,
    @SerialName("strMeasure18")
    val strMeasure18: String? = null,
    @SerialName("strMeasure19")
    val strMeasure19: String? = null,
    @SerialName("strMeasure2")
    val strMeasure2: String? = null,
    @SerialName("strMeasure20")
    val strMeasure20: String? = null,
    @SerialName("strMeasure3")
    val strMeasure3: String? = null,
    @SerialName("strMeasure4")
    val strMeasure4: String? = null,
    @SerialName("strMeasure5")
    val strMeasure5: String? = null,
    @SerialName("strMeasure6")
    val strMeasure6: String? = null,
    @SerialName("strMeasure7")
    val strMeasure7: String? = null,
    @SerialName("strMeasure8")
    val strMeasure8: String? = null,
    @SerialName("strMeasure9")
    val strMeasure9: String? = null,
    @SerialName("strSource")
    val strSource: String? = null,
    @SerialName("strTags")
    val strTags: String? = null,
    @SerialName("strYoutube")
    val strYoutube: String? = null
)

fun RecipeApiItem.toRecipe(): RecipeItem? {
    return if (idMeal != null)
        RecipeItem(
            id = idMeal.toLong(),
            title = strMeal ?: "",
            description = strMeal ?: "",
            category = strCategory ?: "",
            area = strArea ?: "",
            imageUrl = strMealThumb ?: "",
            youtubeLink = strYoutube ?: "",
            ingredients = listOfNotNull(
                "$strIngredient1:$strMeasure1",
                "$strIngredient2:$strMeasure2",
                "$strIngredient3:$strMeasure3",
                "$strIngredient4:$strMeasure4",
                "$strIngredient5:$strMeasure5",
                "$strIngredient6:$strMeasure6",
                "$strIngredient7:$strMeasure7",
                "$strIngredient8:$strMeasure8",
                "$strIngredient9:$strMeasure9",
                "$strIngredient10:$strMeasure10",
                "$strIngredient11:$strMeasure11",
                "$strIngredient12:$strMeasure12",
                "$strIngredient13:$strMeasure13",
                "$strIngredient14:$strMeasure14",
            ),
            instructions = strInstructions?.split(".")?.map {
                it.trim().replace("\r\n", "").captializeFirstWord()
            }?.filter { it.isNotEmpty() } ?: emptyList(),
            isFavorite = false,
            rating = 3
        ) else null
}

fun String.captializeFirstWord() = this.replaceFirstChar { it.uppercase() }