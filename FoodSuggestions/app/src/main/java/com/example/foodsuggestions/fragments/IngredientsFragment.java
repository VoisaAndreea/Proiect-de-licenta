package com.example.foodsuggestions.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.IngredientsAdapter;
import com.example.foodsuggestions.models.Ingredients;

import java.util.ArrayList;
import java.util.List;


public class IngredientsFragment extends Fragment {
    private static final String INGREDIENTS_KEY = "INGREDIENTS_KEY";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.idListIngredients);


        Bundle data = getArguments();

        if(!data.isEmpty()) {
            IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(view.getContext(), data.getParcelableArrayList(INGREDIENTS_KEY));
            RecyclerView.LayoutManager manager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(ingredientsAdapter);
        }
        else{
            Toast.makeText(view.getContext(), "DATA IS EMPTY", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public static IngredientsFragment newInstance(List<Ingredients> ingredients){
        Bundle args = new Bundle();
        args.putParcelableArrayList(INGREDIENTS_KEY, new ArrayList<>(ingredients));
        IngredientsFragment fragment = new IngredientsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}