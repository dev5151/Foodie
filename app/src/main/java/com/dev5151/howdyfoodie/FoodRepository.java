package com.dev5151.howdyfoodie;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dev5151.howdyfoodie.Interfaces.FoodApi;
import com.dev5151.howdyfoodie.Interfaces.FoodDao;
import com.dev5151.howdyfoodie.Models.Recipes;
import com.dev5151.howdyfoodie.Models.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRepository {

    private FoodDao foodDao;
    private LiveData<List<Recipes>> allRecipes;
    private FoodApi foodApi;
    Application application;

    public FoodRepository(Application application) {
        foodApi = RetrofitService.getRetrofitInstance().create(FoodApi.class);
        this.application = application;
    }

    LiveData<ResponseModel> getAllRecipes(int number, String apiKey) {
        final MutableLiveData<ResponseModel> data = new MutableLiveData<>();
        foodApi.getRandomRecipes(number, apiKey).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                    FoodDBRepository foodDBRepository = new FoodDBRepository(application);
                    foodDBRepository.insertRecipes(response.body().getRecipesList());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("Fail", "onFailure: " + t.getMessage());
            }
        });

        return data;
    }



}
