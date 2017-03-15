package com.example.helen_000.recipeapplication;

import android.util.Log;

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

public class PostData {
    public static String postData(String urlSpec, String json) {
        BufferedReader reader = null;
        try{
            URL url = new URL(urlSpec);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            if(json != null){
                conn.setRequestProperty("Content-length", json.getBytes().length + "");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);

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
                    conn.disconnect();
                    return "OK";
                }
                else{
                    conn.disconnect();
                    return response;
                }
            }
            else{
                conn.disconnect();
                return "NOT OK";

            }


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Doesnt work";
    }
}
