package com.example.recipebook.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.recipebook.domain.repository.RecipeRepository
import com.example.recipebook.util.Result

class RecipeListViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeListUiState())
    val uiState: StateFlow<RecipeListUiState> = _uiState

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            repository.getRecipes().collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> _uiState.value.copy(isLoading = true)

                    is Result.Success -> RecipeListUiState(recipes = result.data)

                    is Result.Error -> RecipeListUiState(error = result.message)
                }
            }
        }
    }
}