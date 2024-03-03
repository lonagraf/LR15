package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText edName, edDesc, edCalories;
    private Button addBtn;
    private DatabaseReference databaseReference;
    private String RECIPE_KEY = "Recipe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        edName = findViewById(R.id.edName);
        edDesc = findViewById(R.id.edDesc);
        edCalories = findViewById(R.id.edCalories);
        addBtn = findViewById(R.id.addBtn);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(RECIPE_KEY);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = databaseReference.getKey();
                String name = edName.getText().toString();
                String desc = edDesc.getText().toString();
                String calories = edCalories.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(calories)) {
                    // Обработка случая, если одно из полей пусто
                    Toast.makeText(AddRecipeActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    // Все поля заполнены, продолжаем добавление рецепта
                    Recipe newRecipe = new Recipe(id, name, desc, calories);
                    databaseReference.push().setValue(newRecipe);

                    // Очистка полей EditText
                    edName.setText("");
                    edDesc.setText("");
                    edCalories.setText("");

                    // Информирование пользователя о том, что рецепт успешно добавлен
                    Toast.makeText(AddRecipeActivity.this, "Рецепт успешно добавлен", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}