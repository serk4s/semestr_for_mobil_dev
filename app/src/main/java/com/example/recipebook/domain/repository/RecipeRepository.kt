package com.example.recipebook.domain.repository

import kotlinx.coroutines.flow.Flow
import com.example.recipebook.domain.model.Recipe
import com.example.recipebook.util.Result

interface RecipeRepository {
    fun getRecipes(): Flow<Result<List<Recipe>>>
    suspend fun getRecipeById(id: Int): Recipe
}