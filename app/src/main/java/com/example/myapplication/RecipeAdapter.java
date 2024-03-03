package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    DatabaseReference databaseReference;
    private ArrayList<Recipe> recipeArrayList;
    private Context context;
    private RecipeClickInterface recipeClickInterface;
    int lastPos = -1;

    public RecipeAdapter(ArrayList<Recipe> recipeArrayList, Context context, RecipeClickInterface recipeClickInterface, DatabaseReference databaseReference) {
        this.recipeArrayList = recipeArrayList;
        this.context = context;
        this.recipeClickInterface = recipeClickInterface;
        this.databaseReference = databaseReference;
    }

    @NonNull
    public  RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Recipe recipe = recipeArrayList.get(position);

        holder.recipeName.setText(recipe.getName());
        holder.recipeCalories.setText(recipe.getCalories() + " ккал.");
        setAnimation(holder.itemView, position);
        holder.recipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeClickInterface.onRecipeClick(position);

            }
        });

    }

    private void setAnimation(View itemView, int position){
        if (position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }


    public int getItemCount() {
        return recipeArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeName, recipeCalories;
        private FloatingActionButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipeName);
            recipeCalories = itemView.findViewById(R.id.recipeCalories);

        }
    }

    public interface RecipeClickInterface{
        void onRecipeClick(int position);
    }



}
