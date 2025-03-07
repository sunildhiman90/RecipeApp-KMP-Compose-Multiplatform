package org.sunildhiman90.recipeappcmp.features.common.data.database.daos

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.sunildhiman90.recipeappcmp.features.common.data.database.DbHelper
import org.sunildhiman90.recipeappcmp.features.common.data.database.recipeEntityMapper
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

class FavoriteRecipeDao(
    private val dbHelper: DbHelper
) {

    suspend fun addFavorite(recipeId: Long) {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.upsertFavorite(
               recipe_id = recipeId, added_at = currentDateTime.toString()
            )
        }
    }

    suspend fun removeFavorite(recipeId: Long) {
        dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.detelteFavoriteByRecipeById(
                recipe_id = recipeId
            )
        }
    }

    suspend fun getAllFavoriteRecipes(): List<RecipeItem> {
        return dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.selectAllFavoritesRecipes().awaitAsList().map {
                recipeEntityMapper(it)
            }
        }
    }

    suspend fun isFavorite(recipeId: Long): Boolean {
        return dbHelper.withDatabase { database ->
            database.favoriteRecipeQueries.selectFavoriteByRecipeId(recipeId).awaitAsOneOrNull() != null
        }
    }


}