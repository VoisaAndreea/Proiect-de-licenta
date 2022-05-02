package com.example.foodsuggestions.models;

import com.google.gson.annotations.SerializedName;

public class Ingredients {
    @SerializedName("name")
    public String name;

    @SerializedName("amount")
    public String amount;

    @SerializedName("unit")
    public String unit;


//    public Ingredients(String amound, String unit, String name) {
//        this.amound = amound;
//        this.unit = unit;
//        this.name = name;
//    }
//
//    public String getAmound() {
//        return amound;
//    }
//
//    public void setAmound(String amound) {
//        this.amound = amound;
//    }
//
//    public String getUnit() {
//        return unit;
//    }
//
//    public void setUnit(String unit) {
//        this.unit = unit;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}
