package org.sunildhiman90.recipeappcmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform