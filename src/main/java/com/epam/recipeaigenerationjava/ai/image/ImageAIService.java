package com.epam.recipeaigenerationjava.ai.image;

import java.util.List;

public interface ImageAIService {

    List<String> generateImage(String description, int numberOfImages);

}
