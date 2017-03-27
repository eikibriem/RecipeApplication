package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.helen_000.recipeapplication.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void mButtonSearchOnClick (View v){
        EditText input = (EditText) findViewById(R.id.editTextSearch);
        String searchInput = input.getText().toString();
        Intent intent = new Intent(SearchActivity.this, RecipeListActivity.class);
        intent.putExtra("searchMessage", searchInput);
        startActivity(intent);
    }
}
