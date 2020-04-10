package com.dev5151.howdyfoodie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dev5151.howdyfoodie.Models.Recipes;
import com.dev5151.howdyfoodie.Models.ResponseModel;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {
    private FoodRepository foodRepository;
    private FoodDBRepository foodDBRepository;
    private LiveData<ResponseModel> responseModelLiveData;
    private LiveData<List<Recipes>>recipesList;
    private LiveData<List<Recipes>>recipes;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        foodRepository = new FoodRepository(application);
        foodDBRepository=new FoodDBRepository(application);
        this.responseModelLiveData = foodRepository.getAllRecipes(20, "babaffde58854dc3860e91ef6e3bf00b");
        this.recipesList=foodDBRepository.getAllRecipes();

    }
    public LiveData<ResponseModel> getResponseModelLiveData() {
        return responseModelLiveData;
    }

    public LiveData<List<Recipes>> getRecipes(){
        return recipesList;
    }

    public void insertAll(List<Recipes>recipes){
        foodDBRepository.insertRecipes(recipes);
    }

    public  void  deleteAllRecipes(){
        foodDBRepository.deleteAllRecipes();
    }


}
