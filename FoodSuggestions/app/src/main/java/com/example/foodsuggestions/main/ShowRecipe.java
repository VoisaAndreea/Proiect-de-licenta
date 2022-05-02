package com.example.foodsuggestions.main;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.ViewPagerAdapter;
import com.example.foodsuggestions.fragments.FeedbackFragment;
import com.example.foodsuggestions.fragments.IngredientsFragment;
import com.example.foodsuggestions.fragments.InstructionFragment;
import com.example.foodsuggestions.fragments.RecipeFragment;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class ShowRecipe extends AppCompatActivity {
    private String TAG = ShowRecipe.class.getSimpleName();
//    TextView textView;
//   ImageView imageView;
//    Button bIngredients, bInstructions, bFeedback;
//    TextView txtNumber;
//    EditText editServings;
//
//
//    ArrayList<Ingredients> ingredients;
//    private RecyclerView recyclerView;
//    IngredientsAdapter ingredientsAdapter;
//
//    ArrayList<String> instructionsList;
//    ListView listView;
//    ArrayAdapter<String> listAdapter;

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

        recipeFragment = new RecipeFragment();
        ingredientsFragment = new IngredientsFragment();
        instructionFragment = new InstructionFragment();
        feedbackFragment = new FeedbackFragment();

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(recipeFragment, "Recipe");
        viewPagerAdapter.addFragment(ingredientsFragment, "Ingredients");
        viewPagerAdapter.addFragment(instructionFragment, "Instruction");
        viewPagerAdapter.addFragment(feedbackFragment, "Feedback");
        viewPager.setAdapter(viewPagerAdapter);

        try {
           JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("test"));
           String str = jsonObject.getString("title");
           String img = jsonObject.getString("image");
           String ing = jsonObject.getString("extendedIngredients");
           String summary = jsonObject.getString("summary");
           String instr = jsonObject.getString("analyzedInstructions");

            Bundle data = new Bundle();
            data.putString("titleRet", str);
            data.putString("imageRet", img);
            recipeFragment.setArguments(data);

            Bundle dataIng = new Bundle();
            dataIng.putString("ing", ing);
            ingredientsFragment.setArguments(dataIng);

            Bundle dataInst = new Bundle();
           // dataInst.putString("summary", summary);
           dataInst.putString("step", instr);
            Log.d("TAG","INSTRACTION: " + instr.toString());
            instructionFragment.setArguments(dataInst);

        } catch (JSONException e) {
            e.printStackTrace();
        }


//        textView = findViewById(R.id.idTextFrag);
//        imageView = findViewById(R.id.idImageFrag);
//        bIngredients = findViewById(R.id.buttonIngredients);
//        bInstructions = findViewById(R.id.buttonInstructions);
//        bFeedback = findViewById(R.id.buttonFeedback);
//        txtNumber = findViewById(R.id.textInformative);
//        editServings = findViewById(R.id.editAmount);
//
//
//        recyclerView = findViewById(R.id.idListIngredients);
//        ingredients = new ArrayList<Ingredients>();
//
//        instructionsList = new ArrayList<String>();
//        listView = findViewById(R.id.idListInstructions);

//        try {
//           JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("test"));
//
//            textView.setText(jsonObject.getString("title"));
//            Picasso.with(ShowRecipe.this).load(jsonObject.getString("image")).into(imageView);
//          //  editServings.setText(jsonObject.getString("servings"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        bIngredients.setOnClickListener(this);
//        bInstructions.setOnClickListener(this);
//        bFeedback.setOnClickListener(this);


    }

   // @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case(R.id.buttonIngredients):
//                listView.setVisibility(View.GONE);
//                txtNumber.setVisibility(View.VISIBLE);
//                editServings.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//                new getIngredients().execute();
//                ingredients.clear();
//                break;
//            case(R.id.buttonInstructions):
//                listView.setVisibility(View.VISIBLE);
//                txtNumber.setVisibility(View.GONE);
//                editServings.setVisibility(View.GONE);
//                recyclerView.setVisibility(View.GONE);
//                new getInstructions().execute();
//                break;
//            case(R.id.buttonFeedback):
//                listView.setVisibility(View.GONE);
//                txtNumber.setVisibility(View.GONE);
//                editServings.setVisibility(View.GONE);
//                recyclerView.setVisibility(View.GONE);
//                break;
//        }

    //}

//    private class getIngredients extends AsyncTask<Void, Void, ArrayList<Ingredients>>{
//
//        @Override
//        protected ArrayList<Ingredients> doInBackground(Void... voids) {
////            try {
////                JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("json"));
//////                String servingsServer = jsonObject.getString("servings");
////                Log.d("ShowRecipe.class", "JSONNN:  " + jsonObject);
////                JSONArray jsonArray = jsonObject.getJSONArray("extendedIngredients");
////                Log.d("ShowRecipe.class", "JSONNN ARRAYYYYYYYYY:  " + jsonArray);
////
////                for(int i = 0; i < jsonArray.length(); i++){
////                    JSONObject jso =jsonArray.getJSONObject(i);
////                    String amount = jso.getString("amount");
////                    String unit = jso.getString("unit");
////                    String name = jso.getString("name");
////
////                    ingredients.add(new Ingredients(amount,unit,name));
////
////
////                }
////                return ingredients;
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
//            return null;
//
//        }
//
//        protected void onPostExecute(ArrayList<Ingredients> result) {
//            super.onPostExecute(result);
//            //this method will be running on UI thread
//            ingredientsAdapter = new IngredientsAdapter (ShowRecipe.this, result);
//
//            //se face aranjarea elementelor din lista
//            RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
//            recyclerView.setLayoutManager(manager);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setAdapter(ingredientsAdapter);
//
//
//        }
//    }
//
//    public class getInstructions extends AsyncTask<Void, Void, ArrayList<String>>{
//
//        @Override
//        protected ArrayList<String> doInBackground(Void... voids) {
//            try {
//                JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("json"));
//
//                if(jsonObject.getString("instructions").isEmpty()){
//                    String name = jsonObject.getString("summary");
//                    instructionsList.add(name);
//                }else{
//                    JSONArray jsonObjectSteps = jsonObject.getJSONArray("analyzedInstructions");
//                    for(int i = 0; i < jsonObjectSteps.length(); i++){
//                        JSONObject js = jsonObjectSteps.getJSONObject(i);
//                        JSONArray arraySteps = js.getJSONArray("steps");
//
//                        for(int j = 0; j < arraySteps.length(); j++){
//                            JSONObject objectStep = arraySteps.getJSONObject(j);
//                            String instruct = objectStep.getString("step");
//                            instructionsList.add(instruct);
//                        }
//                    }
//
//                    return instructionsList;
//                }
//
//                return instructionsList;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        protected void onPostExecute(ArrayList<String> result) {
//            super.onPostExecute(result);
//
//            listAdapter = new ArrayAdapter<String>(ShowRecipe.this, R.layout.row_instruction, result);
//            listView.setAdapter(listAdapter);
//
//        }
//
//    }


}