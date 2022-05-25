package com.example.foodsuggestions.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodsuggestions.R;
import com.example.foodsuggestions.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityHomeBinding binding;
    private static final String DAIRY_KEY = "DAIRY_KEY";
    private static final String GLUTEN_KEY = "GLUTEN_KEY";
    private static final String DISH_KEY = "DISH_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigationBar.setSelectedItemId(R.id.nav_home);
        binding.navigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_account:
                        startActivity(new Intent(HomeActivity.this, ContActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_recipes:
                        startActivity(new Intent(HomeActivity.this, RecipesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        binding.idGluten.setOnClickListener(this);
        binding.idDairy.setOnClickListener(this);
        binding.idLunch.setOnClickListener(this);
        binding.idDish.setOnClickListener(this);
        binding.idSide.setOnClickListener(this);
        binding.idDinner.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        Intent intent = null;
        switch (v.getId()){
            case R.id.idGluten:
                intent = CategoryActivity.getIntent(this, new CategoryActivity.FilterCriteria(false, true));
                break;
            case R.id.idDairy:
                intent = CategoryActivity.getIntent(this, new CategoryActivity.FilterCriteria(true, false));
                break;
            case R.id.idLunch:
                intent = CategoryActivity.getIntent(this, new CategoryActivity.FilterCriteria("lunch"));
                break;
            case R.id.idDish:
                intent = CategoryActivity.getIntent(this, new CategoryActivity.FilterCriteria("main dish"));
                break;
            case R.id.idSide:
                intent = CategoryActivity.getIntent(this, new CategoryActivity.FilterCriteria("side dish"));
                break;
            case R.id.idDinner:
                intent = CategoryActivity.getIntent(this, new CategoryActivity.FilterCriteria("dinner"));
                break;
        }
        startActivity(intent);
    }
}