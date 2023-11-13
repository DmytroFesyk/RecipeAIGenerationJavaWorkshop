package com.epam.recipeaigenerationjava.ui;

import lombok.Data;

import java.util.List;

@Data
public class RecipeWithImageRequestModel {
    private String dishType;
    private String ingredients;
    private List<String> excludeCalories;
    private int imageNumber;
    private String imageModel;
    private String imageStyle;
}
