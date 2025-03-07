package org.sunildhiman90.recipeappcmp.features.common.data.database

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.sunildhiman90.recipeappcmp.RecipeAppCmpAppDb
import org.sunildhiman90.recipeappcmp.dbFactory.DatabaseFactory

class DbHelper(
    private val driverFactory: DatabaseFactory
) {

    private var db: RecipeAppCmpAppDb? = null
    private val mutex = Mutex()

    suspend fun <Result : Any?> withDatabase(block: suspend (RecipeAppCmpAppDb) -> Result) =
        mutex.withLock {
            if (db == null) {
                db = createDb(driverFactory)
            }

            return@withLock block(db!!)
        }

    private suspend fun createDb(driverFactory: DatabaseFactory): RecipeAppCmpAppDb {
        return RecipeAppCmpAppDb(
            driver = driverFactory.createDriver(),
            RecipeAdapter = orgsunildhiman90recipeappcmp.Recipe.Adapter(
                ingredientsAdapter = listOfStringsAdapter,
                instructionsAdapter = listOfStringsAdapter
            )
        )
    }
}