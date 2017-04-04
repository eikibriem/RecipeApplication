package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.helen_000.recipeapplication.CreateRecipeSend;
import com.example.helen_000.recipeapplication.Entities.Recipe;
import com.example.helen_000.recipeapplication.R;
import com.example.helen_000.recipeapplication.RecipeDelete;
import com.example.helen_000.recipeapplication.RecipeFetch;
import com.example.helen_000.recipeapplication.RecipeFetchId;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UploadRecipeActivity extends AppCompatActivity implements Validator.ValidationListener{

    private static final String TAG = "CreateRecipe";

    @NotEmpty
    private EditText recipeNameEdit;
    private Spinner recipeGroupEdit;
    @NotEmpty
    private EditText ingredientsEdit;
    @NotEmpty
    private EditText instructionsEdit;
    @NotEmpty
    private EditText imageEdit;
    private Long message;
    private Long operationId;
    private Validator validator;

    private ArrayAdapter<String> adapter;

    private String operation = "createRecipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras(); //getting the id of the recipe from the recipeListActivity
        message = bundle.getLong("message"); //getting the id of the recipe from the recipeListActivity/UploadRecipeActivity
        operationId = bundle.getLong("operation"); //check if you are editing or saving a new recipe

        Log.d(TAG, "hér er id-ið á recipe-inu: " + message);




        setContentView(R.layout.activity_upload_recipe);
        Spinner dropdown = (Spinner)findViewById(R.id.spinnerRecipeGroup);
        String[] items = new String[]{"appetizers", "baking", "breakfast", "dinner", "dessert", "raw"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        Log.d(TAG, "aloha2");

        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
        this.recipeNameEdit = (EditText) findViewById(R.id.editTextRecipeName);
        this.recipeGroupEdit = (Spinner)findViewById(R.id.spinnerRecipeGroup);
        this.ingredientsEdit = (EditText) findViewById(R.id.editTextIngredients);
        this.instructionsEdit = (EditText) findViewById(R.id.editTextInstructions);
        this.imageEdit = (EditText) findViewById(R.id.editTextImage);
        Log.d(TAG, "aloha3");




        //Yes if you are editing a recipe
        if (operationId != 0L){
            operation = "editRecipe";
            Log.d(TAG, "aloha10");

            AsyncTask task = new UploadRecipeActivity.SendRecipeEditDeviceDetails().execute();
            Log.d(TAG, "aloha11");

            //Get position of spinner element


        }


    }
/*
    public String getRecipeName(View v){
        EditText recipeName = (EditText) findViewById(R.id.editTextRecipeName);
        String recipeNameValue = recipeName.getText().toString();

        Log.d("AddRecipe","Recipe Name: "+recipeName);

        return recipeNameValue;
    }

    public String getRecipeGroupView(View v){
        Spinner recipeGroup = (Spinner)findViewById(R.id.spinnerRecipeGroup);
        String recipeGroupValue = recipeGroup.getSelectedItem().toString();

        Log.d("AddRecipeGroup","RecipeGroup Name: "+recipeGroupValue);

        return recipeGroupValue;
    }

    public String getIngredients(View v){
        EditText ingredients = (EditText) findViewById(R.id.editTextIngredients);
        String ingredientsValue = ingredients.getText().toString();

        Log.d("AddIngredients","Ingredients: "+ingredients);

        return ingredientsValue;
    }

    public String getInstructions(View v){
        EditText instructions = (EditText) findViewById(R.id.editTextInstructions);
        String instructionsValue =  instructions.getText().toString();

        Log.d("AddInstructions","instructions:  "+instructions);

        return instructionsValue;
    }

    public String getImage(View v){
        EditText image = (EditText) findViewById(R.id.editTextImage);
        String imageValue = image.getText().toString();

        Log.d("AddImage","image:  "+image);

        return imageValue;

    }
*/
    public void mButtonSaveRecipeOnClick (View v){validator.validate();}



    /*
        Retrieves the recipe that you want to edit
     */
    private class SendRecipeEditDeviceDetails extends AsyncTask<Void, Void, Recipe> {
        @Override
        protected Recipe doInBackground(Void... params) {
            Recipe recipeToEdit = new RecipeFetch().fetchRecipe(operationId);
            return recipeToEdit;
        }

        @Override
        protected void onPostExecute(Recipe recipeToEdit) {

            String recipeGroup = recipeToEdit.getRecipeGroup();
            int selectionPosition= adapter.getPosition(recipeGroup);

            //Set the saved values of the recipe in the text views for the user to change
            recipeNameEdit.setText(recipeToEdit.getRecipeName());
            recipeGroupEdit.setSelection(selectionPosition);
            ingredientsEdit.setText(recipeToEdit.getIngredients());
            instructionsEdit.setText(recipeToEdit.getInstructions());
            imageEdit.setText(recipeToEdit.getImage());
        }
    }


    private class SendRecipeDeviceDetails extends AsyncTask<Recipe, Void, String> {

        @Override
        protected String doInBackground(Recipe... recipes) {

            if(recipes.length > 0){
                String message1 = new CreateRecipeSend().sendRecipe(recipes[0]);


                RecipeFetchId rp = new RecipeFetchId();
                Recipe RecipeidNew = rp.fetchRecipeId(recipes[0].getRecipeName());
                Long idNew = RecipeidNew.getId();
                message = idNew;


                return message1;
            }
            return "Doesnt work, there is no recipe";

        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            Log.e("TAG", response); // this is expecting a response code to be sent from your server upon receiving the POST data

            Log.d(TAG,response.toString());
            if("OK".equals(response)){

                Intent intent = new Intent(UploadRecipeActivity.this, RecipeActivity.class);
                intent.putExtra("message",  message);

                startActivity(intent);
                finish();

            }
            else{
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getJSONArray("recipeName").length() > 0){
                        for(int i=0; i < jsonObject.getJSONArray("recipeName").length(); i++){
                            String errorMessage = jsonObject.getJSONArray("recipeName").getString(i);
                            recipeNameEdit.setError(errorMessage);
                        }
                    }

                    if(jsonObject.getJSONArray("ingredients").length() > 0){
                        for(int i=0; i < jsonObject.getJSONArray("ingredients").length(); i++){
                            String errorMessage = jsonObject.getJSONArray("ingredients").getString(i);
                            ingredientsEdit.setError(errorMessage);
                        }
                    }

                    if(jsonObject.getJSONArray("instructions").length() > 0){
                        for(int i=0; i < jsonObject.getJSONArray("instructions").length(); i++){
                            String errorMessage = jsonObject.getJSONArray("instructions").getString(i);
                            instructionsEdit.setError(errorMessage);
                        }
                    }

                    if(jsonObject.getJSONArray("image").length() > 0){
                        for(int i=0; i < jsonObject.getJSONArray("image").length(); i++){
                            String errorMessage = jsonObject.getJSONArray("image").getString(i);
                            imageEdit.setError(errorMessage);
                        }
                    }


                } catch (JSONException e) {
                    Log.e(TAG, "Error when handling json");
                    Toast toast = Toast.makeText(UploadRecipeActivity.this, "Upload not successful, try again later", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    e.printStackTrace();
                }
            }
        }
        }


    @Override
    public void onValidationSucceeded() {
        if(operation.equals("editRecipe")){
            AsyncTask task = new UploadRecipeActivity.DeleteRecipeTask().execute();

        }
        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeNameEdit.getText().toString());
        recipe.setRecipeGroup(recipeGroupEdit.getSelectedItem().toString());
        recipe.setIngredients(ingredientsEdit.getText().toString());
        recipe.setInstructions(instructionsEdit.getText().toString());
        recipe.setImage(imageEdit.getText().toString());
        String loggedInUser = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).getString("userName", "No logged in user");
        recipe.setUsername(loggedInUser);
        new SendRecipeDeviceDetails().execute(recipe);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }



    private class DeleteRecipeTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params ){
            String returnMessage = new RecipeDelete().deleteRecipe(operationId);
            return returnMessage;
        }

        @Override
        protected void onPostExecute(String returnMessage) {
            Intent intent = new Intent(UploadRecipeActivity.this, HomeScreenActivity.class);
            startActivity(intent);
        }
    }

}

