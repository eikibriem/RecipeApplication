package com.example.helen_000.recipeapplication;


/**
 * Created by helen_000 on 28.2.2017.
 */

public class Recipe {
    private Long id;
    private String recipeName;
    private String recipeGroup;
    private String ingredients;
    //private User user;
    private String instructions;
    private String image;
    private float rate;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeGroup() {
        return recipeGroup;
    }
    public void setRecipeName(String recipeGroup) {
        this.recipeGroup = recipeGroup;
    }

    public String getIngredients() {
        return ingredients;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }

}

