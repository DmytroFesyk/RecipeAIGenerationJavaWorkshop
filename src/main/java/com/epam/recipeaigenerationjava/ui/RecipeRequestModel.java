package com.epam.recipeaigenerationjava.ui;

import lombok.Data;

import java.util.List;

@Data
public class RecipeRequestModel {
    private String dishType;
    private String ingredients;
    private List<String> excludeCalories;
}
