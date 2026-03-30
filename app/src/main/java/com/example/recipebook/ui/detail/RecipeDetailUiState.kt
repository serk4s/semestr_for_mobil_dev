package com.example.recipebook.ui.detail

import com.example.recipebook.domain.model.Recipe

data class RecipeDetailUiState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null,
    val error: String? = null
)
