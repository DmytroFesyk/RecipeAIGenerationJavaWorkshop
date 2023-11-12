package com.epam.recipeaigenerationjava.ai.client.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ChatGptRequest(
        @JsonProperty("model")
        String model,
        @JsonProperty("temperature")
        Double temperature,
        @JsonProperty("messages")
        List<RoleMessage> messages
) {
    public ChatGptRequest(
            @JsonProperty("temperature")
            Double temperature, @JsonProperty("messages")
            List<RoleMessage> messages) {
        this("gpt-3.5-turbo-16k", temperature, messages);
    }

    public record RoleMessage(
            @JsonProperty("role")
            String role,
            @JsonProperty("content")
            String content
    ) {
    }
}
