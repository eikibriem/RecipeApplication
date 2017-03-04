package com.example.helen_000.recipeapplication;

import android.net.Uri;
import android.util.Log;

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
 * Created by helen_000 on 3.3.2017.
 */

//ÞAÐ VANTAR EINHVERJA WHILE LYKKJU HÉRNA ÚTAF VIÐ FÁUM FLEIRI EN EINA NIÐUSTÖÐU

public class RecipeGroupFetch {
    private static final String TAG = "RecipeGroupFetch";



    public List<RecipeGroup> fetchRecipeGroups() {
        List<RecipeGroup> recipeGroups = new ArrayList<RecipeGroup>();
        try {
            String url = Uri.parse("http://10.0.2.2:8080/m/recipeGroupsList/")
                    .buildUpon()
                    .build().toString();
            String jsonString = getData(url);
          //  String jsonStringWithLastCharachter = jsonStringUnTrimmed.substring(1);
            //String jsonString = jsonStringWithLastCharachter.substring(0,jsonStringWithLastCharachter.length()-1);

            Log.i(TAG, "Received JSON: " + jsonString);

            JSONArray jsonBody = new JSONArray(jsonString);
            parseRecipeGroup(recipeGroups, jsonBody);

        }catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch recipes", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return recipeGroups;
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


    private void parseRecipeGroup(List<RecipeGroup> recipeGroups, JSONArray jsonBody)
            throws JSONException {
        try {

             for(int i = 0; i < jsonBody.length(); i++){

                 JSONObject groupJsonObject = jsonBody.getJSONObject(i);

                 RecipeGroup recipeGroup = new RecipeGroup();

                 recipeGroup.setGroupName(groupJsonObject.getString("groupName"));

                 recipeGroups.add(recipeGroup);
            }

        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
    }

}
