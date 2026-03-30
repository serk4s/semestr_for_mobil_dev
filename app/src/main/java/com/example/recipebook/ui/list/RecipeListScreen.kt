package com.example.recipebook.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel,
    onRecipeClick: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val errorMessage = state.error

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe Book", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Back"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding).padding(top = 8.dp).fillMaxWidth()

        when {
            state.isLoading -> {
                Box(
                    modifier = contentModifier,
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Box(
                    modifier = contentModifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = errorMessage)
                }
            }

            else -> {
                LazyColumn(
                    modifier = contentModifier.padding(horizontal = 16.dp)
                ) {
                    items(state.recipes) { recipe ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { onRecipeClick(recipe.id) },
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onRecipeClick(recipe.id) }
                            ) {
                                AsyncImage(
                                    model = recipe.image,
                                    contentDescription = recipe.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                                Text(
                                    text = recipe.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(top = 4.dp, start = 8.dp)
                                )
                                Text(
                                    text = "${recipe.cuisine} • ${recipe.difficulty}",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
