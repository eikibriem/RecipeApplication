package com.example.helen_000.recipeapplication;

import android.net.Uri;
import android.util.Log;

import com.example.helen_000.recipeapplication.Entities.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helen_000 on 3.3.2017.
 */

public class RecipeFetch {

    private static final String TAG = "RecipeFetch";

    public Recipe fetchRecipe(Long recipeId) {

        Recipe recipe = new Recipe();
        try {
            String url = Uri.parse("http://10.0.2.2:8080/m/recipes/" + recipeId)
                    .buildUpon()
                    .build().toString();
            String jsonString = getData(url);
            Log.i(TAG, "Received JSON: " + jsonString);

            JSONObject jsonBody = new JSONObject(jsonString);
            parseRecipe(recipe, jsonBody);


        }catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch recipes", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return recipe;
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

    private void parseRecipe(Recipe recipe, JSONObject jsonBody)
            throws JSONException {
        try {

            recipe.setId(jsonBody.getLong("id"));
            recipe.setRecipeName(jsonBody.getString("recipeName"));
            recipe.setRecipeGroup(jsonBody.getString("recipeGroup"));
            recipe.setIngredients(jsonBody.getString("ingredients"));
            recipe.setInstructions(jsonBody.getString("instructions"));
            recipe.setUsername(jsonBody.getString("username"));
            //recipe.setRate(jsonBody.getString("rate"));
            recipe.setImage(jsonBody.getString("image"));

        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
    }
}