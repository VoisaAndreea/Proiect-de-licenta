package com.example.foodsuggestions.data;

import androidx.annotation.NonNull;
import androidx.core.util.Predicate;
import com.example.foodsuggestions.models.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class RecipesRepository {
    private static final RecipesRepository instance = new RecipesRepository();
    private List<Recipe> recipes = null;

    private RecipesRepository() {
    }

    public static RecipesRepository getInstance() {
        return instance;
    }

    public void getRecipes(Predicate<Recipe> query, RecipesCallback callback) {
        if (recipes != null) {
            callback.onRecipesReceived(getFilteredList(query, recipes));
        } else {
            ServiceProvider.getInstance().getRecipeAPI().getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                    if (response.isSuccessful()) {
                        recipes = response.body();
                        callback.onRecipesReceived(getFilteredList(query, recipes));
                    } else {
                        callback.onFailure(new Throwable(response.errorBody().toString()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                    callback.onFailure(t);
                }
            });
        }
    }

    private List<Recipe> getFilteredList(Predicate<Recipe> predicate, List<Recipe> items) {
        if (predicate == null) return items;

        ArrayList<Recipe> result = new ArrayList<>();
        for (Recipe item : items) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public interface RecipesCallback {
        void onRecipesReceived(List<Recipe> recipes);

        void onFailure(Throwable t);
    }
}
