package com.example.helen_000.recipeapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helen_000.recipeapplication.ChangePasswordSend;
import com.example.helen_000.recipeapplication.Entities.User;
import com.example.helen_000.recipeapplication.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class ChangePasswordActivity extends AppCompatActivity implements Validator.ValidationListener {
    private static final String TAG = "ChangePasswordActivity";
    @NotEmpty
    @Password
    @Length(min = 4)
    private EditText changePassword;
    @ConfirmPassword
    private EditText changePasswordAgain;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
        this.changePassword = (EditText) findViewById(R.id.editTextNewPassword);
        this.changePasswordAgain = (EditText) findViewById(R.id.editTextNewPasswordAgain);
    }

    public void mButtonChangePasswordOnClick (View v){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        SharedPreferences sharedPrefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String username = sharedPrefs.getString("userName", null);
        String oldpassword = sharedPrefs.getString("password", null);
        String password = this.changePassword.getText().toString();
        String passwordagain = this.changePasswordAgain.getText().toString();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirm(passwordagain);
        user.setOldPassword(oldpassword);

        AsyncTask task = new ChangePasswordSendTask().execute(user);

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

    private class ChangePasswordSendTask extends AsyncTask<User, Void, String>{
        @Override
        protected String doInBackground(User... users){
            if(users.length>0){
                String message = new ChangePasswordSend().sendChangePassword(users[0]);
                return message;
            }
            return "Doesnt work, there is no user";
        }

        @Override
        protected void onPostExecute(String response){
            Log.d(TAG, response);
            if("OK".equals(response)){
                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putString("password", changePassword.getText().toString());
                editor.commit();
                Toast toast = Toast.makeText(ChangePasswordActivity.this ,"Change successful", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(ChangePasswordActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
            else{
                Toast toast = Toast.makeText(ChangePasswordActivity.this ,"Not successful, try again later", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }


}
