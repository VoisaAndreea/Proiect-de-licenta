package com.example.foodsuggestions.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.RecipeAdapter;
import com.example.foodsuggestions.data.Result;
import com.example.foodsuggestions.models.Recipe;
import com.example.foodsuggestions.viewmodels.RecipesViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class RecipesActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {
    private String TAG = RecipesActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        bottomNavigationView = findViewById(R.id.navigationBar);
        bottomNavigationView.setSelectedItemId(R.id.nav_recipes);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //overridePendingTransition(0,0) - aceasta "elimina" animatia care se face atunci cand
                //se trece de la o activitate la alta(care e destul de deranjanta)
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(RecipesActivity.this, HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(RecipesActivity.this, SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_account:
                        startActivity(new Intent(RecipesActivity.this, ContActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.idListRecipes);

        //se face aranjarea elementelor din lista
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        recipeAdapter = new RecipeAdapter(RecipesActivity.this);
        recipeAdapter.setListener(this);
        recyclerView.setAdapter(recipeAdapter);

        RecipesViewModel model = new ViewModelProvider(this).get(RecipesViewModel.class);
        model.getRecipes().observe(this, listOperationResponse -> {
            if (listOperationResponse instanceof Result.Success) {
                Result.Success<List<Recipe>> data = (Result.Success<List<Recipe>>) listOperationResponse;
                recipeAdapter.updateRecipes(data.getData());
            } else if (listOperationResponse instanceof Result.Error) {
                Toast.makeText(RecipesActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = ShowRecipeActivity.getIntent(recipe, this);
        startActivity(intent);
    }
}