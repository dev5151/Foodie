package com.dev5151.howdyfoodie.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dev5151.howdyfoodie.Adapters.RecipeAdapter;
import com.dev5151.howdyfoodie.FoodViewModel;
import com.dev5151.howdyfoodie.Interfaces.FoodApi;
import com.dev5151.howdyfoodie.Interfaces.FoodDao;
import com.dev5151.howdyfoodie.Interfaces.ItemClickListener;
import com.dev5151.howdyfoodie.R;
import com.dev5151.howdyfoodie.Models.Recipes;
import com.dev5151.howdyfoodie.Models.ResponseModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FoodViewModel foodViewModel;
    List<Recipes> recipes;
    public FoodDao foodDao;
    RecipeAdapter recipeAdapter;
    private RecyclerView recyclerView;
    FoodApi foodApi;
    ItemClickListener itemClickListener;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        recipes = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_bar);

        setupNetworkListener();

    }

    private void getRecipesFromServer() {
        foodViewModel.getResponseModelLiveData().observe(MainActivity.this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel responseModel) {
                List<Recipes> recipeList = responseModel.getRecipesList();
                recipes.clear();
                recipes.addAll(recipeList);
                foodViewModel.deleteAllRecipes();
                foodViewModel.insertAll(recipes);
                progressBar.setVisibility(View.INVISIBLE);
                setRecipeAdapter(recipes);
            }

        });
    }

    private void fetchFromDb() {
        foodViewModel.getRecipes().observe(MainActivity.this, new Observer<List<Recipes>>() {
            @Override
            public void onChanged(List<Recipes> recipesList) {
                recipes.addAll(recipesList);

                setRecipeAdapter(recipes);
            }
        });
    }

    private void setupNetworkListener() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "INTERNET AVAILABLE", Toast.LENGTH_LONG).show();
                        getRecipesFromServer();
                    }
                });
            }

            @Override
            public void onLost(Network network) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "INTERNET NOT AVAILABLE", Toast.LENGTH_LONG).show();
                        fetchFromDb();
                    }
                });
            }

            @Override
            public void onUnavailable() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "INTERNET NOT AVAILABLE", Toast.LENGTH_LONG).show();
                        fetchFromDb();
                    }
                });
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback);
        }
    }

    private void setRecipeAdapter(List<Recipes> recipeList) {
        itemClickListener = (Recipes recipe) -> {
            Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
            intent.putExtra("title", recipe.getId());
            intent.putExtra("image", recipe.getImage());
            intent.putExtra("summary", recipe.getSummary());
            intent.putExtra("recipe", recipe.getInstructions());


            startActivity(intent);
        };
        recipeAdapter = new RecipeAdapter(recipeList, itemClickListener);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


}
