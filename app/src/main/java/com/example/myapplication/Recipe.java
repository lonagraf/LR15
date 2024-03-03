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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

  }
