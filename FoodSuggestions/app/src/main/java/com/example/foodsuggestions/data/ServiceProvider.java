package com.example.foodsuggestions.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvider {
    private RecipeAPI recipeAPI;

    private ServiceProvider() {
    }

    private static ServiceProvider instance;

    public static ServiceProvider getInstance(){
        if(instance == null){
            instance = new ServiceProvider();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://app-foodrecipes.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                .build();

            instance.recipeAPI= retrofit.create(RecipeAPI.class);
        }
        return instance;
    }

    public RecipeAPI getRecipeAPI(){
        return recipeAPI;
    }
}
