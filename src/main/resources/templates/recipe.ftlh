<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <title>Recipe Generation</title>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            document.getElementById("imageButton").addEventListener("click", spinnerOnclick);
            document.getElementById("recipeButton").addEventListener("click", spinnerOnclick);
        });

        function spinnerOnclick(event) {
            event.currentTarget.setAttribute("disabled", true);
            event.currentTarget.innerHTML =
                `<span class="spinner-border spinner-border-sm" style="width: 2rem; height: 2rem;" role="status" aria-hidden="true"></span>
                    <span>Generating...</span>`;
            event.currentTarget.parentElement.submit();
        }
    </script>
</head>
<body class="vh-100 vw-100 fs-3">
<div class="p-5">
    <h3 class="d-flex flex-column mb-4 h-100 align-items-left">Recipe Generation</h3>
    <div class="d-flex w-100 ">
        <form class="d-flex flex-column align-items-left w-50 m-3" name="f" action="/ui/recipe" method="POST">
            <div class="d-flex flex-column align-items-left input-group-lg mb-3">
                <label for="dish-type" class="form-label">Choose dish type</label>
                <select name="dishType" id="dish-type">
                    <option value="Soup" <#if (recipeRequestModel.dishType)! == 'Soup'>selected</#if>>Soup</option>
                    <option value="Salad" <#if (recipeRequestModel.dishType)! == 'Salad'>selected</#if>>Salad</option>
                    <option value="Pasta" <#if (recipeRequestModel.dishType)! == 'Pasta'> selected </#if>>Pasta</option>
                    <option value="Any" <#if (recipeRequestModel.dishType)! == 'Any'>selected</#if>>Any</option>
                </select>
                <label for="exclude-category" class="form-label">Exclude product categories</label>
                <select name="excludeCalories" id="exclude-category" multiple>
                    <option value="Meat" <#if (recipeRequestModel.excludeCalories)!?seq_contains('Meat')> selected</#if>>Meat</option>
                    <option value="Fish" <#if (recipeRequestModel.excludeCalories)!?seq_contains('Fish') >selected</#if>>Fish</option>
                    <option value="Nuts" <#if (recipeRequestModel.excludeCalories)!?seq_contains('Nuts') >selected</#if> >Nuts</option>
                </select>
                <label for="ingredients" class="form-label">Write ingredients separated by `,`</label>
                <textarea name="ingredients" class="form-control" id="ingredients" rows="3"
                          placeholder="your ingredients"><#if (recipeRequestModel.ingredients)??>${recipeRequestModel.ingredients}</#if></textarea>
            </div>
            <button id="recipeButton" class="btn btn-primary fs-3 " type="submit" onclick="">Generate Recipe</button>
        </form>
        <div class="d-flex flex-column align-items-left m-auto w-50">
            <span class="fs-3">Recipe</span>
            <textarea name="ingredients" class="form-control" id="ingredients" rows="25" placeholder="your recipe"
                      readonly><#if recipe??>Name: ${recipe.name}
Summary: ${recipe.summary}
Instructions:
<#list recipe.instructions as instruction>${instruction}
</#list>
Ingredients:
<#list recipe.ingredients as ingredients>${ingredients.name}-${ingredients.amount}
</#list></#if></textarea>
        </div>
    </div>
    <#--    image generation-->
    <div class="d-flex w-100 mt-3">
        <form class="d-flex flex-column align-items-left w-50 m-3" name="f" action="/ui/recipe/image" method="POST">
            <div class="d-flex flex-column align-items-left input-group-lg mb-3">
                <label for="number" class="form-label">Choose image number from 1 to 2</label>
                <input type="number" id="number" name="number" min="1" max="2" value="1"/>
                <label for="model" class="form-label">Choose model</label>
                <select name="model" id="model">
                    <option value="dall-e-2" <#if (imageRequestModel.model)! == 'dall-e-2'>selected</#if>>DALL·E 2</option>
                    <option value="dall-e-3" <#if (imageRequestModel.model)! == 'dall-e-3'>selected</#if>>DALL·E 3</option>
                </select>
                <label for="style" class="form-label">Choose style</label>
                <select name="style" id="style">
                    <option value="natural" <#if (imageRequestModel.style)! == 'natural'>selected</#if>>Natural</option>
                    <option value="vivid" <#if (imageRequestModel.style)! == 'vivid'>selected</#if>>Vivid</option>
                </select>
                <label for="imagePrompt" class="form-label">Write prompt</label>
                <textarea name="imagePrompt" class="form-control" id="imagePrompt" rows="3"
                          placeholder="your prompt"><#if (imageRequestModel.imagePrompt)??>${imageRequestModel.imagePrompt}</#if></textarea>
            </div>
            <button id="imageButton" class="btn btn-primary fs-3 " type="submit" onclick="">
                Generate Image
            </button>
        </form>
        <div class="d-flex flex-column align-items-left m-auto w-50">
            <img src="${imageUrl!''}">
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
        integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js"
        integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V" crossorigin="anonymous"></script>
</body>
</html>
