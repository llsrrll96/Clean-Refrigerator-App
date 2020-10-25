package com.example.emptytherefrigerator.network;

import com.example.emptytherefrigerator.entity.RecipeComment;
import com.example.emptytherefrigerator.entity.RecipeIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParsing
{
    public static ArrayList<RecipeIn> parsingRecipe(String data)     //레시피 정보 파싱
    {
        ArrayList<RecipeIn> list = new ArrayList<RecipeIn>();
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(data);
            String jsonString = jsonObject.getString("Recipe");
            JSONArray resRecipeList = new JSONArray(jsonString);

            for(int i=0; i<resRecipeList.length(); i++)
            {
                JSONObject recipe = resRecipeList.getJSONObject(i);
                int recipeId = recipe.getInt("recipeId");
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

                list.add(new RecipeIn(recipeId, title, userId, ingredient, recipePerson, recipeTime, recipeImagePath, recipeContents, commentCount, likeCount, uploadDate));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<RecipeComment>parsingCommentList(String data)
    {
        ArrayList<RecipeComment> list = new ArrayList<>();
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(data);
            String jsonString = jsonObject.getString("myCommentList");
            JSONArray resCommentList = new JSONArray(jsonString);

            for(int i=0; i<resCommentList.length(); i++)
            {
                JSONObject comment = resCommentList.getJSONObject(i);

                int recipeId = comment.getInt("recipeId");
                int commentId = comment.getInt("commentId");
                String title = comment.getString("title");
                String userId = comment.getString("userId");
                String content = comment.getString("content");
                String uploadDate = comment.getString("uploadData");

                list.add(new RecipeComment(recipeId, commentId, title, userId, content, uploadDate));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }
}
