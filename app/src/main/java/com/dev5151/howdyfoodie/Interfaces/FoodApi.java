package com.dev5151.howdyfoodie.Interfaces;

import com.dev5151.howdyfoodie.Models.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodApi {

    @GET("/recipes/random")
    Call<ResponseModel> getRandomRecipes(@Query("number") int number,
                                         @Query("apiKey") String apiKey);

}
