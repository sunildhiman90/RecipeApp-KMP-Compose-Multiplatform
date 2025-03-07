package org.sunildhiman90.recipeappcmp.features.common.data.database

import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem
import orgsunildhiman90recipeappcmp.Recipe

fun recipeEntityMapper(recipe: Recipe)  = RecipeItem(
    recipe.id,
    recipe.title,
    recipe.description,
    recipe.category,
    recipe.area,
    recipe.imageUrl,
    recipe.youtubeLink,
    recipe.ingredients,
    recipe.instructions,
    recipe.isFavorite == 1L,
    recipe.rating,
    recipe.duration ?: "20 Mins",
    recipe.difficulty ?: "Easy"
)