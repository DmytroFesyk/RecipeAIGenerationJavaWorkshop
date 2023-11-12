package com.epam.recipeaigenerationjava.ai.text;

import com.epam.recipeaigenerationjava.model.Recipe;

import java.util.List;

public interface TextAIService {

    Recipe generateRecipe(List<String> productNames, List<String> excludedProducts, String dishType);

}
