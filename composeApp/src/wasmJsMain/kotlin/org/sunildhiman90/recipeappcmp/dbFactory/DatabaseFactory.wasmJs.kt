package org.sunildhiman90.recipeappcmp.dbFactory

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.createDefaultWebWorkerDriver
import org.sunildhiman90.recipeappcmp.RecipeAppCmpAppDb

actual class DatabaseFactory {
    actual suspend fun createDriver(): SqlDriver {
        val driver = createDefaultWebWorkerDriver()
        driver.execute(null, "PRAGMA foreign_keys = ON;", 0)
        RecipeAppCmpAppDb.Schema.awaitCreate(driver)
        return driver
    }
}