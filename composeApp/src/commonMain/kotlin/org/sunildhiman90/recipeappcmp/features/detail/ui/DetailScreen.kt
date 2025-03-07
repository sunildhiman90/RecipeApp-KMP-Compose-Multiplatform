package org.sunildhiman90.recipeappcmp.features.detail.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.M
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.ktor.http.HttpMessage
import org.koin.compose.viewmodel.koinViewModel
import org.sunildhiman90.recipeappcmp.features.common.data.models.captializeFirstWord
import org.sunildhiman90.recipeappcmp.features.common.domain.entities.RecipeItem

@Composable
fun DetailRoute(
    recipeId: Long,
    onBackClick: () -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    detailViewModel: RecipeDetailViewModel = koinViewModel()
) {

    LaunchedEffect(Unit) {
        detailViewModel.getRecipeDetail(recipeId)
    }

    val detailUiState = detailViewModel.detailUiState.collectAsState()

    val uriHandler = LocalUriHandler.current

    val onWatchVideoClick: (String) -> Unit = { link ->
        if (link.isNotEmpty()) {
            uriHandler.openUri(link)
        }
    }


    var showAlertDialog by remember {
        mutableStateOf(false)
    }

    val onSaveClick: (RecipeItem) -> Unit = {
        if (!isUserLoggedIn()) {
            showAlertDialog = true
        } else {
            detailViewModel.updateIsFavorite(recipeId = it.id, isAdding = !it.isFavorite)
        }
    }

    val updateIsFavUiState = detailViewModel.updateIsFavUiState.collectAsState()

    if (showAlertDialog) {

        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {
                showAlertDialog = false
            },
            title = {
                Text(
                    text = "Update Favorites",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text(
                    text = "Login to Add/Remove Favorites",
                )
            },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors().copy(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ), onClick = {
                    showAlertDialog = false
                    openLoginBottomSheet {
                        detailUiState.value.recipesDetail?.let {
                            detailViewModel.updateIsFavorite(
                                recipeId = it.id,
                                isAdding = !it.isFavorite
                            )
                        }
                    }
                }) {
                    Text("Login", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                OutlinedButton(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                    colors = ButtonDefaults.buttonColors().copy(
                    containerColor = MaterialTheme.colorScheme.background
                ), onClick = {
                    showAlertDialog = false
                }) {
                    Text("Cancel", color = MaterialTheme.colorScheme.primaryContainer)
                }
            }
        )

    }

    DetailScreen(
        uiState = detailUiState.value,
        updateIsFavUiState = updateIsFavUiState.value,
        onBackClick = onBackClick,
        onWatchVideoClick = onWatchVideoClick,
        onSaveClick = onSaveClick
    )

}


@Composable
fun DetailScreen(
    uiState: RecipeDetailUiState,
    onBackClick: () -> Unit,
    onWatchVideoClick: (String) -> Unit,
    updateIsFavUiState: RecipeDetailUpdateIsFavUiState,
    onSaveClick: (RecipeItem) -> Unit,
) {
    Scaffold(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        ) {

            when {
                uiState.recipesDetailIsLoading -> {
                    LoadingScreen()
                }

                uiState.recipesDetailError != null -> {
                    ErrorScreen(uiState.recipesDetailError, onBackClick)
                }

                uiState.recipesDetail != null -> {
                    RecipeDetailContent(
                        uiState.recipesDetail,
                        onBackClick = onBackClick,
                        onWatchVideoClick = onWatchVideoClick,
                        onSaveClick = onSaveClick
                    )
                }
            }


        }
    }

}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(errorMessage: String, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text("Go Back")
        }

    }
}

@Composable
fun RecipeDetailContent(
    recipeItem: RecipeItem,
    onBackClick: () -> Unit,
    onWatchVideoClick: (String) -> Unit,
    onSaveClick: (RecipeItem) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        RecipeMainContent(
            recipeItem,
            onWatchVideoClick
        )

        //Back and Save Button UI

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(WindowInsets.statusBars.asPaddingValues())
                .padding(
                    vertical = 32.dp
                ).padding(horizontal = 16.dp)
                .align(Alignment.TopCenter)
        ) {

            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(horizontal = 8.dp).size(30.dp).background(
                    color = MaterialTheme.colorScheme.background.copy(
                        alpha = 0.8f
                    ),
                    shape = CircleShape
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }


            IconButton(
                onClick = {
                    onSaveClick(recipeItem)
                },
                modifier = Modifier.padding(horizontal = 8.dp).size(30.dp).background(
                    color = MaterialTheme.colorScheme.background.copy(
                        alpha = 0.8f
                    ),
                    shape = CircleShape
                )
            ) {
                Icon(
                    imageVector = if (recipeItem.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }


        }

    }
}

@Composable
fun RecipeMainContent(
    recipeItem: RecipeItem,
    onWatchVideoClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {

        //Image
        AsyncImage(
            model = recipeItem.imageUrl,
            contentDescription = recipeItem.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(250.dp).clip(
                RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp,
                )
            )
        )

        //Other Details
        RecipeDetails(recipeItem)

        //Desc
        Column(
            modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
        ) {
            Text(
                text = "Description", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(text = recipeItem.description, style = MaterialTheme.typography.bodySmall)
        }

        //Ingredients
        IngredientsList(
            ingredients = recipeItem.ingredients.map {
                val item = it.split(":")
                if (item.isNotEmpty() && item.size == 2) {
                    Pair(item[0].trim().captializeFirstWord(), item[1].trim())
                } else {
                    Pair("", "")
                }
            }.filter {
                it.first.isNotEmpty() && it.second.isNotEmpty()
            }.filterNot {
                it.first.contains("null") || it.second.contains("null")
            }
        )

        Instructions(instructions = recipeItem.instructions)

        WatchVideoButton(
            youtubeLink = recipeItem.youtubeLink,
            onWatchVideoClick = onWatchVideoClick
        )

    }

}


@Composable
fun RecipeDetails(
    recipeItem: RecipeItem,
) {

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = recipeItem.title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.size(16.dp),
                contentDescription = null,
                imageVector = Icons.Default.Schedule
            )
            Text(
                text = recipeItem.duration,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                modifier = Modifier.size(16.dp),
                contentDescription = null,
                imageVector = Icons.Default.Star,
                tint = MaterialTheme.colorScheme.primaryContainer.copy(
                    alpha = 0.5f
                )
            )
            Text(
                text = "${recipeItem.rating} stars",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = recipeItem.difficulty,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

    }

}

@Composable
fun IngredientsList(
    ingredients: List<Pair<String, String>>,
) {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        ingredients.forEach {
            IngredientsItem(name = it.first, quantity = it.second)
        }
    }

}

@Composable
fun IngredientsItem(
    name: String,
    quantity: String
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = name, style = MaterialTheme.typography.bodySmall)
        Text(text = quantity, style = MaterialTheme.typography.bodySmall)
    }

}

@Composable
fun Instructions(
    instructions: List<String>,
) {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        Text(
            text = "Instructions",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        instructions.forEachIndexed { index, value ->
            Text(text = "${index + 1} $value", style = MaterialTheme.typography.bodySmall)
        }
    }

}

@Composable
fun WatchVideoButton(
    youtubeLink: String,
    onWatchVideoClick: (String) -> Unit
) {
    Button(
        onClick = {
            onWatchVideoClick(youtubeLink)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Watch",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            "Watch Video", style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }

}
