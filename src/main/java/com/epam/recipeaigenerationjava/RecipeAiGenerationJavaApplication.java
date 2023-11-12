package com.epam.recipeaigenerationjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RecipeAiGenerationJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeAiGenerationJavaApplication.class, args);
    }

}
