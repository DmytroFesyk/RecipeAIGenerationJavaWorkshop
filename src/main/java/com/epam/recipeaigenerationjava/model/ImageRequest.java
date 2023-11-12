package com.epam.recipeaigenerationjava.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;



public record ImageRequest(
        String description,

        @Schema(defaultValue="1")
        @Min(value = 1, message = "Number of images should be greater than 0")
        int numberOfImages
) {
}
