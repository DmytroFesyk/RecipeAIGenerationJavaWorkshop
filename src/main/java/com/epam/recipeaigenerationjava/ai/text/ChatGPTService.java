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

import static com.epam.recipeaigenerationjava.ai.text.Constants.EXCLUDE_PRODUCTS_NOT_NEED_PROMPT;

@Service
@RequiredArgsConstructor
public class ChatGPTService implements TextAIService{

    private final ChatGPTClient chatGPTClient;
    private final ObjectMapper objectMapper;
    @Value("${chatGpt.key}")
    private String chatGPTKey;

    @Override
    public Recipe generateRecipe(List<String> productNames, List<String> excludedProducts, String dishType){
        var exclude = EXCLUDE_PRODUCTS_NOT_NEED_PROMPT;
        if (excludedProducts != null && !excludedProducts.isEmpty()){
            exclude = String.format(Constants.EXCLUDE_PRODUCTS_PROMPT, String.join(", ", excludedProducts));
        }
        val request = String.format(Constants.CREATE_RECIPE_PROMPT.trim(), dishType, String.join(", ", Objects.requireNonNullElseGet(productNames,List::of)), exclude);
        val userInput = request + System.lineSeparator() + exclude + System.lineSeparator() + Constants.FORMAT_JSON_RULE;

        val chatCompletionRequest = new ChatGptRequest(0.6, List.of(
                new ChatGptRequest.RoleMessage("system", Constants.BASE_RULES),
                new ChatGptRequest.RoleMessage("system", Constants.RECIPE_TASK_RULES.trim()),
                new ChatGptRequest.RoleMessage("user", Constants.ASSISTANT_RECIPE_TASK_REQUEST_INPUT.trim()),
                new ChatGptRequest.RoleMessage("assistant", Constants.ASSISTANT_RECIPE_TASK_RESPONSE_INPUT.trim()),
                new ChatGptRequest.RoleMessage("user", userInput)
        ));
        return getChatResponse(chatCompletionRequest);
    }

    private Recipe getChatResponse(ChatGptRequest chatRequest){
        var response = "";
        try{
            val completion = chatGPTClient.generateRecipe(chatRequest, "Bearer " + chatGPTKey);
            response = completion.choices().get(0).message().content();
            val jsonResponse = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
            return objectMapper.readValue(jsonResponse, Recipe.class);
        } catch (Exception e){
            throw createHttpResponseException(e, response);
        }
    }

    private ErrorResponseException createHttpResponseException(Exception e, String response) {
        val problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        var causeText = e.getClass().getSimpleName();
        if (e.getCause() != null && e.getCause().getMessage() != null) {
            causeText = e.getCause().getMessage();
        }
        problem.setTitle(Objects.requireNonNullElse(e.getMessage(), e.getClass().getSimpleName()));
        problem.setDetail(e.getMessage());
        problem.setProperty("exceptionCause", causeText);
        problem.setProperty("requestType", "recipe-generation");
        problem.setProperty("chatGptResponse", response);
        return new ErrorResponseException(HttpStatusCode.valueOf(problem.getStatus()), problem, null);
    }
}
