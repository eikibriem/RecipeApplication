package com.example.helen_000.recipeapplication;

import android.net.Uri;
import android.util.Log;

import com.example.helen_000.recipeapplication.Entities.LoginUser;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by sandragunnarsdottir on 15/03/17.
 */

public class LoginSend {
    private static final String TAG = "LoginSend";
    public String sendLogin(LoginUser loggedinUser){
        try{

            String url = Uri.parse("http://10.0.2.2:8080/m/login/")
                    .buildUpon()
                    .build().toString();
            Gson gson = new Gson();
            String json = gson.toJson(loggedinUser);
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
