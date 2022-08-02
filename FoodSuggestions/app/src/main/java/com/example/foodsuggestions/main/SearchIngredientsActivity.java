package com.example.foodsuggestions.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodsuggestions.adapters.SearchIngredientAdapter;
import com.example.foodsuggestions.data.Result;
import com.example.foodsuggestions.databinding.ActivitySearchIngredientsBinding;
import com.example.foodsuggestions.models.Ingredients;
import com.example.foodsuggestions.models.Recipe;
import com.example.foodsuggestions.viewmodels.RecipesViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchIngredientsActivity extends AppCompatActivity {
    private ActivitySearchIngredientsBinding binding;
    private SearchIngredientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchIngredientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getListIngredients();

        binding.idButtonSelected.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putStringArrayListExtra("INGREDIENTS_KEY", adapter.getSelectedIngredients());
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
                adapter.filter(newText);
                return true;
            }
        });
    }

    private void getListIngredients(){
        HashSet<String> listIngredient = new HashSet<>();

        RecipesViewModel model = new ViewModelProvider(this).get(RecipesViewModel.class);
        model.getRecipes().observe(this, listOperationResponse -> {
            if (listOperationResponse instanceof Result.Success) {

                Result.Success<List<Recipe>> data = (Result.Success<List<Recipe>>) listOperationResponse;
                for (Recipe recipe : data.getData()) {
                    for(Ingredients ingredients : recipe.getExtendedIngredients()){
                        listIngredient.add(ingredients.getName());
                    }
                }

            } else if (listOperationResponse instanceof Result.Error) {
                Toast.makeText(SearchIngredientsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
            List<IngredientsState> items = new ArrayList<>();
            for (String ingredient : listIngredient){
                items.add(new IngredientsState(ingredient, false));
            }
            adapter = new SearchIngredientAdapter(items);
            binding.idChoiceIngredients.setAdapter(adapter);

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

    public static class IngredientsState implements Comparable<IngredientsState>{
        public String ingredientName;
        public boolean isCheck;

        public IngredientsState(String ingredientName, boolean isCheck) {
            this.ingredientName = ingredientName;
            this.isCheck = isCheck;
        }

        @Override
        public int compareTo(IngredientsState o) {
            return this.ingredientName.compareTo(o.ingredientName);
        }
    }

}