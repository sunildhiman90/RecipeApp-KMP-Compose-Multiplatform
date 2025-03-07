package org.sunildhiman90.recipeappcmp.features.search.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sunildhiman90.recipeappcmp.features.app.data.Screen
import org.sunildhiman90.recipeappcmp.features.search.ui.SearchRoute

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(Screen.Search.route)
}

fun NavGraphBuilder.searchNavGraph(
    navigateToDetail: (Long) -> Unit,
    onBackPress: () -> Unit
) {

    composable(Screen.Search.route) {
        SearchRoute(
            navigateToDetail = navigateToDetail,
            onBackPress = onBackPress
        )
    }

}