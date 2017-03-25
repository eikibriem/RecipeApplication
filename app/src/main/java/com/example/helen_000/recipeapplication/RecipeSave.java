package com.example.helen_000.recipeapplication;

import android.net.Uri;
import android.util.Log;

import com.example.helen_000.recipeapplication.Entities.Recipe;
import com.example.helen_000.recipeapplication.Entities.RecipeGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by helen_000 on 7.3.2017.
 */

public class RecipeSave {

    private static final String TAG = "RecipeSave";

    public Recipe saveRecipe(String recipeData) throws JSONException {
        Recipe recipe = new Recipe();
        String recipeData1 = "[" + recipeData + "]";
        JSONArray jsonBody = new JSONArray(recipeData1);
        JSONObject recipeObject = jsonBody.getJSONObject(0);

        String recipeName = recipeObject.getString("recipeName");
        String recipeGroup = recipeObject.getString("recipeGroup");
        String ingredients = recipeObject.getString("ingredients");
        String instructions = recipeObject.getString("instructions");
        String image = recipeObject.getString("image");

        recipe.setRecipeName(recipeName);
        recipe.setRecipeGroup(recipeGroup);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setImage(image);

        Log.d(TAG, "HOLA!!" + recipeName + " - " + recipeGroup + " - " + ingredients + " - " + instructions + " - " + image);

        try {
          String url = Uri.parse("http://10.0.2.2:8080/m/createRecipe/" + recipeName + "/" + recipeGroup + "/" + ingredients + "/" + instructions + "/" + image)
        //    String url = Uri.parse("http://10.0.2.2:8080/m/createRecipe/anything")
                    .buildUpon()
                    .build().toString();
            String jsonString = setData(url);
            Log.i(TAG, "Received JSON: " + jsonString);

        }catch (IOException ioe) {
            Log.e(TAG, "Failed to save recipe", ioe);
        }
        return recipe;
    }

    public String setData(String urlSpec) throws IOException {

        BufferedReader reader = null;

        try {
            URL url = new URL(urlSpec);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            Log.d(TAG, "hæ4 :)");
/*
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(conn.getResponseMessage() + ": with " + urlSpec);
            }
*/
            //InputStream in = conn.getInputStream();
            StringBuilder sb = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
                Log.d("Response: ", "> " + line); //Here you get the whole response
            }

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    private void parseRecipe(Recipe recipe, JSONObject jsonBody)
            throws JSONException {
        try {
            recipe.setId(jsonBody.getLong("id"));
            recipe.setRecipeName(jsonBody.getString("recipeName"));
            recipe.setRecipeGroup(jsonBody.getString("recipeGroup"));
            recipe.setIngredients(jsonBody.getString("ingredients"));
            recipe.setInstructions(jsonBody.getString("instructions"));
            recipe.setUsername(jsonBody.getString("username"));
            recipe.setImage(jsonBody.getString("image"));

        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
    }}
