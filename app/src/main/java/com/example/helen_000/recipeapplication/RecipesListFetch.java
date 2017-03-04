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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helen_000 on 4.3.2017.
 */

public class RecipesListFetch {
    private static final String TAG = "RecipeGroupFetch";



    public List<Recipe> fetchRecipeList(String message) {
        List<Recipe> recipes = new ArrayList<Recipe>();
        try {
            String url = Uri.parse("http://10.0.2.2:8080/m/recipe" + message)
                    .buildUpon()
                    .build().toString();
            String jsonString = getData(url);
            //  String jsonStringWithLastCharachter = jsonStringUnTrimmed.substring(1);
            //String jsonString = jsonStringWithLastCharachter.substring(0,jsonStringWithLastCharachter.length()-1);

            Log.i(TAG, "Received JSON: " + jsonString);

            JSONArray jsonBody = new JSONArray(jsonString);
            parseRecipes(recipes, jsonBody);

        }catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch recipes", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return recipes;
    }

    public String getData(String urlSpec) throws IOException {

        BufferedReader reader = null;

        try {
            URL url = new URL(urlSpec);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(conn.getResponseMessage() + ": with " + urlSpec);
            }

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


    private void parseRecipes(List<Recipe> recipeList, JSONArray jsonBody)
            throws JSONException {
        try {

            for(int i = 0; i < jsonBody.length(); i++){

                JSONObject groupJsonObject = jsonBody.getJSONObject(i);

                Recipe recipe = new Recipe();

                recipe.setId(groupJsonObject.getLong("id"));
                recipe.setRecipeName(groupJsonObject.getString("recipeName"));
                recipe.setRecipeGroup(groupJsonObject.getString("recipeGroup"));
                recipe.setIngredients(groupJsonObject.getString("ingredients"));
                recipe.setUsername(groupJsonObject.getString("username"));
                recipe.setInstructions(groupJsonObject.getString("instructions"));
                recipe.setImage(groupJsonObject.getString("image"));
                //recipe.setRate(groupJsonObject.getFloat("rate"));

                recipeList.add(recipe);
            }

        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
    }
}
