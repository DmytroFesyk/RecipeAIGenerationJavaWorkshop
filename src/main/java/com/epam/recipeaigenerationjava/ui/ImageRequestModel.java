package com.epam.recipeaigenerationjava.ui;


import lombok.Data;

@Data
public class ImageRequestModel {
    private String imagePrompt;
    private int number;
    private String model;
    private String style;

}
