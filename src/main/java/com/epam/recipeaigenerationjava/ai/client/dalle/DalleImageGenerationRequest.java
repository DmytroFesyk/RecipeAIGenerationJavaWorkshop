package com.epam.recipeaigenerationjava.ai.client.dalle;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DalleImageGenerationRequest(
        String prompt,
        int n,
        String model,
        String size,
        @JsonProperty("response_format") String responseFormat,

        String style
) {
    public DalleImageGenerationRequest(String prompt,int n) {
        this(prompt, n, "dall-e-2", "1024x1024","url","natural");
    }

    public DalleImageGenerationRequest(String prompt,int n, String model, String style) {
        this(prompt, n, model, "1024x1024", "url", style);
    }
}
