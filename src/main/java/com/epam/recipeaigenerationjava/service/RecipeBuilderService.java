package com.epam.recipeaigenerationjava.service;

import com.epam.recipeaigenerationjava.ai.text.TextAIService;
import com.epam.recipeaigenerationjava.model.FullRecipe;
import com.epam.recipeaigenerationjava.model.FullRecipeRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeBuilderService {

    private final TextAIService textAIService;
    private final ImageService imageService;

    public FullRecipe generateRecipe(FullRecipeRequest fullRecipeRequest) {
        val recipe =  textAIService.generateRecipe(fullRecipeRequest.ingredients(), fullRecipeRequest.excludeCalories(), fullRecipeRequest.dishType());
        val image = imageService.generateImage(recipe.summary(), fullRecipeRequest.numberImages(), fullRecipeRequest.imageModel(), fullRecipeRequest.imageStyle());
        return new FullRecipe(recipe, image);
    }
}
