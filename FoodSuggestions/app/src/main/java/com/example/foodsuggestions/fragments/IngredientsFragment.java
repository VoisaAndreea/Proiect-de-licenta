package com.example.foodsuggestions.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.IngredientsAdapter;
import com.example.foodsuggestions.models.Ingredients;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class IngredientsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.idListIngredients);


        Bundle data = getArguments();
        ArrayList<Ingredients> gson =
                new Gson().fromJson(data.getString("ing"),
                new TypeToken<ArrayList<Ingredients>>(){}.getType());


        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(view.getContext(), gson);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(ingredientsAdapter);
        return view;
    }
}