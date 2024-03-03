package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText edName, edDesc, edCalories;
    private Button addBtn;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private String recipeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        edName = findViewById(R.id.edName);
        edDesc = findViewById(R.id.edDesc);
        edCalories = findViewById(R.id.edCalories);
        addBtn = findViewById(R.id.addBtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Recipe");
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edName.getText().toString();
                String desc = edDesc.getText().toString();
                String calories = edCalories.getText().toString();
                recipeId = name;

               Recipe newRecipe = new Recipe(recipeId, name, desc, calories);

               databaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       databaseReference.child(recipeId).setValue(newRecipe);
                       Toast.makeText(AddRecipeActivity.this, "Рецепт добавлен", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(AddRecipeActivity.this, MainActivity.class));
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       Toast.makeText(AddRecipeActivity.this, "Ошибка добавления рецепта", Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });
    }
}