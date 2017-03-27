package com.example.helen_000.recipeapplication;

import android.net.Uri;
import android.util.Log;

import com.example.helen_000.recipeapplication.Entities.Recipe;
import com.google.gson.Gson;

/**
 * Created by sandragunnarsdottir on 15/03/17.
 */

public class CreateRecipeSend {
    private static final String TAG = "CreateRecipeSend";
    public String sendRecipe(Recipe recipe){
        try{
            String url = Uri.parse("http://10.0.2.2:8080/m/createRecipe/")
                    .buildUpon()
                    .build().toString();
            Gson gson = new Gson();
            String json = gson.toJson(recipe);
            String response = PostData.postData(url,json);

            Log.i(TAG, "Received response:" + response);
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "Doesnt work";
    }


}
