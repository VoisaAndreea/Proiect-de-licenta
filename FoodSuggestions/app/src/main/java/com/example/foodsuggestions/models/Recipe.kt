package com.example.foodsuggestions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.ArrayList

@Parcelize
data class Recipe(
        @SerializedName("title") val title: String,
        @SerializedName("image") val image: String,
        @SerializedName("summary") val summary: String,
        @SerializedName("sourceUrl") val sourceUrl: String,
        @SerializedName("dairyFree") val dairyFree: String,
        @SerializedName("glutenFree") val glutenFree: String,
        @SerializedName("dishTypes") val dishTypes : ArrayList<String>,
        @SerializedName("extendedIngredients") val extendedIngredients: ArrayList<Ingredients>,
        @SerializedName("analyzedInstructions") val analyzedInstructions: ArrayList<Instructions>
): Parcelable

