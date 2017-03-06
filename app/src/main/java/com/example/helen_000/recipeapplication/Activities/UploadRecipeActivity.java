package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.helen_000.recipeapplication.Entities.Recipe;
import com.example.helen_000.recipeapplication.R;

public class UploadRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipe);
        Spinner dropdown = (Spinner)findViewById(R.id.spinnerRecipeGroup);
        String[] items = new String[]{"Appetizer", "Baking", "Breakfast", "Dinner", "Dessert", "Raw"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    public void getRecipeName(View v){
        EditText recipeName = (EditText) findViewById(R.id.editTextRecipeName);
        recipeName.getText().toString();
        Log.d("AddRecipe","Recipe Name: "+recipeName);
    }

    public void getRecipeGroupView(View v){
        Spinner recipeGroup = (Spinner)findViewById(R.id.spinnerRecipeGroup);
        String recipeGroupValue = recipeGroup.getSelectedItem().toString();
        Log.d("AddRecipeGroup","RecipeGroup Name: "+recipeGroupValue);
    }

    public void getIngredients(View v){
        EditText ingredients = (EditText) findViewById(R.id.editTextIngredients);
        ingredients.getText().toString();
        Log.d("AddIngredients","Ingredients: "+ingredients);
    }

    public void getInstructions(View v){
        EditText instructions = (EditText) findViewById(R.id.editTextInstructions);
        instructions.getText().toString();
        Log.d("AddInstructions","instructions:  "+instructions);
    }

    public void getImage(View v){
        EditText image = (EditText) findViewById(R.id.editTextImage);
        image.getText().toString();
        Log.d("AddImage","image:  "+image);
    }

    public void mButtonSaveRecipeOnClick (String recipeName, String recipeGroup, String ingredients, String instructions, String image){
        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeName);
        recipe.setRecipeGroup(recipeGroup);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setRate(0);
        recipe.setImage(image);
        //recipe.setUsername(??Sækja username??);

        //recipe.save();
    }
}
