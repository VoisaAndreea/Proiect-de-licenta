package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.model.CacheRecipe

@Dao
internal interface CacheRecipeDao {

    @Query("DELETE FROM recipes")
    fun deleteAll()

    @Insert
    fun insert(vararg cacheRecipe: CacheRecipe)

    @Query("SELECT * FROM recipes")
    fun getAll(): List<CacheRecipe>

    fun insertAll(cacheRecipeList: List<CacheRecipe>) {
        insert(*cacheRecipeList.toTypedArray())
    }
}