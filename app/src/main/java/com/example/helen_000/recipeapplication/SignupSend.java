package com.example.helen_000.recipeapplication;

import android.net.Uri;
import android.util.Log;

import com.example.helen_000.recipeapplication.Entities.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sandragunnarsdottir on 12/03/17.
 */

public class SignupSend {
    private static final String TAG = "SignupSend";
    public String sendSignup(User user){
        try{
            String url = Uri.parse("http://10.0.2.2:8080/m/createuser/")
                    .buildUpon()
                    .build().toString();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            String response = postData(url, json);

            Log.i(TAG, "Received response:" + response);

            return response;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "Doesnt work";
    }

    private String postData(String urlSpec, String json) {
        BufferedReader reader = null;
        try{
            URL url = new URL(urlSpec);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            //http://stackoverflow.com/questions/20020902/android-httpurlconnection-how-to-set-post-data-in-http-body
            //http://guruparang.blogspot.is/2016/01/example-on-working-with-json-and.html
            if(json != null){
                //set the content length of the body
                conn.setRequestProperty("Content-length", json.getBytes().length + "");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);

                //send the json as body of the request
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(json.getBytes("UTF-8"));
                outputStream.close();
            }
            conn.connect();
            int responseNo = conn.getResponseCode();
            if(responseNo == 200){
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                    Log.d("Response: ", "> " + line); //Here you get the whole response
                }

                String response = sb.toString();
                if("OK\n".equals(response)) {

                    return "OK";
                }
                else{
                    return response;
                }
            }
            else{
                return "NOT OK";
            }
            //return conn.getResponseCode();

        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "Doesnt work";
    }


}
