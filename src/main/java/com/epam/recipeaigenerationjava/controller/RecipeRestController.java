package com.epam.recipeaigenerationjava.controller;

import com.epam.recipeaigenerationjava.model.Image;
import com.epam.recipeaigenerationjava.model.ImageRequest;
import com.epam.recipeaigenerationjava.model.Recipe;
import com.epam.recipeaigenerationjava.model.RecipeRequest;
import com.epam.recipeaigenerationjava.service.ImageService;
import com.epam.recipeaigenerationjava.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/recipe")
@RequiredArgsConstructor
public class RecipeRestController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Recipe generateRecipe(@RequestBody RecipeRequest recipeRequest) {
        return recipeService.generateRecipe(recipeRequest);
    }

    @PostMapping(path = {"/image"},consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Image generateImage(@Valid @RequestBody ImageRequest imageRequest) {
        return imageService.generateImage(imageRequest);
    }
}
