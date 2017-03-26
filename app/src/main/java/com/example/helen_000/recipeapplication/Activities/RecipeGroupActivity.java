package com.example.helen_000.recipeapplication.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helen_000.recipeapplication.Entities.Recipe;
import com.example.helen_000.recipeapplication.Entities.RecipeGroup;
import com.example.helen_000.recipeapplication.R;
import com.example.helen_000.recipeapplication.RecipeFetch;
import com.example.helen_000.recipeapplication.RecipeGroupFetch;

import java.util.ArrayList;
import java.util.List;

public class RecipeGroupActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_group);
        AsyncTask task = new FetchRecipeGroupsTask().execute();


    }

    private List<RecipeGroup> mRecipeGroups = new ArrayList<RecipeGroup>();

    private class FetchRecipeGroupsTask extends AsyncTask<Void, Void, List<RecipeGroup>> {
        @Override
        protected List<RecipeGroup> doInBackground(Void... params ){
            List<RecipeGroup> recipeGroups = new RecipeGroupFetch().fetchRecipeGroups();
            return recipeGroups;
        }

        @Override
        protected void onPostExecute(List<RecipeGroup> recipeGroups) {
            mRecipeGroups = recipeGroups;

            Button buttonAppetizers = (Button) findViewById(R.id.mButtonAppetizers);
            buttonAppetizers.setText(mRecipeGroups.get(0).getGroupName());

            Button buttonBaking = (Button) findViewById(R.id.mButtonBaking);
            buttonBaking.setText(mRecipeGroups.get(1).getGroupName());

            Button buttonBreakfast = (Button) findViewById(R.id.mButtonBreakfast);
            buttonBreakfast.setText(mRecipeGroups.get(2).getGroupName());

            Button buttonDessert = (Button) findViewById(R.id.mButtonDessert);
            buttonDessert.setText(mRecipeGroups.get(3).getGroupName());

            Button buttonDinner = (Button) findViewById(R.id.mButtonDinner);
            buttonDinner.setText(mRecipeGroups.get(4).getGroupName());

            Button buttonRaw = (Button) findViewById(R.id.mButtonRaw);
            buttonRaw.setText(mRecipeGroups.get(5).getGroupName());

        }
    }

    public void mButtonAppetizersButtonOnClick (View v){
        Intent intent = new Intent(RecipeGroupActivity.this, RecipeListActivity.class);
        String message = "appetizers";
        intent.putExtra("message",  message);
        startActivity(intent);
    }

    public void mButtonBakingButtonOnClick (View v){
        Intent intent = new Intent(RecipeGroupActivity.this, RecipeListActivity.class);
        String message = "baking";
        intent.putExtra("message",  message);
        startActivity(intent);
    }

    public void mButtonBreakfastButtonOnClick (View v){
        Intent intent = new Intent(RecipeGroupActivity.this, RecipeListActivity.class);
        String message = "breakfast";
        intent.putExtra("message",  message);
        startActivity(intent);
    }

    public void mButtonDinnerButtonOnClick (View v){
        Intent intent = new Intent(RecipeGroupActivity.this, RecipeListActivity.class);
        String message = "dinner";
        intent.putExtra("message",  message);
        startActivity(intent);
    }

    public void mButtonDessertButtonOnClick (View v){
        Intent intent = new Intent(RecipeGroupActivity.this, RecipeListActivity.class);
        String message = "dessert";
        intent.putExtra("message",  message);
        startActivity(intent);
    }

    public void mButtonRawButtonOnClick (View v){
        Intent intent = new Intent(RecipeGroupActivity.this, RecipeListActivity.class);
        String message = "raw";
        intent.putExtra("message",  message);
        startActivity(intent);
    }


}


