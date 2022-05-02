package com.example.foodsuggestions.retrofit;

import com.example.foodsuggestions.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeAPI {

    @GET("getRecipes")
    Call<List<Recipe>> getRecipes();
}
