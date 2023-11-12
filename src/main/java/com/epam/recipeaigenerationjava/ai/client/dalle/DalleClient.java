package com.epam.recipeaigenerationjava.ai.client.dalle;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${dalle.url}", name = "DalleApi")
public interface DalleClient {

    @PostMapping(path = {"/generations"})
    DalleImageGenerationResponse generateImage(
            @RequestBody DalleImageGenerationRequest imageGenerationRequest,
            @RequestHeader("Authorization") String key
    );
}
