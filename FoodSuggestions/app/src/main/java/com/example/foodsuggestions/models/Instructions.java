package com.example.foodsuggestions.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Instructions {

    @SerializedName("steps")
    public ArrayList<Steps> steps;
}
