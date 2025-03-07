package org.sunildhiman90.recipeappcmp.features.feed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sunildhiman90.recipeappcmp.features.app.data.Screen
import org.sunildhiman90.recipeappcmp.features.feed.ui.FeedRoute

fun NavController.navigateToFeed(navOptions: NavOptions? = null) {
    navigate(Screen.Home.route)
}

fun NavGraphBuilder.feedNavGraph(
    navigateToDetail: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    navigateToSearch: () -> Unit,
) {

    composable(Screen.Home.route) {
        FeedRoute(
            navigateToDetail = navigateToDetail,
            navigateToSearch = navigateToSearch
        )
    }

}