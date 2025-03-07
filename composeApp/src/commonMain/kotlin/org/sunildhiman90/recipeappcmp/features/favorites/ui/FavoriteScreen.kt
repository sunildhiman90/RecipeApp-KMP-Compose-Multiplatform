package org.sunildhiman90.recipeappcmp.features.favorites.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem
import org.sunildhiman90.recipeappcmp.features.common.ui.components.ErrorContent
import org.sunildhiman90.recipeappcmp.features.common.ui.components.Loader

@Composable
fun FavoritesRoute(
    favoritesScreenViewModel: FavoritesScreenViewModel = koinViewModel(),
    navigateToDetail: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
) {

    val uiState = favoritesScreenViewModel.favoritesScreenUiState.collectAsState()

    LaunchedEffect(Unit) {
        favoritesScreenViewModel.getRecipesList()
    }

    FavoritesScreen(
        uiState = uiState.value,
        navigateToDetail = navigateToDetail
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    uiState: FavoritesScreenUiState,
    navigateToDetail: (Long) -> Unit
) {

    val recipes = uiState.itemsList
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Text("Favorites")
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        ) {
            HorizontalDivider(
                thickness = 0.3.dp,
                color = MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.5f
                )
            )

            when {
                uiState.itemsListIsLoading -> {
                    Loader()
                }

                uiState.itemsListError != null -> {
                    ErrorContent()
                }

                recipes != null -> {
                    FavoriteContent(
                        innerPadding = innerPadding,
                        recipes = recipes,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
        }


    }
}


@Composable
fun FavoriteContent(
    innerPadding: PaddingValues,
    recipes: List<RecipeItem>,
    navigateToDetail: (Long) -> Unit
) {
    val imageModifier =
        Modifier.width(140.dp).height(80.dp).clip(RoundedCornerShape(16.dp))

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(recipes, key = {
            it.id
        }) {
            FavoriteRecipeCard(
                recipe = it,
                imageModifier = imageModifier,
                navigateToDetail = navigateToDetail
            )
        }

    }

}

@Composable
private fun FavoriteRecipeCard(
    recipe: RecipeItem,
    modifier: Modifier = Modifier,
    imageModifier: Modifier,
    navigateToDetail: (Long) -> Unit
) {

    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.clickable {
            navigateToDetail(recipe.id)
        }
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {

            AsyncImage(
                model = recipe.imageUrl,
                onError = {
                    println("AsyncImage_onError=${it.result.throwable}")
                },
                modifier = imageModifier,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Column {
                Text(
                    textAlign = TextAlign.Start,
                    text = recipe.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {

                    Row(
                        modifier = Modifier.padding(end = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = recipe.duration,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        )
                        Text(
                            text = " ${recipe.rating}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp),
                        )
                    }
                }
            }
        }

    }

}