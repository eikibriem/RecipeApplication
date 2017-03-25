package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.helen_000.recipeapplication.R;

public class UserPageActivity extends AppCompatActivity {
    private String TAG = "UserPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
    }

    public void mButtonChangePasswordOnClick (View v){
        Intent intent = new Intent(UserPageActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void mButtonMyRecipesOnClick (View v){
        Intent intent = new Intent(UserPageActivity.this, RecipeListActivity.class);

        String loggedInUser = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).getString("userName", "No logged in user");
        intent.putExtra("message",  loggedInUser);

        startActivity(intent);
    }
}
