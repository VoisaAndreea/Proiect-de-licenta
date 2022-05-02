package com.example.foodsuggestions.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe {
    @SerializedName("title")
    public String title;

    @SerializedName("image")
    public String image;

    @SerializedName("summary")
    public String summary;

    @SerializedName("extendedIngredients")
    public ArrayList<Ingredients> extendedIngredients;

    @SerializedName("analyzedInstructions")
    public ArrayList<Instructions> analyzedInstructions;


   // Ingredients extendedIngredients;

}
