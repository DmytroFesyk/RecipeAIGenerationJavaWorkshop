package com.epam.recipeaigenerationjava.controller;

import com.epam.recipeaigenerationjava.model.RecipeModel;
import com.epam.recipeaigenerationjava.model.RecipeRequestModel;
import com.epam.recipeaigenerationjava.model.RecipeRequest;
import com.epam.recipeaigenerationjava.service.ImageService;
import com.epam.recipeaigenerationjava.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;


@Controller
@RequestMapping("/ui/recipe")
@RequiredArgsConstructor
public class RecipeWebController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    @GetMapping
    public String getRecipeForm(Model model) {
        return "recipe";
    }
    @PostMapping("/update/form")
    public String updateRecipeParameters(@ModelAttribute RecipeRequestModel recipeRequestModel, Model model) {
        return "recipe";
    }

    @PostMapping
    public String generateRecipe(@ModelAttribute RecipeRequestModel recipeRequestModel, Model model) {
        val ingredients = Arrays.stream(recipeRequestModel.getIngredients().split(",")).map(String::trim).toList();
        val recipe = recipeService.generateRecipe(new RecipeRequest(recipeRequestModel.getDishType(), ingredients, recipeRequestModel.getExcludeCalories()));
        val recipeModel = RecipeModel.builder()
                .name(recipe.name())
                .ingredients(recipe.ingredients().stream().map(ingredient -> new RecipeModel.IngredientModel(ingredient.name(), ingredient.amount())).toList())
                .summary(recipe.summary())
                .instructions(recipe.instructions())
                .build();
        model.addAttribute("recipe", recipeModel);
        return "recipe";
    }


//    @PostMapping
//    public Recipe generateRecipe(@RequestBody RecipeRequest recipeRequest) {
//        return recipeService.generateRecipe(recipeRequest);
//    }
//
//    @PostMapping(path = {"/image"})
//    public Image generateImage(@Valid @RequestBody ImageRequest imageRequest) {
//        return imageService.generateImage(imageRequest);
//    }
}
