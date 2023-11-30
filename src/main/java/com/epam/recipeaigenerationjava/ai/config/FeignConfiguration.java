package com.epam.recipeaigenerationjava.ai.config;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfiguration {

    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(10, TimeUnit.MINUTES, 10, TimeUnit.MINUTES, true);
    }
}
