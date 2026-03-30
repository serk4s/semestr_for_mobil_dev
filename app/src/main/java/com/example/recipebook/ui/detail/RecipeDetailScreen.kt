package com.example.recipebook.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val recipe = state.recipe

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe?.name ?: "Recipe") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding).fillMaxSize()

        when {
            state.isLoading -> {
                Box(
                    modifier = contentModifier,
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = contentModifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.error!!)
                }
            }

            recipe != null -> {
                LazyColumn(
                    modifier = contentModifier.padding(16.dp)
                ) {
                    item {
                        AsyncImage(
                            model = recipe.image,
                            contentDescription = recipe.name,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    item {
                        Text("Cuisine: ${recipe.cuisine}")
                        Text("Difficulty: ${recipe.difficulty}")
                        Text("Servings: ${recipe.servings}")
                        Text("Calories: ${recipe.caloriesPerServing}")
                        Spacer(Modifier.height(12.dp))
                    }

                    item {
                        Text("Ingredients", style = MaterialTheme.typography.titleMedium)
                    }

                    items(recipe.ingredients) {
                        Text("• $it")
                    }

                    item {
                        Spacer(Modifier.height(12.dp))
                        Text("Instructions", style = MaterialTheme.typography.titleMedium)
                    }

                    itemsIndexed(recipe.instructions) { index, step ->
                        Text("${index + 1}. $step")
                    }

                    item {
                        Spacer(Modifier.height(12.dp))
                        Text("Rating: ${recipe.rating} (${recipe.reviewCount} reviews)")
                        Text("Tags: ${recipe.tags.joinToString()}")
                        Text("Meal type: ${recipe.mealType.joinToString()}")
                    }
                }
            }
        }
    }
}