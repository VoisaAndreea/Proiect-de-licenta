package com.example.foodsuggestions.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodsuggestions.data.RecipesRepository
import com.example.foodsuggestions.data.RecipesRepository.RecipesCallback
import com.example.foodsuggestions.data.Result
import com.example.foodsuggestions.models.Recipe

class RecipesViewModel : ViewModel() {

    private val _recipes = MutableLiveData<Result<List<Recipe>>>()
    val recipes: LiveData<Result<List<Recipe>>>
        get() = _recipes

    init {
        RecipesRepository.getInstance().getRecipes(null, object : RecipesCallback {
            override fun onRecipesReceived(recipes: List<Recipe>) {
                _recipes.postValue(Result.Success(recipes))
            }

            override fun onFailure(t: Throwable) {
                _recipes.postValue(Result.Error("Something went wrong"))
            }
        })
    }
}