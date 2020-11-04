package com.example.emptytherefrigerator.network;

import com.example.emptytherefrigerator.entity.LikeIn;
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
        ArrayList<RecipeIn> recipeListData = new ArrayList<RecipeIn>();
        try
        {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0 ; i< jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //test
                System.out.println("jsonObject: "+ jsonObject.get("title"));

                RecipeIn recipeIn = new RecipeIn();
                recipeIn.setRecipeInId(jsonObject.getInt("recipeInId"));
                recipeIn.setTitle(jsonObject.getString("title"));
                recipeIn.setUserId(jsonObject.getString("userId"));
                recipeIn.setIngredient(jsonObject.getString("ingredient"));
                recipeIn.setIngredientUnit(jsonObject.getString("ingredientUnit"));
                recipeIn.setRecipePerson(jsonObject.getInt("recipePerson"));
                recipeIn.setRecipeTime(jsonObject.getInt("recipeTime"));
                recipeIn.setContents(jsonObject.getString("contents"));
                recipeIn.setCommentCount(jsonObject.getInt("commentCount"));
                recipeIn.setLikeCount(jsonObject.getInt("likeCount"));
                recipeIn.setUploadDate(jsonObject.getString("uploadDate"));

                JSONArray jsonArrayImage = jsonObject.getJSONArray("recipeImageBytes");
                String[] recipeImageBytes = new String [jsonArrayImage.length()];

                for(int j= 0; j < jsonArrayImage.length(); j++)
                {
                    JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(j);
                    recipeImageBytes[j] = jsonObjectImage.getString("recipeImageByte");
                }
                recipeIn.setRecipeImageByte(recipeImageBytes);
                recipeListData.add(recipeIn);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return recipeListData;
    }
    public static ArrayList<RecipeComment>parsingCommentList(String data)
    {
        ArrayList<RecipeComment> list = new ArrayList<>();
        try
        {
            JSONArray resCommentList = new JSONArray(data);
            System.out.println(data);

            for(int i=0; i<resCommentList.length(); i++)
            {
                JSONObject comment = resCommentList.getJSONObject(i);

                int recipeId = comment.getInt("recipeInId");
                int commentId = comment.getInt("commentId");
                String title = comment.getString("title");
                String userId = comment.getString("userId");
                String content = comment.getString("content");
                String uploadDate = comment.getString("uploadDate");

                JSONArray jsonArrayImage = comment.getJSONArray("recipeImageBytes");
                String[] recipeImageBytes = new String [jsonArrayImage.length()];

                for(int j= 0; j < jsonArrayImage.length(); j++)
                {
                    JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(j);
                    recipeImageBytes[j] = jsonObjectImage.getString("recipeImageByte");
                }
                list.add(new RecipeComment(recipeId, commentId, title, userId, content, uploadDate, recipeImageBytes));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<LikeIn> parsingLikeInList(String data)
    {
        ArrayList<LikeIn> list = new ArrayList<>();
        try
        {
            JSONArray likeInList = new JSONArray(data);
            System.out.println(data);

            for(int i=0;i<likeInList.length(); i++)
            {
                JSONObject likeIn = likeInList.getJSONObject(i);

                int recipeInId = likeIn.getInt("recipeInId");
                String title = likeIn.getString("title");
                String writerId = likeIn.getString("userId");       //레시피를 쓴 사람의 id
                String uploadDate = likeIn.getString("uploadDate");

                JSONArray jsonArrayImage = likeIn.getJSONArray("recipeImageBytes");
                String[] recipeImageBytes = new String [jsonArrayImage.length()];

                for(int j= 0; j < jsonArrayImage.length(); j++)
                {
                    JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(j);
                    recipeImageBytes[j] = jsonObjectImage.getString("recipeImageByte");
                }
                //list.add(new LikeIn(recipeInId, ))            //나중에 디비 구조 보고 수정 필요

            }
            return list;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
