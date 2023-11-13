package com.epam.recipeaigenerationjava.service;

import com.epam.recipeaigenerationjava.ai.image.ImageAIService;
import com.epam.recipeaigenerationjava.model.Image;
import com.epam.recipeaigenerationjava.model.ImageRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageAIService imageAIService;
    public Image generateImage(ImageRequest imageRequest) {
        val imageUrls = imageAIService.generateImage(imageRequest.description(), imageRequest.numberOfImages());
        return new Image(imageUrls);
    }

    public Image generateImage(String description, int numberOfImages, String model, String style) {
        val imageUrls = imageAIService.generateImage(description, numberOfImages, model, style);
        return new Image(imageUrls);
    }
}
