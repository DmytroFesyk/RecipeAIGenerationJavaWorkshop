package com.epam.recipeaigenerationjava.ai.image;

import java.util.Collections;
import java.util.List;

public interface ImageAIService {

    List<String> generateImage(String description, int numberOfImages);

    default List<String> generateImage(String description, int numberOfImages, String model, String style){
        return Collections.emptyList();
    }

}
