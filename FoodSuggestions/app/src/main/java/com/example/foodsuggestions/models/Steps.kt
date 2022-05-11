package com.example.foodsuggestions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Steps(
    @SerializedName("step") val step: String
): Parcelable