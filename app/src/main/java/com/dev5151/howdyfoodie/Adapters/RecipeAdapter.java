package com.dev5151.howdyfoodie.Adapters;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dev5151.howdyfoodie.Interfaces.ItemClickListener;
import com.dev5151.howdyfoodie.Models.Recipes;
import com.dev5151.howdyfoodie.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipes> recipeList;
    private ItemClickListener itemClickListener;

    public RecipeAdapter(List<Recipes> recipeList, ItemClickListener itemClickListener) {
        this.recipeList = recipeList;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeAdapter.RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipes recipes = recipeList.get(position);
        holder.title.setText(recipes.getTitle());
        Glide.with(holder.itemView.getContext()).load(recipes.getImage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClick(recipes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;
        private TextView time;
        private ProgressBar progressBar;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.tvTitle);
            time = itemView.findViewById(R.id.tvTime);
            progressBar = itemView.findViewById(R.id.progress_bar);


        }
    }
}
