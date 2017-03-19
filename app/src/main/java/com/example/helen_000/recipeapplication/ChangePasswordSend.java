package com.example.helen_000.recipeapplication;

import android.net.Uri;
import android.util.Log;

import com.example.helen_000.recipeapplication.Entities.User;
import com.google.gson.Gson;

/**
 * Created by sandragunnarsdottir on 19/03/17.
 */

public class ChangePasswordSend {
    private static final String TAG = "ChangePasswordSend";
    public String sendChangePassword(User user){
        try{
            String url = Uri.parse("http://10.0.2.2:8080/m/changepassword")
                    .buildUpon()
                    .build().toString();
            Gson gson = new Gson();
            String json = gson.toJson(user);
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
