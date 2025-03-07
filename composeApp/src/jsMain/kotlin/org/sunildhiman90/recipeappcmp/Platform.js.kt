package org.sunildhiman90.recipeappcmp

class JsPlatform: Platform {
    override val name: String = "Web with Kotlin/Js"
}

actual fun getPlatform(): Platform = JsPlatform()