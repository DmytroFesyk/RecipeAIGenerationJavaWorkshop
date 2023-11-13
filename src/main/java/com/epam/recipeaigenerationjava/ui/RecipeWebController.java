package com.epam.recipeaigenerationjava.ui;

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

    @GetMapping(path = {"/with-image"})
    public String getRecipeWithImageForm(Model model) {
        return "recipeWithImage";
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

//    @PostMapping(path = {"/image"})
//    public String generateImage(@ModelAttribute ImageRequestModel imageRequestModel,Model model) {
//        val image = imageService.generateImage(new ImageRequest(imageRequestModel.getImagePrompt(), imageRequestModel.getNumber()));
//        model.addAttribute("imageUrl", image.imageUrls().get(0));
//        return "recipe";
//    }

    @PostMapping(path = {"/image"})
    public String generateImage(@ModelAttribute ImageRequestModel imageRequestModel,Model model) {
        val image = imageService.generateImage(imageRequestModel.getImagePrompt(), imageRequestModel.getNumber(), imageRequestModel.getModel(), imageRequestModel.getStyle());
        model.addAttribute("imageUrl", image.imageUrls().get(0));
        return "recipe";
    }

//    @PostMapping(path = {"/with-image"})
//    public String generateRecipeWithImage(@ModelAttribute RecipeWithImageRequestModel recipeWithImageRequestModel, Model model) {
//        val ingredients = Arrays.stream(recipeWithImageRequestModel.getIngredients().split(",")).map(String::trim).toList();
//        val recipe = recipeBuilderService.generateRecipe(new FullRecipeRequest(recipeWithImageRequestModel.getDishType(), ingredients, recipeWithImageRequestModel.getExcludeCalories(), recipeWithImageRequestModel.getImageNumber(), recipeWithImageRequestModel.getImageModel(), recipeWithImageRequestModel.getImageStyle()));
//        val recipeModel = RecipeModel.builder()
//                .name(recipe.recipe().name())
//                .ingredients(recipe.recipe().ingredients().stream().map(ingredient -> new RecipeModel.IngredientModel(ingredient.name(), ingredient.amount())).toList())
//                .summary(recipe.recipe().summary())
//                .instructions(recipe.recipe().instructions())
//                .build();
//        model.addAttribute("recipe", recipeModel);
//        model.addAttribute("imageUrl", recipe.image().imageUrls().get(0));
//        return "recipeWithImage";
//    }
}
