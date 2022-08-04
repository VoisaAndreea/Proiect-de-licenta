package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "recipes")
internal data class CacheRecipe(
    @PrimaryKey val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val sourceUrl: String,
    val dairyFree: String,
    val glutenFree: String,
    val dishTypes: String,
    val ingredients: String,
    val instructions: String
) {
    constructor(recipe: Recipe) : this(
        recipe.id,
        recipe.title,
        recipe.image,
        recipe.summary,
        recipe.sourceUrl,
        recipe.dairyFree,
        recipe.glutenFree,
        Gson().toJson(recipe.dishTypes),
        Gson().toJson(recipe.extendedIngredients),
        Gson().toJson(recipe.analyzedInstructions)
    )
}