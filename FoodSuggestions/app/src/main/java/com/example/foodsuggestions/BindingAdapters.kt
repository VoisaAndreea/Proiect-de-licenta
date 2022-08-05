package com.example.foodsuggestions

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("loadImage")
fun loadImage(image: ImageView, url: String) {
    Picasso.with(image.context).load(url).into(image)
}

@BindingAdapter("isVisible")
fun isVisible(view: View, viewIsVisible: Boolean) {
    view.isVisible = viewIsVisible
}