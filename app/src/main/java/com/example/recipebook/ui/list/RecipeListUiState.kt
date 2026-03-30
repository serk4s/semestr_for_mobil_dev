package com.example.recipebook.ui.list

import com.example.recipebook.domain.model.Recipe

data class RecipeListUiState(
    val isLoading : Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val error: String? = null
)
