package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditRecipeActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.myapplication.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.myapplication.EXTRA_NAME";
    public static final String EXTRA_DESC = "com.example.myapplication.EXTRA_DESC";
    public static final String EXTRA_CALORIE = "com.example.myapplication.EXTRA_CALORIE";

    private EditText edName, edDesc, edCalories;
    private Button addBtn, deleteBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        edName = findViewById(R.id.edName);
        edDesc = findViewById(R.id.edDesc);
        edCalories = findViewById(R.id.edCalories);
        addBtn = findViewById(R.id.addBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        String recipeId = getIntent().getStringExtra(EXTRA_ID);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Recipe").child(recipeId);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            edName.setText(intent.getStringExtra(EXTRA_NAME));
            edDesc.setText(intent.getStringExtra(EXTRA_DESC));
            edCalories.setText(intent.getStringExtra(EXTRA_CALORIE));
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString();
                String desc = edDesc.getText().toString();
                String calorie = edCalories.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("description", desc);
                map.put("calories", calorie);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditRecipeActivity.this, "Рецепт обновлен", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditRecipeActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditRecipeActivity.this, "Не удалось обновить рецепт", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.removeValue();
                Toast.makeText(EditRecipeActivity.this, "Рецепт удален", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditRecipeActivity.this, MainActivity.class));
            }
        });
    }
}