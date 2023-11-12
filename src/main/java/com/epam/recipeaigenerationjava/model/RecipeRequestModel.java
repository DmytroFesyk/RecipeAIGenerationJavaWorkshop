package com.epam.recipeaigenerationjava.model;

import lombok.Data;

import java.util.List;

@Data
public class RecipeRequestModel {
    String dishType;
    String ingredients;
    List<String> excludeCalories;
}
