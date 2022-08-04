package com.example.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.ArrayList

@Parcelize
data class Instructions(
    @SerializedName("steps") val steps: ArrayList<Steps>
):Parcelable