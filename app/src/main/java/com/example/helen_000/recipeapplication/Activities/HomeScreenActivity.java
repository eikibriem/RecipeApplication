package com.example.helen_000.recipeapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.example.helen_000.recipeapplication.R;

public class HomeScreenActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }
   // Button mHomeScreenRecipeGroupsButton = (Button)findViewById(R.id.mHomeScreenRecipeGroupsButton);

    public void mHomeScreenRecipeGroupsButtonOnClick (View v){
        Intent intent = new Intent(HomeScreenActivity.this, RecipeGroupActivity.class);
        startActivity(intent);
    }
    public void mHomeScreenSearchButtonOnClick (View v){
        Intent intent = new Intent(HomeScreenActivity.this, RecipeGroupActivity.class);
        startActivity(intent);
    }
    public void mHomeScreenLoginButtonOnClick (View v){
        Intent intent = new Intent(HomeScreenActivity.this, RecipeGroupActivity.class);
        startActivity(intent);
    }
    public void mHomeScreenSignupButtonOnClick (View v){
        Intent intent = new Intent(HomeScreenActivity.this, RecipeGroupActivity.class);
        startActivity(intent);
    }
/*
    mHomeScreenRecipeGroupsButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(HomeScreenActivity.this, RecipeGroupActivity.class));
            finish();
        }
    });
*/
}
