package com.example.foodsuggestions.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.ViewPagerAdapter;
import com.example.foodsuggestions.fragments.FeedbackFragment;
import com.example.foodsuggestions.fragments.IngredientsFragment;
import com.example.foodsuggestions.fragments.InstructionFragment;
import com.example.foodsuggestions.fragments.RecipeFragment;
import com.example.foodsuggestions.models.Recipe;
import com.google.android.material.tabs.TabLayout;

public class ShowRecipeActivity extends AppCompatActivity {
    private String TAG = ShowRecipeActivity.class.getSimpleName();

    private RecipeFragment recipeFragment;
    private IngredientsFragment ingredientsFragment;
    private InstructionFragment instructionFragment;
    private FeedbackFragment feedbackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);

        ViewPager viewPager = findViewById(R.id.idViewPager);
        TabLayout tabLayout = findViewById(R.id.idToolBar);

        Recipe recipe = getIntent().getParcelableExtra(RECIPE_KEY);

        recipeFragment = RecipeFragment.newInstance(recipe.getTitle(),recipe.getImage());
        ingredientsFragment = IngredientsFragment.newInstance(recipe.getExtendedIngredients());
        instructionFragment = InstructionFragment.newInstance(recipe.getAnalyzedInstructions(),
                              recipe.getSummary(), recipe.getSourceUrl());
        feedbackFragment = new FeedbackFragment();

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(recipeFragment, "Recipe");
        viewPagerAdapter.addFragment(ingredientsFragment, "Ingredients");
        viewPagerAdapter.addFragment(instructionFragment, "Instruction");
        viewPagerAdapter.addFragment(feedbackFragment, "Feedback");
        viewPager.setAdapter(viewPagerAdapter);

    }

    private static final String RECIPE_KEY = "RECIPE_KEY";
    public static Intent getIntent(Recipe recipe, Context context) {
        Intent intent = new Intent(context, ShowRecipeActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);
        return intent;
    }

}