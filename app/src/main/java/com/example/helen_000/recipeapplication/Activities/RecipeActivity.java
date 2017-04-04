 package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helen_000.recipeapplication.Entities.Recipe;
import com.example.helen_000.recipeapplication.R;
import com.example.helen_000.recipeapplication.RecipeDelete;
import com.example.helen_000.recipeapplication.RecipeFetch;
import com.squareup.picasso.Picasso;

 public class RecipeActivity extends AppCompatActivity {
     private String TAG = "recipeActivity";

     private Long message;
     ImageView image;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        image = (ImageView)findViewById(R.id.imageView);
        AsyncTask task = new RecipeActivity.FetchRecipeTask().execute();
        Bundle bundle = getIntent().getExtras(); //getting the id of the recipe from the recipeListActivity
        message = bundle.getLong("message"); //getting the id of the recipe from the recipeListActivity/UploadRecipeActivity
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

            Picasso.with(RecipeActivity.this).load(mRecipe.getImage()).into(image);

            TextView textViewIngredients = (TextView) findViewById(R.id.textViewIngredients);
            textViewIngredients.setText(mRecipe.getIngredients());

            TextView textViewInstructions = (TextView) findViewById(R.id.textViewInstructions);
            textViewInstructions.setText(mRecipe.getInstructions());

            TextView textViewUsername = (TextView) findViewById(R.id.textViewUsername);
            textViewUsername.setText("Posted by " + mRecipe.getUsername());

            Button buttonEditRecipe = (Button) findViewById(R.id.mEditRecipe);
            Button buttonDeleteRecipe = (Button) findViewById(R.id.mDeleteRecipe);


            String loggedInUser = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).getString("userName", "No logged in user");
            String recipeUser = mRecipe.getUsername();


            //If the loggedInUser is the same as the recipeUser we display the delete and edit recipe buttons
            if (loggedInUser.equals(recipeUser)) {
                buttonEditRecipe.setVisibility(View.VISIBLE);
                buttonDeleteRecipe.setVisibility(View.VISIBLE);
            }

        }
    }
     public void mDeleteRecipeButtonOnClick (View v){

         AsyncTask task = new RecipeActivity.DeleteRecipeTask().execute(mRecipe);

     }

     private class DeleteRecipeTask extends AsyncTask<Recipe, Void, String> {

         @Override
         protected String doInBackground(Recipe... params ){
             String returnMessage = new RecipeDelete().deleteRecipe(message);
             return returnMessage;
         }

         @Override
         protected void onPostExecute(String returnMessage) {
             Intent intent = new Intent(RecipeActivity.this, HomeScreenActivity.class);
             startActivity(intent);
             finish();
         }
     }


     public void mEditRecipeButtonOnClick (View v){

         AsyncTask task = new RecipeActivity.EditRecipeTask().execute(mRecipe);

     }
     private class EditRecipeTask extends AsyncTask<Recipe, Void, Recipe> {

         @Override
         protected Recipe doInBackground(Recipe... params ){
             return params[0];
         }

         @Override
         protected void onPostExecute(Recipe recipeToEdit) {
             Intent intent = new Intent(RecipeActivity.this, UploadRecipeActivity.class);
             Long recipeToEditId = recipeToEdit.getId();
             intent.putExtra("operation",  recipeToEditId);

             startActivity(intent);
         }
     }


 }
