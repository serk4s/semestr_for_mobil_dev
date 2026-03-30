package com.example.recipebook.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {

    @GET("recipes")
    suspend fun getRecipes(): RecipeResponseDto

    @GET("recipes/{id}")
    suspend fun getRecipe(@Path("id") id: Int): RecipeDto
}