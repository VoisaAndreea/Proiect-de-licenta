package com.example.data;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.util.Predicate;
import androidx.room.Room;

import com.example.data.db.AppDatabase;
import com.example.data.model.CacheRecipe;
import com.example.data.model.Recipe;
import com.example.data.remote.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesRepository {
    private static RecipesRepository instance;
    private List<Recipe> recipes = null;
    private final AppDatabase db;

    private RecipesRepository(Context applicationContext) {
        //applicationContext.deleteDatabase("database-name");
        db = Room.databaseBuilder(
                applicationContext,
                AppDatabase.class,
                "database-name"
        ).build();
    }

    public static RecipesRepository getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("Please call getInstance(Context) first");
        }
        return instance;
    }

    public static RecipesRepository getInstance(Context appContext) {
        if (instance == null) {
            instance = new RecipesRepository(appContext);
        }
        return instance;
    }

    public void getRecipes(Predicate<Recipe> query, RecipesCallback callback) {
        if (recipes != null) {
            callback.onRecipesReceived(getFilteredList(query, recipes));
        } else {
            ServiceProvider.getInstance().getRecipeAPI().getRecipes().enqueue(new Callback<List<Recipe>>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                    if (response.isSuccessful()) {
                        recipes = response.body();
                        runInBackground(() -> {
                            List<CacheRecipe> cacheRecipeList = recipes.stream().map(CacheRecipe::new).collect(Collectors.toList());
                            db.cacheRecipeDao().deleteAll();
                            db.cacheRecipeDao().insertAll(cacheRecipeList);
                            new Handler(Looper.getMainLooper()).post(() -> {
                                callback.onRecipesReceived(getFilteredList(query, recipes));
                            });
                        });
                    } else {
                        loadCacheData(callback, new Throwable(response.errorBody().toString()), query);
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                   loadCacheData(callback, t, query);
                }
            });
        }
    }

    private void runInBackground(Runnable doInBackground) {
        new Thread(() -> {
            doInBackground.run();
            //new Handler(Looper.getMainLooper()).post(doInMainThread);
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadCacheData(RecipesCallback callback,Throwable throwable, Predicate<Recipe> query) {
        runInBackground(() -> {
            List<CacheRecipe> chr = db.cacheRecipeDao().getAll();
            List<Recipe> recipes = chr.stream().map(Recipe::new).collect(Collectors.toList());
            new Handler(Looper.getMainLooper()).post(() -> {
                if (recipes.size() != 0) {
                    callback.onRecipesReceived(getFilteredList(query, recipes));
                } else {
                    callback.onFailure(throwable);
                }
            });
        });
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
