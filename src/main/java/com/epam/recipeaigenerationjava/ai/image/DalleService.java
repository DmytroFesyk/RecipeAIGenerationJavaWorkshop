package com.epam.recipeaigenerationjava.ai.image;

import com.epam.recipeaigenerationjava.ai.client.dalle.DalleClient;
import com.epam.recipeaigenerationjava.ai.client.dalle.DalleImageGenerationRequest;
import com.epam.recipeaigenerationjava.ai.client.dalle.DalleImageGenerationResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DalleService implements ImageAIService {

    private final DalleClient dalleClient;
    @Value("${dalle.key}")
    private String dalleKey;

    @Override
    public List<String> generateImage(String description, int numberOfImages) {
        val generatedImages = dalleClient.generateImage(new DalleImageGenerationRequest(description, numberOfImages), "Bearer " + dalleKey);
        return generatedImages.data().stream().map(DalleImageGenerationResponse.ImageURL::url).toList();
    }

    @Override
    public List<String> generateImage(String description, int numberOfImages, String model, String style) {
        val generatedImages = dalleClient.generateImage(new DalleImageGenerationRequest(description,numberOfImages, model, style), "Bearer " + dalleKey);
        return generatedImages.data().stream().map(DalleImageGenerationResponse.ImageURL::url).toList();
    }
}
