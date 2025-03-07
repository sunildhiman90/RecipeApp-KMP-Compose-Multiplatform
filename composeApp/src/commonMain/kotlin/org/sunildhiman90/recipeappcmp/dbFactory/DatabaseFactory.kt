package org.sunildhiman90.recipeappcmp.dbFactory

import app.cash.sqldelight.db.SqlDriver

const val DB_FILE_NAME = "cmpapp.db"

expect class DatabaseFactory {
    suspend fun createDriver(): SqlDriver
}