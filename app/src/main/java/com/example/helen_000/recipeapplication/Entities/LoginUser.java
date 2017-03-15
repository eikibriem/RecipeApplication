package com.example.helen_000.recipeapplication.Entities;

/**
 * Created by sandragunnarsdottir on 15/03/17.
 */

public class LoginUser {
    public LoginUser(){

    }

    private String userName;
    private String password;

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }


}
