package com.example.foodsuggestions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    private List<Recipe> listRecipe;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    private TextView textView;
    private ImageView imageView;
    private ProgressDialog progressDialog;

    private String TAG = RecipesActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        recyclerView = (RecyclerView) findViewById(R.id.idListRecipes);
        textView = (TextView) findViewById(R.id.idTitleRe);
        imageView = (ImageView) findViewById(R.id.idImageRe);

        listRecipe = new ArrayList<Recipe>();

        new getRecipes().execute();
    }

    private class getRecipes extends AsyncTask<Void, Void, List<Recipe>> implements RecipeAdapter.ItemClickListener{

        @Override
        protected List<Recipe> doInBackground(Void... voids) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://192.168.1.102:8045/getRecipes"; //192.168.1.102-laptop
            String jsonStr = sh.makeServiceCall(url);

            Log.d(TAG, "RESPONSE FROM URL: " + jsonStr);

            if(jsonStr != null){
                try {
                    JSONArray jsonObject = new JSONArray(jsonStr);
                    for(int i = 0; i < jsonObject.length(); i++){
                        JSONObject jo =jsonObject.getJSONObject(i);
                        String id = jo.getString("id");
                        String title = jo.getString("title");
                        String image = jo.getString("image");
                        listRecipe.add(new Recipe(title,image));

                    }
                    return listRecipe;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(List<Recipe> result) {
            super.onPostExecute(result);
            //this method will be running on UI thread
            recipeAdapter = new RecipeAdapter (RecipesActivity.this, result);

            //se face aranjarea elementelor din lista
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(recipeAdapter);


            recipeAdapter.setClickListener(this);

        }
        @Override
        public void onItemClick(View view, int position) {

        }
    }
}