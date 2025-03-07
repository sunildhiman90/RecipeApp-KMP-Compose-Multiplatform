package org.sunildhiman90.recipeappcmp.features.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.P
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.imageResource
import org.koin.compose.viewmodel.koinViewModel
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

@Composable
fun SearchRoute(
    navigateToDetail: (Long) -> Unit,
    onBackPress: () -> Unit,
    searchViewModel: SearchViewModel = koinViewModel()
) {
    val screenState = searchViewModel.searchScreenUiState.collectAsState()
    val searchText = searchViewModel.searchText.collectAsState()

    SearchScreen(
        searchText = searchText.value,
        onSearchTextChanged = {
            searchViewModel.onSearchQueryChanged(it)
        },
        searchScreenState = screenState.value,
        onRecipeItemClicked = {
            navigateToDetail(it.id)
        },
        onBackPress = onBackPress
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    searchScreenState: SearchScreenState,
    onRecipeItemClicked: (RecipeItem) -> Unit,
    onBackPress: () -> Unit
) {

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            title = {
                Text(
                    text = "Search Recipes"
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
    }) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchScreenContent(
                searchText, onSearchTextChanged, searchScreenState, onRecipeItemClicked
            )
        }
    }

}

@Composable
fun SearchScreenContent(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    searchScreenState: SearchScreenState,
    onRecipeItemClicked: (RecipeItem) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).padding(bottom = 16.dp)
    ) {

        OutlinedTextField(
            shape = MaterialTheme.shapes.medium,
            value = searchText,
            onValueChange = {
                onSearchTextChanged(it)
            },
            placeholder = {
                Text(text = "Search Recipe Items...")
            },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.onPrimary
            ).fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                cursorColor = MaterialTheme.colorScheme.primaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            searchScreenState.error != null -> {
                Text(
                    text = "No Recipe Items found",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            searchScreenState.success && searchScreenState.results.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(searchScreenState.results, key = {
                        it.id
                    }) { item ->
                        SearchRecipeItem(item = item, onRecipeItemClicked = onRecipeItemClicked)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchRecipeItem(
    item: RecipeItem,
    onRecipeItemClicked: (RecipeItem) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth().clickable {
            onRecipeItemClicked(item)
        }.padding(16.dp)
    ) {

        AsyncImage(
            model = item.imageUrl,
            onError = {
                println("AsyncImage_onError=${it.result.throwable}")
            },
            modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Column {
            Text(
                textAlign = TextAlign.Start,
                text = item.title,
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
                        text = item.duration,
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
                        text = " ${item.rating}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
            }
        }
    }

}