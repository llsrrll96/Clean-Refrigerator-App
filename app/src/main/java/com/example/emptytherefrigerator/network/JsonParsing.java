package com.example.emptytherefrigerator.network;

import com.example.emptytherefrigerator.entity.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonParsing
{
    public static ArrayList<Recipe> parsingRecipe(String data)     //레시피 정보 파싱
    {
        ArrayList<Recipe> list = new ArrayList<Recipe>();
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(data);
            String jsonString = jsonObject.getString("Recipe");
            JSONArray resRecipeList = new JSONArray(jsonString);

            for(int i=0; i<resRecipeList.length(); i++)
            {
                JSONObject recipe = resRecipeList.getJSONObject(i);
                String recipeId = recipe.getString("recipeId");
                String title = recipe.getString("title");
                String userId = recipe.getString("userId");
                String ingredient = recipe.getString("ingredient");

                int recipePerson = recipe.getInt("recipePerson");
                int recipeTime = recipe.getInt("recipeTime");
                String recipeImagePath = recipe.getString("recipeImagePath");
                String recipeContents = recipe.getString("recipeContents");
                int commentCount = recipe.getInt("recipeContents");
                int likeCount = recipe.getInt("likeCount");
                String uploadDate = recipe.getString("uploadData");

                list.add(new Recipe(recipeId, title, userId, ingredient, recipePerson, recipeTime, recipeImagePath, recipeContents, commentCount, likeCount, uploadDate));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }
}
