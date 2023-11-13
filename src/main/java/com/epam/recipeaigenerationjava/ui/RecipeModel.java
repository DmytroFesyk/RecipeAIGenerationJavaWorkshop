package com.epam.recipeaigenerationjava.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RecipeModel {

    private String name;
    private List<IngredientModel> ingredients;
    private String summary;
    private List<String> instructions;
    @Data
    @AllArgsConstructor
    public static class IngredientModel {
        String name;
        String amount;

    }
}
