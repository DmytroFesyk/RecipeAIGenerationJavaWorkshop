package com.epam.recipeaigenerationjava.ai.client.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public record ChatGptResponse(
        @JsonProperty("id") String id,
        @JsonProperty("object") String objectName,
        @JsonProperty("created") Integer created,
        @JsonProperty("model") String model,
        @JsonProperty("usage") Usage usage,
        @JsonProperty("choices") ArrayList<Choice> choices

) {
    public record Usage(
            @JsonProperty("prompt_tokens") Integer promptTokens,
            @JsonProperty("completion_tokens") Integer completionTokens,
            @JsonProperty("total_tokens") Integer totalTokens
    ) {
    }

    public record Choice(
            @JsonProperty("message") Message message,
            @JsonProperty("finish_reason") String finishReason,
            @JsonProperty("index") Integer index
    ) {
    }

    public record Message(
            @JsonProperty("role") String role,
            @JsonProperty("content") String content
    ) {
    }
}
