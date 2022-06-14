package com.example.foodsuggestions.main

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.foodsuggestions.R

/**
 * Custom view to display a  home category item.
 */
class HomeCategoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        val cardView = inflate(context, R.layout.home_category_view, null)
        addView(cardView)

        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.HomeCategoryView, 0, 0
        )

        val title = a.getString(R.styleable.HomeCategoryView_title)
        val drawable = a.getDrawable(R.styleable.HomeCategoryView_image)

        cardView.findViewById<TextView>(R.id.categoryTitle).text = title
        cardView.findViewById<ImageView>(R.id.categoryImage).setImageDrawable(drawable)

        a.recycle()
    }
}