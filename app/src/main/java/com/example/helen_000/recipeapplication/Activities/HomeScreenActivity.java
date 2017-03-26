package com.example.helen_000.recipeapplication.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import com.example.helen_000.recipeapplication.R;

public class HomeScreenActivity extends AppCompatActivity {
    private String TAG = "HomeScreenActivity";

    private String loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Button signUpButton = (Button) findViewById(R.id.mHomeScreenSignupButton);
        Button logInButton = (Button) findViewById(R.id.mHomeScreenLoginButton);
        Button myPageButton = (Button) findViewById(R.id.mHomeScreenMyPageButton);
        Button logOutButton = (Button) findViewById(R.id.mHomeScreenLogOutButton);
        Button createRecipeButton = (Button) findViewById(R.id.buttonCreateRecipe);
        SharedPreferences sharedPrefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String username = sharedPrefs.getString("userName", null);
        if(username != null){
            signUpButton.setVisibility(View.GONE);
            logInButton.setVisibility(View.GONE);
        }
        if(username == null){
            myPageButton.setVisibility(View.GONE);
            logOutButton.setVisibility(View.GONE);
            createRecipeButton.setVisibility(View.GONE);
        }

    }
    @Override
    public void onBackPressed() {
    }

    public void mHomeScreenRecipeGroupsButtonOnClick (View v){
        Intent intent = new Intent(HomeScreenActivity.this, RecipeGroupActivity.class);
        startActivity(intent);
    }
    public void mHomeScreenSearchButtonOnClick (View v){
        Intent intent = new Intent(HomeScreenActivity.this, SearchActivity.class);
        startActivity(intent);
    }
    public void mHomeScreenMyPageButtonOnClick (View v){
        Intent intent = new Intent(HomeScreenActivity.this, UserPageActivity.class);
        startActivity(intent);
    }
    public void mHomeScreenLoginButtonOnClick (View v){
        Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void mHomeScreenSignupButtonOnClick (View v){
        Intent intent = new Intent(HomeScreenActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    public void mHomeScreenCreateRecipeButtonOnClick (View v){
        Intent intent = new Intent(HomeScreenActivity.this, UploadRecipeActivity.class);
        intent.putExtra("operation", 0L);
        startActivity(intent);
    }

    public void mHomeScreenLogOutButtonOnClick (View v){
        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putString("userName", null);
        editor.putString("password", null);
        editor.commit();
        Intent intent = new Intent(HomeScreenActivity.this, HomeScreenActivity.class);
        startActivity(intent);
    }


}
