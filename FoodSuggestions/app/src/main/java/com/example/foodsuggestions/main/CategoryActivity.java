package com.example.foodsuggestions.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Predicate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.RecipeAdapter;
import com.example.foodsuggestions.data.RecipesRepository;
import com.example.foodsuggestions.databinding.ActivityCategoryBinding;
import com.example.foodsuggestions.models.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {

    private static final String FILTER_CATEGORY_KEY = "filterCategoryKey";

    public static Intent getIntent(Context context, FilterCriteria filter) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(FILTER_CATEGORY_KEY, filter);
        return intent;
    }

    private ActivityCategoryBinding binding;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FilterCriteria filter = (FilterCriteria) getIntent().getSerializableExtra(FILTER_CATEGORY_KEY);

        binding.navigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_account:
                        startActivity(new Intent(CategoryActivity.this, ContActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(CategoryActivity.this, SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_recipes:
                        startActivity(new Intent(CategoryActivity.this, RecipesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(CategoryActivity.this, HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        binding.listCategory.setLayoutManager(manager);
        binding.listCategory.setHasFixedSize(true);

        recipeAdapter = new RecipeAdapter(CategoryActivity.this);
        recipeAdapter.setListener(this);
        binding.listCategory.setAdapter(recipeAdapter);

        RecipesRepository.getInstance().getRecipes(getPredicate(filter), new RecipesRepository.RecipesCallback() {
            @Override
            public void onRecipesReceived(List<Recipe> recipes) {
                recipeAdapter.updateRecipes(recipes);
            }

            @Override
            public void onFailure(Throwable t) {
                // should not happen because no call is made
            }
        });
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = ShowRecipeActivity.getIntent(recipe, this);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Predicate<Recipe> getPredicate(FilterCriteria criteria) {
        if (criteria.query != null) {
            return recipe -> recipe.getDishTypes().stream().anyMatch(s -> s.equals(criteria.query));
        } else if (criteria.isDairyFree) {
            return recipe -> recipe.getDairyFree().equals("true");
        } else if (criteria.isGlutenFree) {
            return recipe -> recipe.getGlutenFree().equals("true");
        } else {
            return null;
        }
    }

    static class FilterCriteria implements Serializable {
        final boolean isDairyFree; // used for dairy filtering
        final boolean isGlutenFree; // used for gluten filtering
        final String query; // used for dish type

        FilterCriteria(boolean isDairyFree, boolean isGlutenFree) {
            this.isDairyFree = isDairyFree;
            this.isGlutenFree = isGlutenFree;
            this.query = null;
        }

        FilterCriteria(String query) {
            this.isDairyFree = false;
            this.isGlutenFree = false;
            this.query = query;
        }
    }
}