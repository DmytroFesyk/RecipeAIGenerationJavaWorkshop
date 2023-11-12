package com.epam.recipeaigenerationjava.ai.client.dalle;

import java.util.List;

public record DalleImageGenerationResponse(
        int created,
        List<ImageURL> data
) {
    public record ImageURL(
            String url
    ) {
    }
}
