package com.epam.recipeaigenerationjava.model;

import java.util.List;

public record RecipeRequest(
        String dishType,
        List<String> ingredients,
        List<String> excludeCalories
) {
}
