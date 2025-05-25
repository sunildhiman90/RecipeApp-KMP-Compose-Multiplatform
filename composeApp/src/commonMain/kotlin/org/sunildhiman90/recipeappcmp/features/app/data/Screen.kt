package org.sunildhiman90.recipeappcmp.features.app.data

import kotlinx.serialization.SerialName
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.sunildhiman90.recipeappcmp.features.detail.navigation.RECIPE_ID_ARG
import recipeapp_cmp.composeapp.generated.resources.Res
import recipeapp_cmp.composeapp.generated.resources.bookmark_selected
import recipeapp_cmp.composeapp.generated.resources.bookmark_unselected
import recipeapp_cmp.composeapp.generated.resources.detail
import recipeapp_cmp.composeapp.generated.resources.favorites
import recipeapp_cmp.composeapp.generated.resources.home
import recipeapp_cmp.composeapp.generated.resources.home_selected
import recipeapp_cmp.composeapp.generated.resources.home_unselected
import recipeapp_cmp.composeapp.generated.resources.profile
import recipeapp_cmp.composeapp.generated.resources.profile_selected
import recipeapp_cmp.composeapp.generated.resources.profile_unselected
import recipeapp_cmp.composeapp.generated.resources.search
import recipeapp_cmp.composeapp.generated.resources.tabs


sealed class Screen(
    val route: String,
    val resourceId: StringResource,
    val selectedIcon: DrawableResource? = null,
    val unselectedIcon: DrawableResource? = null,
) {

    data object Search : Screen("search", Res.string.search)
    data object Tabs : Screen("tabs", Res.string.tabs)
    data object Detail : Screen("detail?$RECIPE_ID_ARG={$RECIPE_ID_ARG}", Res.string.detail)

    data object Home : Screen("home",
        Res.string.home,
        selectedIcon = Res.drawable.home_selected,
        unselectedIcon = Res.drawable.home_unselected
    )

    data object Favorites : Screen("favorites",
        Res.string.favorites,
        selectedIcon = Res.drawable.bookmark_selected,
        unselectedIcon = Res.drawable.bookmark_unselected
    )

    data object Profile : Screen("profile",
        Res.string.profile,
        selectedIcon = Res.drawable.profile_selected,
        unselectedIcon = Res.drawable.profile_unselected
    )
}