package com.example.foodsuggestions.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.util.Predicate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.RecipeAdapter;
import com.example.foodsuggestions.data.RecipesRepository;
import com.example.foodsuggestions.databinding.ActivitySearchBinding;
import com.example.foodsuggestions.models.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {
    private ActivitySearchBinding binding;
    private RecipeAdapter recipeAdapter;

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

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    getResult(newText);
                    return true;
                } else {
                    return false;
                }
            }
        });

        binding.searchIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchIngredientsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getResult(String newText){
        RecipesRepository.getInstance().getRecipes(getPredicate(newText), new RecipesRepository.RecipesCallback() {
            @Override
            public void onRecipesReceived(List<Recipe> recipes) {
                recipeAdapter.updateRecipes(recipes);
            }
            @Override
            public void onFailure(Throwable t) {

            }

        });

    }

    private Predicate<Recipe> getPredicate(String criteria){
        if (criteria != null) {
            return recipe -> recipe.getTitle().contains(criteria);
        } else {
            return null;
        }
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = ShowRecipeActivity.getIntent(recipe, this);
        startActivity(intent);
    }
}