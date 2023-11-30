package com.epam.recipeaigenerationjava.model;

import java.util.List;

public record FullRecipeRequest(
        String dishType,
        List<String> ingredients,
        List<String> excludeCalories,
         int numberImages,
        String imageModel,
        String imageStyle
) {
}
