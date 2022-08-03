package com.example.data.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.ArrayList
import com.google.gson.reflect.TypeToken




@Parcelize
data class Recipe(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("image") val image: String,
        @SerializedName("summary") val summary: String,
        @SerializedName("sourceUrl") val sourceUrl: String,
        @SerializedName("dairyFree") val dairyFree: String,
        @SerializedName("glutenFree") val glutenFree: String,
        @SerializedName("dishTypes") val dishTypes : ArrayList<String>,
        @SerializedName("extendedIngredients") val extendedIngredients: ArrayList<Ingredients>,
        @SerializedName("analyzedInstructions") val analyzedInstructions: ArrayList<Instructions>
): Parcelable {

        internal constructor(recipe: CacheRecipe): this(
                recipe.id,
                recipe.title,
                recipe.image,
                recipe.summary,
                recipe.sourceUrl,
                recipe.dairyFree,
                recipe.glutenFree,
                Gson().fromJson(recipe.dishTypes,
                        object : TypeToken<ArrayList<String?>?>() {}.type) ,
                Gson().fromJson(recipe.ingredients,
                        object : TypeToken<ArrayList<Ingredients?>?>() {}.type),
                Gson().fromJson(recipe.instructions,
                        object : TypeToken<ArrayList<Instructions?>?>() {}.type)
        )
}

