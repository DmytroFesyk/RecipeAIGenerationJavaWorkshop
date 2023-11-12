package com.epam.recipeaigenerationjava.service;

import com.epam.recipeaigenerationjava.ai.text.TextAIService;
import com.epam.recipeaigenerationjava.model.Recipe;
import com.epam.recipeaigenerationjava.model.RecipeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final TextAIService textAIService;

    public Recipe generateRecipe(RecipeRequest recipeRequest) {
        return textAIService.generateRecipe(recipeRequest.ingredients(), recipeRequest.excludeCalories(), recipeRequest.dishType());
    }
}
