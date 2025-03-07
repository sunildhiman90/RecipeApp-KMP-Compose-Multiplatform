package org.sunildhiman90.recipeappcmp.dbFactory

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.sunildhiman90.recipeappcmp.RecipeAppCmpAppDb
import java.util.Properties

actual class DatabaseFactory {
    actual suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(
            JdbcSqliteDriver.IN_MEMORY,
            properties = Properties().apply { put("foreign_keys", "true") }
        )
        RecipeAppCmpAppDb.Schema.awaitCreate(driver)
        return driver
    }
}