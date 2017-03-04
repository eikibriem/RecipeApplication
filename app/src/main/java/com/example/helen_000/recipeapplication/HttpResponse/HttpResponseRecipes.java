package com.example.helen_000.recipeapplication.HttpResponse;


import com.example.helen_000.recipeapplication.Entities.Recipe;

import java.util.List;

/**
 * Created by helen_000 on 3.3.2017.
 */

public class HttpResponseRecipes {
    private List<Recipe> recipeList;
    private int code;

    public HttpResponseRecipes(List<Recipe> recipeList, int code) {
        this.recipeList = recipeList;
        this.code = code;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
