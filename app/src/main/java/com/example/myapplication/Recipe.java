package com.example.myapplication;

public class Recipe {
    public String id, name, description, calories;

    public Recipe() {
    }

    public Recipe(String id, String name, String description, String calories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calories = calories;
    }
}
