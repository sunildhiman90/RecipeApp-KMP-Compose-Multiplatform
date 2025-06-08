package org.sunildhiman90.recipeappcmp.features.common.data.database.daos

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.sunildhiman90.recipeappcmp.RecipeAppCmpAppDb
import org.sunildhiman90.recipeappcmp.features.common.data.database.DbHelper
import org.sunildhiman90.recipeappcmp.features.common.data.database.recipeEntityMapper
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

class FavoriteRecipeDao(
    private val dbHelper: DbHelper
) {

    suspend fun addFavorite(recipeId: Long) {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        dbHelper.withDatabase { database ->
            //TODO, upsertFavorite is not working in sqldelight 2.1.0, due to this issue: https://github.com/sqldelight/sqldelight/issues/5753
            // SO currently we have issue in upsert statement, for workaround of running, i added SELECT 1 in the end of statement,.
            // But now its now executing query for upsert
            /*database.favoriteRecipeQueries.upsertFavorite(
                recipe_id = recipeId, added_at = currentDateTime.toString()
            )*/

            //As of now using this workaround
            if (isFavorite(database, recipeId)) {
                //Already favorite, remove it
                deleteFavoriteRecipeById(
                    database = database,
                    recipeId = recipeId
                )
            } else {
                //Not favorite, add it
                addFavorite(database, recipeId, currentDateTime.toString())//database.favoriteRecipeQueries.insertFavorite(
            }
        }
    }

    private suspend fun addFavorite(database: RecipeAppCmpAppDb, recipeId: Long, currentDateTime: String) {
        database.favoriteRecipeQueries.insertFavorite(
            recipe_id = recipeId, added_at = currentDateTime
        )
    }

    private suspend fun deleteFavoriteRecipeById(database: RecipeAppCmpAppDb, recipeId: Long) {
        database.favoriteRecipeQueries.deleteFavoriteRecipeById(
            recipe_id = recipeId
        )
    }


    suspend fun removeFavorite(recipeId: Long) {
        dbHelper.withDatabase { database ->
            removeFavorite(database, recipeId)
        }
    }

    private suspend fun removeFavorite(database: RecipeAppCmpAppDb, recipeId: Long) {
        database.favoriteRecipeQueries.deleteFavoriteRecipeById(
            recipe_id = recipeId
        )
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

    private suspend fun isFavorite(database: RecipeAppCmpAppDb, recipeId: Long): Boolean {
        return database.favoriteRecipeQueries.selectFavoriteByRecipeId(recipeId).awaitAsOneOrNull() != null
    }


}