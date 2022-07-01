package com.example.foodsuggestions.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodsuggestions.data.Result;
import com.example.foodsuggestions.databinding.ActivitySearchIngredientsBinding;
import com.example.foodsuggestions.models.Ingredients;
import com.example.foodsuggestions.models.Recipe;
import com.example.foodsuggestions.viewmodels.RecipesViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchIngredientsActivity extends AppCompatActivity {
    ActivitySearchIngredientsBinding binding;
    Set<String> listIngredient;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> selectIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchIngredientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listIngredient = new HashSet<>();
        getListIngredients();
        Log.d("TAG", "LIST: " + listIngredient.size() + ": " + listIngredient);

        selectIngredients = new ArrayList<>();
        Intent result = new Intent();

        binding.idButtonSelected.setOnClickListener(v -> {
            selectIngredients.clear();
            for(int i = 0; i < binding.idChoiceIngredients.getCount(); i++){
                if (binding.idChoiceIngredients.isItemChecked(i)) {
                    selectIngredients.add(binding.idChoiceIngredients.getItemAtPosition(i).toString());
                }
            }
            result.putStringArrayListExtra("INGREDIENTS_KEY", selectIngredients);
            setResult(Activity.RESULT_OK, result);
            finish();
        });

        binding.idSearchIng.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void getListIngredients(){
        RecipesViewModel model = new ViewModelProvider(this).get(RecipesViewModel.class);
        model.getRecipes().observe(this, listOperationResponse -> {
            if (listOperationResponse instanceof Result.Success) {
                Result.Success<List<Recipe>> data = (Result.Success<List<Recipe>>) listOperationResponse;

                List<Recipe> countRecipe = data.getData();
                List<Ingredients> countIngredients;


                for(int i = 0; i < countRecipe.size(); i++){
                    countIngredients = countRecipe.get(i).getExtendedIngredients();

                    for(int j = 0; j < countIngredients.size(); j++){
                        listIngredient.add(countIngredients.get(j).getName());
                    }
                }

            } else if (listOperationResponse instanceof Result.Error) {
                Toast.makeText(SearchIngredientsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }

            arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_multiple_choice,
                    new ArrayList<>(listIngredient)
            );
            binding.idChoiceIngredients.setAdapter(arrayAdapter);

        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}