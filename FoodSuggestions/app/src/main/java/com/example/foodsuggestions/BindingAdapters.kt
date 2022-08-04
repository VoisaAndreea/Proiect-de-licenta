package com.example.foodsuggestions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("loadImage")
fun loadImage(image: ImageView, url: String) {
    Picasso.with(image.context).load(url).into(image)
}