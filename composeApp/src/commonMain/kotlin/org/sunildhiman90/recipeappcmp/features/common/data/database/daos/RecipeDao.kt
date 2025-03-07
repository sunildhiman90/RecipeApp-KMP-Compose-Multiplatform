package org.sunildhiman90.recipeappcmp.features.common.data.database.daos

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import org.sunildhiman90.recipeappcmp.features.common.data.database.DbHelper
import org.sunildhiman90.recipeappcmp.features.common.data.database.recipeEntityMapper
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

class RecipeDao(
    private val dbHelper: DbHelper
) {

    suspend fun insertRecipe(recipeItem: RecipeItem) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.insertRecipe(
                recipeItem.id,
                recipeItem.title,
                recipeItem.description,
                recipeItem.category,
                recipeItem.area,
                recipeItem.imageUrl,
                recipeItem.youtubeLink,
                recipeItem.ingredients,
                recipeItem.instructions,
                if (recipeItem.isFavorite) 1 else 0,
                recipeItem.rating,
                recipeItem.duration,
                recipeItem.difficulty
            )
        }
    }

    suspend fun updateRecipe(recipeItem: RecipeItem) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.updateRecipe(
                recipeItem.title,
                recipeItem.description,
                recipeItem.category,
                recipeItem.area,
                recipeItem.imageUrl,
                recipeItem.youtubeLink,
                recipeItem.ingredients,
                recipeItem.instructions,
                if (recipeItem.isFavorite) 1 else 0,
                recipeItem.rating,
                recipeItem.duration,
                recipeItem.difficulty,
                recipeItem.id
            )
        }
    }

    suspend fun insertRecipesBulk(recipes: List<RecipeItem>) {
        dbHelper.withDatabase { database ->
            recipes.forEach { recipeItem ->
                database.recipeEntityQueries.insertRecipe(
                    recipeItem.id,
                    recipeItem.title,
                    recipeItem.description,
                    recipeItem.category,
                    recipeItem.area,
                    recipeItem.imageUrl,
                    recipeItem.youtubeLink,
                    recipeItem.ingredients,
                    recipeItem.instructions,
                    if (recipeItem.isFavorite) 1 else 0,
                    recipeItem.rating,
                    recipeItem.duration,
                    recipeItem.difficulty
                )
            }
        }
    }

    suspend fun upsertRecipesBulk(recipes: List<RecipeItem>) {
        dbHelper.withDatabase { database ->
            recipes.forEach { recipeItem ->
                database.recipeEntityQueries.upsertRecipe(
                    recipeItem.title,
                    recipeItem.description,
                    recipeItem.category,
                    recipeItem.area,
                    recipeItem.imageUrl,
                    recipeItem.youtubeLink,
                    recipeItem.ingredients,
                    recipeItem.instructions,
                    if (recipeItem.isFavorite) 1 else 0,
                    recipeItem.rating,
                    recipeItem.duration,
                    recipeItem.difficulty,
                    recipeItem.id,
                )
            }
        }
    }

    suspend fun getAllRecipes(): List<RecipeItem> {

        return dbHelper.withDatabase { database ->
            database.recipeEntityQueries.selectAllRecipes().awaitAsList().map {
                recipeEntityMapper(it)
            }
        }

    }

    suspend fun getRecipeById(id: Long): RecipeItem? {

        return dbHelper.withDatabase { database ->
            database.recipeEntityQueries.selectRecipeById(id).awaitAsOneOrNull()?.let {
                recipeEntityMapper(it)
            }
        }

    }

    suspend fun deleteRecipeById(id: Long) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.detelteRecipeById(id)
        }
    }


    suspend fun searchRecipesByText(text: String): List<RecipeItem> {
        return dbHelper.withDatabase { database ->
            database.recipeEntityQueries.searchRecipeByText(text).awaitAsList().map {
                recipeEntityMapper(it)
            }
        }
    }

}