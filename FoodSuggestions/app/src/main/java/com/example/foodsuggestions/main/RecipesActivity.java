package com.example.foodsuggestions.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.RecipeAdapter;
import com.example.foodsuggestions.models.Recipe;
import com.example.foodsuggestions.retrofit.ServiceProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesActivity extends AppCompatActivity {

    private List<Recipe> listRecipe;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    private ArrayList<Object> objectArrayList;
    BottomNavigationView bottomNavigationView;

    private String TAG = RecipesActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        bottomNavigationView = findViewById(R.id.idNavbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_recipes);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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

        recyclerView = (RecyclerView) findViewById(R.id.idListRecipes);

        listRecipe = new ArrayList<Recipe>();
        objectArrayList = new ArrayList<Object>();

        ServiceProvider.getInstance().getRecipeAPI().getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()){

                    //this method will be running on UI thread
                    recipeAdapter = new RecipeAdapter (RecipesActivity.this, response.body());

                    //se face aranjarea elementelor din lista
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(recipeAdapter);


                }
                else{
                    Toast.makeText(RecipesActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                
            }


            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(RecipesActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        });



        //new getRecipes().execute();

        //recipeAdapter.setClickListener(this);
    }

//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(RecipesActivity.this, "Ce a fost selectat: " + recipeAdapter.getItem(position), Toast.LENGTH_SHORT).show();
//
//
//    }

//    private class getRecipes extends AsyncTask<Void, Void, List<Recipe>> implements RecipeAdapter.ItemClickListener{
//
//        @Override
//        protected List<Recipe> doInBackground(Void... voids) {
//
//            HttpHandler sh = new HttpHandler();
//            // Making a request to url and getting response
//            String url = "http://192.168.50.74:8045/getRecipes";
//            String jsonStr = sh.makeServiceCall(url);
//
//            return new Gson().fromJson(jsonStr, new TypeToken<List<Recipe>>(){}.getType());
//
//        }
//
//        protected void onPostExecute(List<Recipe> result) {
//            super.onPostExecute(result);
//            //this method will be running on UI thread
//            recipeAdapter = new RecipeAdapter (RecipesActivity.this, result);
//
//            //se face aranjarea elementelor din lista
//            RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
//            recyclerView.setLayoutManager(manager);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setAdapter(recipeAdapter);
//
//
//            recipeAdapter.setClickListener(this);
//
//        }
//        @Override
//        public void onItemClick(View view, int position) {
//            //Toast.makeText(RecipesActivity.this, objectArrayList.get(position).toString(),Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(RecipesActivity.this, ShowRecipe.class);
//            intent.putExtra("json", objectArrayList.get(position).toString());
//            startActivity(intent);
//
//
//        }
//    }
}