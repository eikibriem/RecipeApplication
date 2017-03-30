package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.helen_000.recipeapplication.Entities.Recipe;
import com.example.helen_000.recipeapplication.R;
import com.example.helen_000.recipeapplication.RecipesListFetch;
import com.example.helen_000.recipeapplication.SearchRecipeListFetch;
import com.example.helen_000.recipeapplication.YourRecipesListFetch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = "RecipeListActivity";
    private String message;
    private String searchMessage;
    private String loggedInUser;
    boolean ContainsRecipeGroupInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");
        searchMessage = bundle.getString("searchMessage");

        if(searchMessage != null){
            AsyncTask task = new FetchSearchTask().execute();
            return;
        }

        //Athugum hvort verið er að leita eftir notanda eða recipeGroup
        ContainsRecipeGroupInfo = messageContainsRecipeGroup(message);

        //Leitað eftir notanda
        if (ContainsRecipeGroupInfo == false){
            AsyncTask task = new RecipeListActivity.FetchYourRecipeListTask().execute();
        }
        //Leitað eftir recipeGroup
        else {
            AsyncTask task = new RecipeListActivity.FetchRecipeListTask().execute();
        }
    }


    /*
       Checks if the message includes information about a recipe group
     */
    public static boolean messageContainsRecipeGroup(String message) {

        List<String> recipeGroups = new ArrayList<String>();
        recipeGroups.add(0, "appetizers");
        recipeGroups.add(1, "dinners");
        recipeGroups.add(2, "desserts");
        recipeGroups.add(3, "raw");
        recipeGroups.add(4, "breakfast");
        recipeGroups.add(5, "baking");

        boolean ContainsRecipeGroupInfo = recipeGroups.contains(message);
        Log.d(TAG, "niðurstaðan: " + ContainsRecipeGroupInfo);

        return ContainsRecipeGroupInfo;
    }




    private List<Recipe> mRecipes = new ArrayList<Recipe>();

    /*
        This AsyncTask is used when searching by a recipe group
     */
    private class FetchRecipeListTask extends AsyncTask<String, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(String... params ){
            List<Recipe> recipes = new RecipesListFetch().fetchRecipeList(message);
            return recipes;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            mRecipes = recipes;
            LinearLayout myLayout = (LinearLayout) findViewById(R.id.linear);

            if (recipes.size() == 0){
                TextView textView = new TextView(RecipeListActivity.this);
                textView.setText("No recipes");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 0, 20, 20); // (left, top, right, bottom)
                textView.setLayoutParams(lp);
                myLayout.addView(textView);
            }

            ImageView[] myRecipesImages = new ImageView[recipes.size()];
            Button[] myButtons = new Button[recipes.size()];



            for(int i = 0; i < recipes.size(); i++){

                myRecipesImages[i] = new ImageView(RecipeListActivity.this);
                myButtons[i] = new Button(RecipeListActivity.this);

                Picasso.with(RecipeListActivity.this).load(mRecipes.get(i).getImage()).into(myRecipesImages[i]);
                myButtons[i].setText(mRecipes.get(i).getRecipeName());

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 0, 20, 20); // (left, top, right, bottom)
                myRecipesImages[i].setLayoutParams(lp);
                myButtons[i].setLayoutParams(lp);

                final Long recipeId = mRecipes.get(i).getId();

                myButtons[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
                        Long message = recipeId;
                        intent.putExtra("message",  message);
                        startActivity(intent);
                    }
                });

                myRecipesImages[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
                        Long message = recipeId;
                        intent.putExtra("message",  message);
                        startActivity(intent);
                    }
                });

                myLayout.addView(myRecipesImages[i]);
                myLayout.addView(myButtons[i]);

            }
        }
    }



    /*
        This AsyncTask is used when searching by a user
     */
    private class FetchYourRecipeListTask extends AsyncTask<String, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(String... params ){
            Log.d(TAG, "User: " + message);
            List<Recipe> recipes = new YourRecipesListFetch().fetchRecipeList(message);
            return recipes;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            mRecipes = recipes;
            LinearLayout myLayout = (LinearLayout) findViewById(R.id.linear);

            if (recipes.size() == 0){
                TextView textView = new TextView(RecipeListActivity.this);
                textView.setText("No recipes");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 0, 20, 20); // (left, top, right, bottom)
                textView.setLayoutParams(lp);
                myLayout.addView(textView);
            }

            ImageView[] myRecipesImages = new ImageView[recipes.size()];
            Button[] myButtons = new Button[recipes.size()];



            for(int i = 0; i < recipes.size(); i++){

                myRecipesImages[i] = new ImageView(RecipeListActivity.this);
                myButtons[i] = new Button(RecipeListActivity.this);

                Picasso.with(RecipeListActivity.this).load(mRecipes.get(i).getImage()).into(myRecipesImages[i]);
                myButtons[i].setText(mRecipes.get(i).getRecipeName());

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 0, 20, 20); // (left, top, right, bottom)
                myRecipesImages[i].setLayoutParams(lp);
                myButtons[i].setLayoutParams(lp);

                final Long recipeId = mRecipes.get(i).getId();

                myButtons[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
                        Long message = recipeId;
                        intent.putExtra("message",  message);
                        startActivity(intent);
                    }
                });

                myRecipesImages[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
                        Long message = recipeId;
                        intent.putExtra("message",  message);
                        startActivity(intent);
                    }
                });

                myLayout.addView(myRecipesImages[i]);
                myLayout.addView(myButtons[i]);

            }
        }
    }

    private class FetchSearchTask extends AsyncTask<String, Void, List<Recipe>>{
        @Override
        protected List<Recipe> doInBackground(String... params ){
            Log.d(TAG, "SearchString: " + searchMessage);

            List<Recipe> recipes = new SearchRecipeListFetch().fetchRecipeList(searchMessage);
            return recipes;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            mRecipes = recipes;
            LinearLayout myLayout = (LinearLayout) findViewById(R.id.linear);

            if (recipes.size() == 0){
                TextView textView = new TextView(RecipeListActivity.this);
                textView.setText("No recipes");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 0, 20, 20); // (left, top, right, bottom)
                textView.setLayoutParams(lp);
                myLayout.addView(textView);
            }

            ImageView[] myRecipesImages = new ImageView[recipes.size()];
            Button[] myButtons = new Button[recipes.size()];



            for(int i = 0; i < recipes.size(); i++){

                myRecipesImages[i] = new ImageView(RecipeListActivity.this);
                myButtons[i] = new Button(RecipeListActivity.this);

                Picasso.with(RecipeListActivity.this).load(mRecipes.get(i).getImage()).into(myRecipesImages[i]);
                myButtons[i].setText(mRecipes.get(i).getRecipeName());

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 0, 20, 20); // (left, top, right, bottom)
                myRecipesImages[i].setLayoutParams(lp);
                myButtons[i].setLayoutParams(lp);

                final Long recipeId = mRecipes.get(i).getId();

                myButtons[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
                        Long message = recipeId;
                        intent.putExtra("message",  message);
                        startActivity(intent);
                    }
                });

                myRecipesImages[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
                        Long message = recipeId;
                        intent.putExtra("message",  message);
                        startActivity(intent);
                    }
                });

                myLayout.addView(myRecipesImages[i]);
                myLayout.addView(myButtons[i]);

            }
        }
    }
}
