package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.CacheRecipe

@Database(entities = [CacheRecipe::class], version = 1)
internal abstract class AppDatabase: RoomDatabase() {
    abstract fun cacheRecipeDao(): CacheRecipeDao
}