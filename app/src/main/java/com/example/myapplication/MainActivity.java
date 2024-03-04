package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickInterface {

    private FloatingActionButton recipeActivityBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private ArrayList<Recipe> recipeArrayList;
    private RecipeAdapter recipeAdapter;
    private RelativeLayout relativeLayout;
    private FloatingActionButton delete;
    public static final int EDIT_EMPLOYEE_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        relativeLayout = findViewById(R.id.relativeLayout);
        recipeActivityBtn = findViewById(R.id.addRecipeActivityBtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        recipeArrayList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference("Recipe");
        recipeActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });

        recipeAdapter = new RecipeAdapter(recipeArrayList, this, this, databaseReference);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeAdapter);
        getRecipes();
        recipeAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);
                intent.putExtra(EditRecipeActivity.EXTRA_ID, recipe.getId());
                intent.putExtra(EditRecipeActivity.EXTRA_NAME, recipe.getName());
                intent.putExtra(EditRecipeActivity.EXTRA_DESC, recipe.getDescription());
                intent.putExtra(EditRecipeActivity.EXTRA_CALORIE, recipe.getCalories());
                startActivityForResult(intent, 1);
            }
        });

    }

    public void onRecipeClick(int position){
        recipeArrayList.get(position);
    }

    private void getRecipes() {
        recipeArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                recipeArrayList.add(snapshot.getValue(Recipe.class));
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Recipe removedRecipe = snapshot.getValue(Recipe.class);
                if (removedRecipe != null) {
                    recipeArrayList.remove(removedRecipe);
                    if (recipeAdapter != null) {
                        recipeAdapter.notifyItemRemoved(recipeArrayList.indexOf(removedRecipe));
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}