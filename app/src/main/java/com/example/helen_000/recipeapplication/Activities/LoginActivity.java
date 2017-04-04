package com.example.helen_000.recipeapplication.Activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;

import android.view.View;

import android.widget.EditText;

import android.widget.Toast;

import com.example.helen_000.recipeapplication.Entities.LoginUser;
import com.example.helen_000.recipeapplication.LoginSend;
import com.example.helen_000.recipeapplication.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by sandragunnarsdottir on 12/03/17.
 */
public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {
    private static final String TAG = "LoginActivity";

    @NotEmpty
    @Length(min = 4, max = 32)
    private EditText usernameEdit;
    @Password(min = 4)
    private EditText passwordEdit;

    private Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
        this.usernameEdit = (EditText) findViewById(R.id.editTextUsername);
        this.passwordEdit = (EditText) findViewById(R.id.editTextPassword);


    }

    public void mButtonLogInUserOnClick (View v){
        validator.validate();
    }

    private class LoginSendTask extends AsyncTask<LoginUser, Void, String> {
        @Override
        protected String doInBackground(LoginUser... loggedinUsers) {
            if(loggedinUsers.length>0){
                String message = new LoginSend().sendLogin(loggedinUsers[0]);
                return message;
            }
            return "Doesnt work, there is no user";
        }

        @Override
        protected void onPostExecute(String response){
            Log.d(TAG, response);
            if("OK".equals(response)){
                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putString("userName", usernameEdit.getText().toString());
                editor.putString("password", passwordEdit.getText().toString());
                editor.commit();
                Toast toast = Toast.makeText(LoginActivity.this ,"Log in successful", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                Log.d(TAG, "GilmoreGirlsÁHomeScreen: " + usernameEdit.getText().toString());
                intent.putExtra("userName", usernameEdit.getText().toString());
                startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(LoginActivity.this ,response, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                usernameEdit.setError(response);
                passwordEdit.setError(response);
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserName(usernameEdit.getText().toString());
        loginUser.setPassword(passwordEdit.getText().toString());

        AsyncTask task = new LoginSendTask().execute(loginUser);
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
}

