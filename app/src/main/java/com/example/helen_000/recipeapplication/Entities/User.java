package com.example.helen_000.recipeapplication.Entities;

/**
 * Created by sandragunnarsdottir on 12/03/17.
 */

public class User {
    public User(){

    }

    private String name;
    private String userName;
    private String password;
    private String passwordConfirm;
    private String oldPassword;
    private String email;

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getUsername(){
        return this.userName;
    }

    public void setUsername(String username){
        this.userName = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPasswordConfirm(){
        return this.passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm){
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getOldPassword(){
        return this.oldPassword;
    }

    public void setOldPassword(String oldPassword){
        this.oldPassword = oldPassword;
    }
}
