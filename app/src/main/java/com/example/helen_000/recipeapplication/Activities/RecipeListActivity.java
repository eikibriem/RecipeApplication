package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.helen_000.recipeapplication.Entities.Recipe;
import com.example.helen_000.recipeapplication.R;
import com.example.helen_000.recipeapplication.RecipesListFetch;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        AsyncTask task = new RecipeListActivity.FetchRecipeListTask().execute();
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");
    }


    private List<Recipe> mRecipes = new ArrayList<Recipe>();

    private class FetchRecipeListTask extends AsyncTask<String, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(String... params ){
            List<Recipe> recipes = new RecipesListFetch().fetchRecipeList(message);
            return recipes;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            mRecipes = recipes;

            Button[] myButtons = new Button[recipes.size()];

            LinearLayout myLayout = (LinearLayout) findViewById(R.id.linear);

            for(int i = 0; i < recipes.size(); i++){
                myButtons[i] = new Button(RecipeListActivity.this);
                Log.d(TAG, "Hér er ég: " + mRecipes.get(i).getRecipeName());
                myButtons[i].setText(mRecipes.get(i).getRecipeName());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 0, 20, 20); // (left, top, right, bottom)
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

                myLayout.addView(myButtons[i]);

            }

            //TextView textView2 = (TextView) findViewById(R.id.textView2);

            //setupAdapter(); //handles displaying the recipes
        }
    }
}
