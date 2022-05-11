package com.example.foodsuggestions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredients(
    @SerializedName("name")
    val name: String,

    @SerializedName("amount")
    val amount: String,


    @SerializedName("unit")
    val unit: String,
):Parcelable

