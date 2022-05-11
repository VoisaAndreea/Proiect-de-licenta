package com.example.foodsuggestions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.example.foodsuggestions.models.Steps
import kotlinx.parcelize.Parcelize
import java.util.ArrayList

@Parcelize
data class Instructions(
    @SerializedName("steps") val steps: ArrayList<Steps>
):Parcelable