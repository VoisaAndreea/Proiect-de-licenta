package com.example.foodsuggestions.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.RecipeAdapter;
import com.example.foodsuggestions.databinding.ActivityCategoryBinding;
import com.example.foodsuggestions.models.Recipe;
import com.example.foodsuggestions.retrofit.ServiceProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {
    private ActivityCategoryBinding binding;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

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


        ServiceProvider.getInstance().getRecipeAPI().getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()){
                    List<Recipe> recipeList = response.body();

                    Log.d(CategoryActivity.class.getSimpleName(), "SIZE: " + recipeList.size());
                        for (int i = 0; i < recipeList.size(); i++) {
                            if (recipeList.get(i) != null && recipeList.get(i).getDairyFree().equals(bundle.getString("DAIRY_KEY"))) {
                                recipeAdapter.updateRecipes(Collections.singletonList(recipeList.get(i)));
                            } else if (recipeList.get(i) != null && recipeList.get(i).getGlutenFree().equals(bundle.getString("GLUTEN_KEY"))) {
                                recipeAdapter.updateRecipes(Collections.singletonList(recipeList.get(i)));
                            }

                            else if(!recipeList.get(i).getDishTypes().isEmpty()){
                                if (bundle.getString("DISH_KEY") != null && bundle.getString("DISH_KEY").equals("lunch")) {
                                    for (int j = 0; j < recipeList.get(i).getDishTypes().size(); j++) {
                                        if (recipeList.get(i).getDishTypes().get(j).equals(bundle.getString("DISH_KEY"))) {
                                            recipeAdapter.updateRecipes(Collections.singletonList(recipeList.get(i)));
                                        }
                                    }
                                }

                                else if(bundle.getString("DISH_KEY") != null && bundle.getString("DISH_KEY").equals("side dish")){
                                    for(int j = 0; j < recipeList.get(i).getDishTypes().size(); j++){
                                        if(recipeList.get(i).getDishTypes().get(j).equals(bundle.getString("DISH_KEY"))){
                                            recipeAdapter.updateRecipes(Collections.singletonList(recipeList.get(i)));
                                        }
                                    }
                                }

                                else if(bundle.getString("DISH_KEY") != null && bundle.getString("DISH_KEY").equals("main dish")){
                                    for(int j = 0; j < recipeList.get(i).getDishTypes().size(); j++){
                                        if(recipeList.get(i).getDishTypes().get(j).equals(bundle.getString("DISH_KEY"))){
                                            recipeAdapter.updateRecipes(Collections.singletonList(recipeList.get(i)));
                                        }
                                    }
                                }

                                else if(bundle.getString("DISH_KEY") != null && bundle.getString("DISH_KEY").equals("dinner")){
                                    for(int j = 0; j < recipeList.get(i).getDishTypes().size(); j++){
                                        if(recipeList.get(i).getDishTypes().get(j).equals(bundle.getString("DISH_KEY"))){
                                            recipeAdapter.updateRecipes(Collections.singletonList(recipeList.get(i)));
                                        }
                                    }
                                }
                            }
                        Log.d(CategoryActivity.class.getSimpleName(), "DISH SIZE: " + recipeList.get(i).getDishTypes().size());
                        }

                    Log.d(CategoryActivity.class.getSimpleName(), "BUNDLE: " + bundle);
                    //this method will be running on UI thread

                }
                else{
                    Toast.makeText(CategoryActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = ShowRecipeActivity.getIntent(recipe, this);
        startActivity(intent);
    }

}