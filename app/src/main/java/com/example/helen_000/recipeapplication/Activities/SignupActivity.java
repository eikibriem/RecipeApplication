package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helen_000.recipeapplication.Entities.User;
import com.example.helen_000.recipeapplication.R;
import com.example.helen_000.recipeapplication.SignupSend;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SignupActivity extends AppCompatActivity implements Validator.ValidationListener {
    private static final String TAG = "SignupActivity";
    @NotEmpty
    private EditText nameEdit;
    @NotEmpty
    @Length(min = 4, max = 32)
    private EditText usernameEdit;
    @Password(min = 4)
    private EditText passwordEdit;
    @ConfirmPassword
    private EditText passwordConfirmEdit;
    @Email
    @NotEmpty
    private EditText emailEdit;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
        this.nameEdit = (EditText) findViewById(R.id.editTextName);
        this.usernameEdit = (EditText) findViewById(R.id.editTextUsername);
        this.passwordEdit = (EditText) findViewById(R.id.editTextPassword);
        this.passwordConfirmEdit = (EditText) findViewById(R.id.editTextPasswordConfirm);
        this.emailEdit = (EditText) findViewById(R.id.editTextEmail);
    }


    public void mButtonSaveUserOnClick (View v){
        validator.validate();


    }

    private class SignupSendTask extends AsyncTask<User, Void, String> {
        //doInBackground getur tekið inn marga usera, en við tökum bara inn einn í einu
        //erum að athuga hvort aðgerðin hafi tekist með því að skoða tölu
        @Override
        protected String doInBackground(User... users){
            if(users.length > 0){
                String message = new SignupSend().sendSignup(users[0]);
                return message;
            }
            return "Doesnt work, there is no user";

        }
        @Override
        protected void onPostExecute(String response){
            Log.d(TAG,response.toString());
            if("OK".equals(response)){

                Toast toast = Toast.makeText(SignupActivity.this ,"Sign up successful", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else{
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getJSONArray("userName").length() > 0){
                        for(int i=0; i < jsonObject.getJSONArray("userName").length(); i++){
                            String errorMessage = jsonObject.getJSONArray("userName").getString(i);
                            usernameEdit.setError(errorMessage);
                        }
                    }

                    if(jsonObject.getJSONArray("name").length() > 0){
                        for(int i=0; i < jsonObject.getJSONArray("name").length(); i++){
                            String errorMessage = jsonObject.getJSONArray("name").getString(i);
                            nameEdit.setError(errorMessage);
                        }
                    }

                    if(jsonObject.getJSONArray("password").length() > 0){
                        for(int i=0; i < jsonObject.getJSONArray("password").length(); i++){
                            String errorMessage = jsonObject.getJSONArray("password").getString(i);
                            passwordEdit.setError(errorMessage);
                        }
                    }

                    if(jsonObject.getJSONArray("passwordConfirm").length() > 0){
                        for(int i=0; i < jsonObject.getJSONArray("passwordConfirm").length(); i++){
                            String errorMessage = jsonObject.getJSONArray("passwordConfirm").getString(i);
                            passwordConfirmEdit.setError(errorMessage);
                        }
                    }

                    if(jsonObject.getJSONArray("email").length() > 0){
                        for(int i=0; i < jsonObject.getJSONArray("email").length(); i++){
                            String errorMessage = jsonObject.getJSONArray("email").getString(i);
                            emailEdit.setError(errorMessage);
                        }
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "Error when handling json");
                    Toast toast = Toast.makeText(SignupActivity.this, "Sign up not successful, try again later", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
        User user = new User();
        user.setName(nameEdit.getText().toString());
        user.setUsername(usernameEdit.getText().toString());
        user.setPassword(passwordEdit.getText().toString());
        user.setPasswordConfirm(passwordConfirmEdit.getText().toString());
        user.setEmail(emailEdit.getText().toString());

        //Bua til SignupSendTask og keyra það fyrir user
        AsyncTask task = new SignupSendTask().execute(user);
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
