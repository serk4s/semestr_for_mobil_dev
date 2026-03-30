package com.example.recipebook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val ingredientsJson: String,
    val instructionsJson: String,
    val prepTimeMinutes: Int,
    val cookTimeMinutes: Int,
    val servings: Int,
    val difficulty: String,
    val cuisine: String,
    val caloriesPerServing: Int,
    val tagsJson: String,
    val image: String,
    val rating: Double,
    val reviewCount: Int,
    val mealTypeJson: String
)