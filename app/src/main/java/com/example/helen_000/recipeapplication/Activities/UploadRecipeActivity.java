package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.helen_000.recipeapplication.Entities.Recipe;
import com.example.helen_000.recipeapplication.Entities.RecipeGroup;
import com.example.helen_000.recipeapplication.R;
import com.example.helen_000.recipeapplication.RecipeGroupFetch;
import com.example.helen_000.recipeapplication.RecipeSave;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UploadRecipeActivity extends AppCompatActivity {

    private static final String TAG = "CreateRecipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipe);
        Spinner dropdown = (Spinner)findViewById(R.id.spinnerRecipeGroup);
        String[] items = new String[]{"Appetizer", "Baking", "Breakfast", "Dinner", "Dessert", "Raw"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    public String getRecipeName(View v){
        EditText recipeName = (EditText) findViewById(R.id.editTextRecipeName);
        String recipeNameValue = recipeName.getText().toString();

        Log.d("AddRecipe","Recipe Name: "+recipeName);

        return recipeNameValue;
    }

    public String getRecipeGroupView(View v){
        Spinner recipeGroup = (Spinner)findViewById(R.id.spinnerRecipeGroup);
        String recipeGroupValue = recipeGroup.getSelectedItem().toString();

        Log.d("AddRecipeGroup","RecipeGroup Name: "+recipeGroupValue);

        return recipeGroupValue;
    }

    public String getIngredients(View v){
        EditText ingredients = (EditText) findViewById(R.id.editTextIngredients);
        String ingredientsValue = ingredients.getText().toString();

        Log.d("AddIngredients","Ingredients: "+ingredients);

        return ingredientsValue;
    }

    public String getInstructions(View v){
        EditText instructions = (EditText) findViewById(R.id.editTextInstructions);
        String instructionsValue =  instructions.getText().toString();

        Log.d("AddInstructions","instructions:  "+instructions);

        return instructionsValue;
    }

    public String getImage(View v){
        EditText image = (EditText) findViewById(R.id.editTextImage);
        String imageValue = image.getText().toString();

        Log.d("AddImage","image:  "+image);

        return imageValue;
    }

    public void mButtonSaveRecipeOnClick (View v){

        String recipeName = getRecipeName(v);
        String recipeGroup = getRecipeGroupView(v);
        String ingredients = getIngredients(v);
        String instructions = getInstructions(v);
        String image = getImage(v);
        //String username = getUsername(v);

       /* Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeName);
        recipe.setRecipeGroup(recipeGroup);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setRate(0);
        recipe.setImage(image);
        //recipe.setUsername(??Sækja username??);
        */

        Log.d(TAG,"Recipe Hluturinn: " + recipeName);

        JSONObject recipeData = new JSONObject();
        try {

            recipeData.put("recipeName", recipeName);

            recipeData.put("recipeGroup", recipeGroup);
            recipeData.put("ingredients", ingredients);
            recipeData.put("instructions", instructions);
            recipeData.put("rate", 0);
            recipeData.put("image", image);

            Log.d(TAG,"Recipe Hluturinn: " + recipeData.toString());
            Log.d(TAG, "Hellú" + recipeData.toString());
            new saveRecipeTask().execute(recipeData.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class saveRecipeTask extends AsyncTask<String, Void, Recipe> {
        @Override
        protected Recipe doInBackground(String... params) {
            //data: params[1]
            Recipe recipe = null;
            try {
                Log.d(TAG, "Hellú");
                recipe = new RecipeSave().saveRecipe(params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return recipe;
        }
    }

/*
    private class SendRecipeDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";
            Log.d(TAG,"Ég er hér1");
            HttpURLConnection httpURLConnection = null;
            try {
                Log.d(TAG,"Ég er hér2");
                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                Log.d(TAG,"Ég er hér3");
                httpURLConnection.setRequestMethod("POST");
                Log.d(TAG,"Ég er hér4");

                httpURLConnection.setDoOutput(true);

                Log.d(TAG,"Ég er hér5");
                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                Log.d(TAG,"Ég er hér6");
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                Log.d(TAG,"Ég er hér7");
                InputStream in = httpURLConnection.getInputStream();
                Log.d(TAG,"Ég er hér7,5");
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                Log.d(TAG,"Ég er hér8");

                int inputStreamData = inputStreamReader.read();

                Log.d(TAG,"Ég er hér9");
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    Log.d(TAG,"Ég er hér10");
                    data += current;
                    Log.d(TAG, "HALLÓÓÓÓÓ!! + " + data + " + " + current);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
*/
}

