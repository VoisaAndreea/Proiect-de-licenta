package com.example.foodsuggestions

import android.app.Application
import com.example.data.RecipesRepository

class FoodApp: Application() {

    override fun onCreate() {
        super.onCreate()
        RecipesRepository.getInstance(applicationContext)
    }
}