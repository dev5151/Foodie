package com.dev5151.howdyfoodie.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;

import com.dev5151.howdyfoodie.FoodViewModel;
import com.dev5151.howdyfoodie.Interfaces.FoodDao;
import com.dev5151.howdyfoodie.R;
import com.dev5151.howdyfoodie.Models.Recipes;
import com.dev5151.howdyfoodie.Models.ResponseModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FoodViewModel foodViewModel;
    List<Recipes> recipes;
    public FoodDao foodDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        recipes = new ArrayList<>();
        //setupNetworkListener();
        getRecipes();

       /* foodViewModel.getRecipes().observe(MainActivity.this, new Observer<List<Recipes>>() {
            @Override
            public void onChanged(List<Recipes> recipesList) {
                recipes.addAll(recipesList);
            }
        });*/
    }

    private void getRecipes() {
        foodViewModel.getResponseModelLiveData().observe(MainActivity.this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel responseModel) {
                List<Recipes> recipeList = responseModel.getRecipesList();
                recipes.addAll(recipeList);
                foodViewModel.insertAll(recipes);
            }

        });
    }

    private void setupNetworkListener() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                getRecipes();
            }

            @Override
            public void onLost(Network network) {
                foodViewModel.getRecipes().observe(MainActivity.this, new Observer<List<Recipes>>() {
                    @Override
                    public void onChanged(List<Recipes> recipesList) {
                        recipes.addAll(recipesList);
                    }
                });
            }

            @Override
            public void onUnavailable() {
                foodViewModel.getRecipes().observe(MainActivity.this, new Observer<List<Recipes>>() {
                    @Override
                    public void onChanged(List<Recipes> recipesList) {
                        recipes.addAll(recipesList);
                    }
                });
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback);
        }
    }
}
