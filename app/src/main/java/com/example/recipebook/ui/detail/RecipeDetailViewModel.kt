package com.example.recipebook.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val repository: RecipeRepository,
    private val recipeId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState

    init {
        loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            _uiState.value = RecipeDetailUiState(isLoading = true)
            try {
                val recipe = repository.getRecipeById(recipeId)
                _uiState.value = RecipeDetailUiState(recipe = recipe)
            } catch (e: Exception) {
                _uiState.value =
                    RecipeDetailUiState(error = "Ошибка загрузки рецепта")
            }
        }
    }
}