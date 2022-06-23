package com.example.foodsuggestions.main;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodsuggestions.data.Result;
import com.example.foodsuggestions.databinding.ActivitySearchIngredientsBinding;
import com.example.foodsuggestions.models.Ingredients;
import com.example.foodsuggestions.models.Recipe;
import com.example.foodsuggestions.viewmodels.RecipesViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchIngredientsActivity extends AppCompatActivity {
    ActivitySearchIngredientsBinding binding;
    List<String> listIngredient;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchIngredientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listIngredient = new ArrayList<String>();
        getListIngredients();
        Log.d("TAG", "LIST: " + listIngredient.size() + ": " + listIngredient);


//        arrayAdapter = new ArrayAdapter<String>(
//                this,
//                    android.R.layout.simple_list_item_multiple_choice,
//                    listIngredient
//            );
//        binding.idChoiceIngredients.setAdapter(arrayAdapter);


    }

    private void getListIngredients(){
        RecipesViewModel model = new ViewModelProvider(this).get(RecipesViewModel.class);
        model.getRecipes().observe(this, listOperationResponse -> {
            if (listOperationResponse instanceof Result.Success) {
                Result.Success<List<Recipe>> data = (Result.Success<List<Recipe>>) listOperationResponse;

                List<Recipe> countRecipe = data.getData();
                List<Ingredients> countIngredients;
                int count;

                for(int i = 0; i < countRecipe.size(); i++){
                    countIngredients = countRecipe.get(i).getExtendedIngredients();
                    count = 0;
                    for(int j = 0; j < countIngredients.size(); j++){
                        for(String k : listIngredient){
                            if (k.equals(countIngredients.get(j).getName())) {
                                count++;
                            }
                        }
                        if (count == 0) {
                            listIngredient.add(countIngredients.get(j).getName());
                        }
                    }
                }

            } else if (listOperationResponse instanceof Result.Error) {
                Toast.makeText(SearchIngredientsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }

            arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_multiple_choice,
                    listIngredient
            );
            binding.idChoiceIngredients.setAdapter(arrayAdapter);
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}