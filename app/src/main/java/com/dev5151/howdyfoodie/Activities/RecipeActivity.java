package com.dev5151.howdyfoodie.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev5151.howdyfoodie.FoodViewModel;
import com.dev5151.howdyfoodie.Models.Recipes;
import com.dev5151.howdyfoodie.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    TextView tvTitle, tvSummary, tvRecipe;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        tvTitle = findViewById(R.id.tvTitle);
        tvSummary = findViewById(R.id.tvSummary);
        tvRecipe = findViewById(R.id.tvRecipe);
        image = findViewById(R.id.image);

        fetchData();

    }

    private void fetchData() {
        Intent intent = getIntent();
        tvTitle.setText(intent.getStringExtra("name"));
        tvSummary.setText(Html.fromHtml(intent.getStringExtra("summary")));
        tvRecipe.setText(Html.fromHtml(intent.getStringExtra("recipe")));
        Glide.with(getApplicationContext()).load(intent.getStringExtra("image")).into(image);
    }
}
