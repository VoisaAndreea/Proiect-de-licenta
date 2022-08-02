package com.example.foodsuggestions.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.util.Predicate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.RecipeAdapter;
import com.example.data.RecipesRepository;
import com.example.foodsuggestions.databinding.ActivitySearchBinding;
import com.example.data.model.Ingredients;
import com.example.data.model.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {
    private ActivitySearchBinding binding;
    private RecipeAdapter recipeAdapter;
    FilterSearch filterSearch;
    ArrayList<String> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigationBar.setSelectedItemId(R.id.nav_search);
        binding.navigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_account:
                        startActivity(new Intent(SearchActivity.this, ContActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(SearchActivity.this, HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_recipes:
                        startActivity(new Intent(SearchActivity.this, RecipesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        binding.idListSearch.setLayoutManager(manager);
        binding.idListSearch.setHasFixedSize(true);

        recipeAdapter = new RecipeAdapter(SearchActivity.this);
        recipeAdapter.setListener(this);
        binding.idListSearch.setAdapter(recipeAdapter);

        binding.searchButton.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                filterSearch = new FilterSearch(newText);

                if (newText != null) {
                    getResult(filterSearch);
                    return true;
                } else {
                    return false;
                }
            }
        });

        binding.searchIngredients.setOnClickListener(view -> {
            Intent intent = new Intent(SearchActivity.this, SearchIngredientsActivity.class);
            startActivityForResult(intent, 123);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            ingredients = data.getStringArrayListExtra("INGREDIENTS_KEY");
            filterSearch = new FilterSearch(ingredients);
            getResult(filterSearch);
            //Toast.makeText(this, "Item Selected: " + ingredients, Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getResult(FilterSearch newText){
        RecipesRepository.getInstance().getRecipes(getPredicate(newText), new RecipesRepository.RecipesCallback() {
            @Override
            public void onRecipesReceived(List<Recipe> recipes) {
                for(Recipe r : recipes){
                    Log.d("TAG", "RECIPES:" + r.getTitle());
                }
                if (recipes.isEmpty()) {
                    Toast.makeText(SearchActivity.this, "RECIPES NOT FOUND", Toast.LENGTH_SHORT).show();
                } else {
                    recipeAdapter.updateRecipes(recipes);
                }

                Log.d("TAG", "RECIPES:" + recipes.size());
                Log.d("TAG", "LIST INGREDIENNTS:" + ingredients);
            }
            @Override
            public void onFailure(Throwable t) {

            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Predicate<Recipe> getPredicate(FilterSearch criteria){
        if (criteria.title != null) {
            return recipe -> recipe.getTitle().toLowerCase().contains(criteria.title.toLowerCase());
        } else if (criteria.ingredientList != null) {
            return recipe -> {
                int count = 0;
                for(Ingredients ingredient : recipe.getExtendedIngredients() ){
                    for(String searchIngredient : criteria.ingredientList){
                        if (ingredient.getName().equals(searchIngredient)) {
                            count++;
                        }
                    }
                }
                return count == criteria.ingredientList.size();
            };



        } else {
            return null;

        }
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = ShowRecipeActivity.getIntent(recipe, this);
        startActivity(intent);
    }

    static class FilterSearch {
        final String title;
        final ArrayList<String> ingredientList;

        public FilterSearch(String title) {
            this.title = title;
            ingredientList = null;
        }

        public FilterSearch(ArrayList<String> ingredientList) {
            this.ingredientList = ingredientList;
            title = null;
        }
    }
}