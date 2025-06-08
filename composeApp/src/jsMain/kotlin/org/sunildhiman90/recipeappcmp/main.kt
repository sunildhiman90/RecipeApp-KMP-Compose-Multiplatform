package org.sunildhiman90.recipeappcmp

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToNavigation
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.read
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady
import kotlinx.browser.window
import org.sunildhiman90.recipeappcmp.di.initKoinJs
import org.sunildhiman90.recipeappcmp.features.app.data.Screen
import org.sunildhiman90.recipeappcmp.features.detail.navigation.RECIPE_ID_ARG

val koin = initKoinJs()

@OptIn(ExperimentalComposeUiApi::class, ExperimentalBrowserHistoryApi::class)
fun main() {
    onWasmReady {
        ComposeViewport(document.body!!) {

            val navController = rememberNavController()

            App(navController = navController)

            LaunchedEffect(Unit) {


                val initRoute = window.location.hash.substringAfter("#", "")
                when {
                    initRoute.startsWith("detail") -> {
                        val id = initRoute.substringAfter("detail/").toLong()
                        navController.navigate(
                            Screen.Detail.route.replace(
                                "$RECIPE_ID_ARG={$RECIPE_ID_ARG}",
                                "$RECIPE_ID_ARG=$id"
                            )
                        )
                    }

                    initRoute.startsWith("search") -> {
                        navController.navigate(Screen.Search.route)
                    }
                }

                window.bindToNavigation(navController) { entry ->
                    val route = entry.destination.route.orEmpty()

                    when {
                        route.startsWith(Screen.Tabs.route) -> {
                            "#"
                        }

                        route.startsWith(Screen.Detail.route) -> {
                            val args = entry.arguments
                            val id = args?.read {
                                getLongOrNull(RECIPE_ID_ARG)
                            }

                            //#detail/1
                            "#detail/${id}"
                        }

                        route.startsWith(Screen.Search.route) -> {
                            "#search"
                        }

                        else -> ""
                    }
                }
            }
        }
    }
}