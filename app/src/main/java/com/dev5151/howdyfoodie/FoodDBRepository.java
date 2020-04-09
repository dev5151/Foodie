package com.dev5151.howdyfoodie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dev5151.howdyfoodie.Interfaces.FoodDao;
import com.dev5151.howdyfoodie.Models.Recipes;

import java.util.List;

public class FoodDBRepository {
    private FoodDao foodDao;
    LiveData<List<Recipes>> allRecipes;
    Application application;

    public FoodDBRepository(Application application) {
        FoodDatabase foodDatabase = FoodDatabase.getInstance(application);
        foodDao = foodDatabase.foodDao();
        allRecipes = foodDao.getAllRecipes();
        this.application = application;
    }

    public LiveData<List<Recipes>> getAllRecipes() {
        return allRecipes;
    }

    public void insertRecipes(List<Recipes> recipesList) {
        new insertAsyncTask(foodDao).execute(recipesList);
    }

    public void deleteAllRecipes() {
        new DeleteAllFoodAsyncTask(foodDao).execute();
    }


    private static class insertAsyncTask extends AsyncTask<List<Recipes>, Void, Void> {

        private FoodDao asyncTaskDao;

        insertAsyncTask(FoodDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(List<Recipes>... lists) {
            asyncTaskDao.insertRecipes(lists[0]);
            return null;
        }
    }

    private static class DeleteAllFoodAsyncTask extends AsyncTask<Void, Void, Void> {
        private FoodDao foodDao;

        public DeleteAllFoodAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            foodDao.deleteAllRecipes();
            return null;
        }
    }

}
