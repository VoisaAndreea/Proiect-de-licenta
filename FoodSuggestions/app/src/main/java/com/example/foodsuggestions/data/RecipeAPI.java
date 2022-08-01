package com.example.foodsuggestions.data;

import com.example.foodsuggestions.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface RecipeAPI {

    @GET("getRecipes")
    Call<List<Recipe>> getRecipes();
}
