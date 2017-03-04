 package com.example.helen_000.recipeapplication.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.helen_000.recipeapplication.Entities.Recipe;
import com.example.helen_000.recipeapplication.R;
import com.example.helen_000.recipeapplication.RecipeFetch;

 public class RecipeActivity extends AppCompatActivity {


     private Long message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        AsyncTask task = new RecipeActivity.FetchRecipeTask().execute();
        Bundle bundle = getIntent().getExtras();
        message = bundle.getLong("message");
    }


     private Recipe mRecipe = new Recipe();

    private class FetchRecipeTask extends AsyncTask<Void, Void, Recipe> {
        @Override
        protected Recipe doInBackground(Void... params ){
            Recipe recipe = new RecipeFetch().fetchRecipe(message);
            return recipe;
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            mRecipe = recipe;
            TextView textViewRecipeName = (TextView) findViewById(R.id.textViewRecipeName);
            textViewRecipeName.setText(mRecipe.getRecipeName());
           // ImageView imageView = (ImageView) findViewById(R.id.imageView);
         //   imageView.setImageResource(mRecipe.getImage());

            TextView textViewIngredients = (TextView) findViewById(R.id.textViewIngredients);
            textViewIngredients.setText(mRecipe.getIngredients());

            TextView textViewInstructions = (TextView) findViewById(R.id.textViewInstructions);
            textViewInstructions.setText(mRecipe.getInstructions());

            TextView textViewUsername = (TextView) findViewById(R.id.textViewUsername);
            textViewUsername.setText("Posted by " + mRecipe.getUsername());

        }
    }
}
