package com.dev5151.howdyfoodie.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {

    @SerializedName("recipes")
    List<Recipes> recipesList;

    public ResponseModel(List<Recipes> recipesList) {
        this.recipesList = recipesList;
    }

    public List<Recipes> getRecipesList() {
        return recipesList;
    }

    public void setRecipesList(List<Recipes> recipesList) {
        this.recipesList = recipesList;
    }
}
