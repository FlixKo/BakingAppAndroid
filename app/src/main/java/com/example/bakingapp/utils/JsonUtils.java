package com.example.bakingapp.utils;

import android.util.Log;

import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class JsonUtils {

    public static ArrayList<Recipe> getRecipesList(String responseFromHttpUrl) throws JSONException {

        JSONArray responseArray =new JSONArray(responseFromHttpUrl);

        ArrayList<Recipe> recipes = new ArrayList<>();

        try{
            for (int i = 0; i<responseArray.length();i++){
                JSONObject jsonRecipe = responseArray.getJSONObject(i);
                Recipe recipe = new Recipe();
                ArrayList<RecipeStep> stepArrayList= new ArrayList<>();
                ArrayList<Ingredient>ingredientsArrayList = new ArrayList<>();
                JSONArray ingredientsJSONArray = jsonRecipe.getJSONArray("ingredients");
                JSONArray stepsJSONArray = jsonRecipe.getJSONArray("steps");

                for (int j = 0;j<ingredientsJSONArray.length();j++){
                    JSONObject jsonIngredient =ingredientsJSONArray.getJSONObject(j);

                    Ingredient ingredient = new Ingredient();

                    ingredient.setIngredient(jsonIngredient.getString("ingredient"));
                    ingredient.setMeasure(jsonIngredient.getString("measure"));
                    ingredient.setQuantity(jsonIngredient.getInt("quantity"));

                    ingredientsArrayList.add(ingredient);
                }

                for (int k = 0;k<stepsJSONArray.length();k++){
                    JSONObject stepJson = stepsJSONArray.getJSONObject(k);
                    RecipeStep step = new RecipeStep();

                    step.setId(stepJson.getInt("id"));
                    step.setDescription(stepJson.getString("description"));
                    step.setShortDescription(stepJson.getString("shortDescription"));
                    step.setVideoUrl(stepJson.getString("videoURL"));
                    step.setThumbnailURL(stepJson.getString("thumbnailURL"));

                    stepArrayList.add(step);
                }

                recipe.setId(jsonRecipe.getInt("id"));
                recipe.setName(jsonRecipe.getString("name"));
                recipe.setIngredients(ingredientsArrayList);
                recipe.setSteps(stepArrayList);
                recipe.setServings(jsonRecipe.getInt("servings"));

                recipes.add(recipe);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return recipes;
    }
}
