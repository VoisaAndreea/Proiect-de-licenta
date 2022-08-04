package com.example.data.remote;


import com.example.data.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeAPI {

    @GET("getRecipes")
    Call<List<Recipe>> getRecipes();
}
