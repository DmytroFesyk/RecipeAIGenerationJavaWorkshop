package com.epam.recipeaigenerationjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Recipe(
        @JsonProperty("recipe")
        String name,
        List<Ingredient> ingredients,
        String summary,
        List<String> instructions
) {
    public record Ingredient(
            String name,
            String amount
    ) {
    }
}
