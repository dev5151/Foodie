package com.dev5151.howdyfoodie.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dev5151.howdyfoodie.Models.Recipes;
import com.dev5151.howdyfoodie.Models.ResponseModel;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipes(List<Recipes> recipes);


    @Query("SELECT * FROM `RECIPE TABLE`")
    LiveData<List<Recipes>> getAllRecipes();

    @Query("DELETE FROM `Recipe Table`")
    void deleteAllRecipes();
}
