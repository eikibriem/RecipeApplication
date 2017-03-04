package com.example.helen_000.recipeapplication.API;

import com.example.helen_000.recipeapplication.Entities.Recipe;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by helen_000 on 3.3.2017.
 */

public interface RecipeAPI {


   // @GET("/mej3hi/tonlistv2/master/tonlist.json")
    // Call<List<Recipe>> getEvent();

    @GET("/m/")
    Call<List<Recipe>> getIndex();

   // @POST("/m/createRecipe")
    //Call<Void> postCreateEvent(@Body Recipe recipe, @Part MultipartBody.Part image, @Part("name") RequestBody name);

    //@GET("/m/editRecipe")
    //Call<Void> getEditRecipe(@Body Recipe recipe, @Part MultipartBody.Part image, @Part("name") RequestBody name);


}
