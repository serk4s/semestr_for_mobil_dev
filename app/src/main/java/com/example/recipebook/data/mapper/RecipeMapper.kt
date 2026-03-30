package com.example.recipebook.data.mapper

import com.example.recipebook.data.local.RecipeEntity
import com.example.recipebook.data.remote.RecipeDto
import com.example.recipebook.domain.model.Recipe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val json = Json { encodeDefaults = true }

fun RecipeDto.toEntity() = RecipeEntity(
    id = id,
    name = name,
    ingredientsJson = json.encodeToString(ingredients),
    instructionsJson = json.encodeToString(instructions),
    prepTimeMinutes = prepTimeMinutes,
    cookTimeMinutes = cookTimeMinutes,
    servings = servings,
    difficulty = difficulty,
    cuisine = cuisine,
    caloriesPerServing = caloriesPerServing,
    tagsJson = json.encodeToString(tags),
    image = image,
    rating = rating,
    reviewCount = reviewCount,
    mealTypeJson = json.encodeToString(mealType)
)

fun RecipeEntity.toDomain() = Recipe(
    id = id,
    name = name,
    ingredients = json.decodeFromString(ingredientsJson),
    instructions = json.decodeFromString(instructionsJson),
    prepTimeMinutes = prepTimeMinutes,
    cookTimeMinutes = cookTimeMinutes,
    servings = servings,
    difficulty = difficulty,
    cuisine = cuisine,
    caloriesPerServing = caloriesPerServing,
    tags = json.decodeFromString(tagsJson),
    image = image,
    rating = rating,
    reviewCount = reviewCount,
    mealType = json.decodeFromString(mealTypeJson)
)

fun RecipeDto.toDomain() = Recipe(
    id = id,
    name = name,
    ingredients = ingredients,
    instructions = instructions,
    prepTimeMinutes = prepTimeMinutes,
    cookTimeMinutes = cookTimeMinutes,
    servings = servings,
    difficulty = difficulty,
    cuisine = cuisine,
    caloriesPerServing = caloriesPerServing,
    tags = tags,
    image = image,
    rating = rating,
    reviewCount = reviewCount,
    mealType = mealType
)