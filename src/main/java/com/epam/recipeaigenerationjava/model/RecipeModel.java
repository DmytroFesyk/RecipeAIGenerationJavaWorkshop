package com.epam.recipeaigenerationjava.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RecipeModel {

    String name;
    List<IngredientModel> ingredients;
    String summary;
    List<String> instructions;
    @Data
    @AllArgsConstructor
    public static class IngredientModel {
        String name;
        String amount;

    }
}
