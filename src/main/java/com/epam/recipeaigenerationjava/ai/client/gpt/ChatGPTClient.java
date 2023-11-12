package com.epam.recipeaigenerationjava.ai.client.gpt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${chatGpt.url}", name = "ChatGPTApi")
public interface ChatGPTClient {

    @PostMapping
    ChatGptResponse generateRecipe(
            @RequestBody ChatGptRequest chatGptRequest,
            @RequestHeader("Authorization") String key
    );
}
