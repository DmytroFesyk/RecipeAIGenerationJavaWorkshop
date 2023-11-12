package com.epam.recipeaigenerationjava.ai.text;

import com.epam.recipeaigenerationjava.ai.client.gpt.ChatGPTClient;
import com.epam.recipeaigenerationjava.ai.client.gpt.ChatGptRequest;
import com.epam.recipeaigenerationjava.model.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.List;
import java.util.Objects;

import static com.epam.recipeaigenerationjava.ai.text.Constants.ASSISTANT_RECIPE_TASK_REQUEST_INPUT;
import static com.epam.recipeaigenerationjava.ai.text.Constants.ASSISTANT_RECIPE_TASK_RESPONSE_INPUT;
import static com.epam.recipeaigenerationjava.ai.text.Constants.BASE_RULES;
import static com.epam.recipeaigenerationjava.ai.text.Constants.CHAT_COOK_ROLE;
import static com.epam.recipeaigenerationjava.ai.text.Constants.CREATE_RECIPE_PROMPT;
import static com.epam.recipeaigenerationjava.ai.text.Constants.EXCLUDE_PRODUCTS_NOT_NEED_PROMPT;
import static com.epam.recipeaigenerationjava.ai.text.Constants.EXCLUDE_PRODUCTS_PROMPT;
import static com.epam.recipeaigenerationjava.ai.text.Constants.FORMAT_JSON_RULE;
import static com.epam.recipeaigenerationjava.ai.text.Constants.RECIPE_TASK_RULES;

@Service
@RequiredArgsConstructor
public class ChatGPTService implements TextAIService {

    private final ChatGPTClient chatGPTClient;
    private final ObjectMapper objectMapper;
    @Value("${chatGpt.key}")
    private String chatGPTKey;

    @Override
    public Recipe generateRecipe(List<String> productNames, List<String> excludedProducts, String dishType) {
        var exclude = EXCLUDE_PRODUCTS_NOT_NEED_PROMPT;
        if (excludedProducts != null && !excludedProducts.isEmpty()) {
            exclude = String.format(EXCLUDE_PRODUCTS_PROMPT, String.join(", ", excludedProducts));
        }
        val request = String.format(CREATE_RECIPE_PROMPT.trim(), dishType, String.join(", ", Objects.requireNonNullElseGet(productNames, List::of)), exclude);

        val userInput = request + System.lineSeparator() + exclude + System.lineSeparator() + FORMAT_JSON_RULE;

        val chatCompletionRequest = new ChatGptRequest(0.6, List.of(
                new ChatGptRequest.RoleMessage("system", setChatSpeciality(CHAT_COOK_ROLE)),
                new ChatGptRequest.RoleMessage("system", RECIPE_TASK_RULES.trim()),
                new ChatGptRequest.RoleMessage("user", ASSISTANT_RECIPE_TASK_REQUEST_INPUT.trim()),
                new ChatGptRequest.RoleMessage("assistant", ASSISTANT_RECIPE_TASK_RESPONSE_INPUT.trim()),
                new ChatGptRequest.RoleMessage("user", userInput)
        ));
        return getChatResponse(chatCompletionRequest, "recipe request", Recipe.class);
    }

    private String setChatSpeciality(String whoYouAre) {
        return String.format(BASE_RULES, whoYouAre);
    }

    private <T> T getChatResponse(ChatGptRequest chatRequest, String requestType, Class<T> responseClass) {
        var response = "";
        try {
            val completion = chatGPTClient.generateRecipe(chatRequest, "Bearer " + chatGPTKey);
            response = completion.choices().get(0).message().content();

            val jsonResponse = response.substring(response.indexOf('{'), response.lastIndexOf('}') + 1);
            return objectMapper.readValue(jsonResponse, responseClass);
        } catch (Exception e) {
            val problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            var causeText = e.getClass().getSimpleName();
            if (e.getCause() != null && e.getCause().getMessage() != null) {
               causeText= e.getCause().getMessage();
            }
            problem.setTitle(Objects.requireNonNullElse(e.getMessage(), e.getClass().getSimpleName()));
            problem.setDetail(e.getMessage());
            problem.setProperty("exceptionCause", causeText);
            problem.setProperty("requestType", requestType);
            problem.setProperty("chatGptResponse", response);
            throw new ErrorResponseException(HttpStatusCode.valueOf(problem.getStatus()), problem, null);
        }
    }
}
