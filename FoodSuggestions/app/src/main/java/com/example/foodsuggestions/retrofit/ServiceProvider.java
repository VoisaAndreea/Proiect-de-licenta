package com.example.foodsuggestions.retrofit;

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
                .baseUrl("http://192.168.0.124:8045/")
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
