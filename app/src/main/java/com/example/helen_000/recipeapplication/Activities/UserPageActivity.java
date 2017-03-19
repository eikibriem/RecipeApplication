package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.helen_000.recipeapplication.R;

public class UserPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
    }

    public void mButtonChangePasswordOnClick (View v){
        Intent intent = new Intent(UserPageActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
