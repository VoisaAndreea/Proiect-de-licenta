package com.example.data.db

import androidx.room.Insert
import androidx.room.Query

interface RecipeDao {

    fun deleteAll()

    @Insert
    fun insertAll()

    @Query("SELECT * FROM ")
    fun getAll()
}