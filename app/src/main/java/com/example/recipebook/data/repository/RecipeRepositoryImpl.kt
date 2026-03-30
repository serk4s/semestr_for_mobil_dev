package com.example.recipebook.data.repository

import com.example.recipebook.data.local.RecipeDao
import com.example.recipebook.data.mapper.toDomain
import com.example.recipebook.data.mapper.toEntity
import com.example.recipebook.data.remote.RecipeApi
import com.example.recipebook.domain.model.Recipe
import com.example.recipebook.domain.repository.RecipeRepository
import com.example.recipebook.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(
    private val api: RecipeApi,
    private val dao: RecipeDao
) : RecipeRepository {

    override fun getRecipes(): Flow<Result<List<Recipe>>> = flow {
        emit(Result.Loading)

        val cached = dao.getAll() // Получаем закэшированные рецепты
        if (cached.isNotEmpty()) {
            emit(Result.Success(cached.map { it.toDomain() }))
        }
        // Загружаем свежие данные в кэш
        try {
            val remote = api.getRecipes() // Получаем свежие рецепты
            val entities = remote.recipes.map { it.toEntity() }
            dao.insertAll(entities) // Загружаем в кэш

            emit(Result.Success(remote.recipes.map { it.toDomain() }))
        } catch (e: Exception) {
            if (cached.isEmpty()) {
                emit(Result.Error("Ошибка загрузки данных"))
            }
        }
    }

    override suspend fun getRecipeById(id: Int): Recipe {
        return try {
            api.getRecipe(id).toDomain()
        } catch (e: Exception) {
            dao.getById(id)?.toDomain()
                ?: throw e
        }
    }
}