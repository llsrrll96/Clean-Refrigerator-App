package com.example.emptytherefrigerator.network;

import com.example.emptytherefrigerator.entity.LikeIn;
import com.example.emptytherefrigerator.entity.LikeOut;
import com.example.emptytherefrigerator.entity.RecipeComment;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.entity.RecipeOut;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JsonParsing
{
    public static String dateToString(String date)      //서버에서 받은 datetime을 우리가 원하는 포맷으로 바꿔주는 함수
    {
        StringBuilder tmp = new StringBuilder();

        for(int i=0; i<19;i++)
        {
            if(date.charAt(i)=='T')
            {
                tmp.append(' ');
                continue;
            }
            tmp.append(date.charAt(i));
        }
        System.out.println(tmp.toString());
        return tmp.toString();
    }
    public static ArrayList<RecipeIn> parsingRecipe(String data)     //레시피 정보 파싱
    {
        ArrayList<RecipeIn> recipeListData = new ArrayList<>();
        try
        {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0 ; i< jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
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

                recipeIn.setUploadDate(dateToString(jsonObject.getString("uploadDate")));
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

    public static ArrayList<RecipeOut> parsingRecipeOut(String data)     //레시피 정보 파싱
    {
        ArrayList<RecipeOut> recipeListData = new ArrayList<>();
        try
        {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0 ; i< jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RecipeOut recipeOut = new RecipeOut();
                recipeOut.setRecipeOutId(jsonObject.getInt("recipeOutId"));
                recipeOut.setLink(jsonObject.getString("link"));
                recipeOut.setTitle(jsonObject.getString("title"));
                recipeOut.setIngredient(jsonObject.getString("ingredient"));            //서버에서 받아오는지 확인후 수정

                JSONArray jsonArrayImage = jsonObject.getJSONArray("recipeImageBytes");
                String[] recipeImageBytes = new String [jsonArrayImage.length()];

                for(int j= 0; j < jsonArrayImage.length(); j++)
                {
                    JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(j);
                    recipeImageBytes[j] = jsonObjectImage.getString("recipeImageByte");
                }
                recipeOut.setRecipeImageByte(recipeImageBytes);
                recipeListData.add(recipeOut);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return recipeListData;
    }

    public static RecipeIn parsingRecipeIn(String data)     //레시피 객체 하나만 파싱
    {
        RecipeIn recipeIn= new RecipeIn();
        try
        {
            JSONObject jsonObject = new JSONObject(data);

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

            recipeIn.setUploadDate(dateToString(jsonObject.getString("uploadDate")));

            JSONArray jsonArrayImage = jsonObject.getJSONArray("recipeImageBytes");
            String[] recipeImageBytes = new String [jsonArrayImage.length()];

            for(int j= 0; j < jsonArrayImage.length(); j++)
            {
                JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(j);
                recipeImageBytes[j] = jsonObjectImage.getString("recipeImageByte");
            }
            recipeIn.setRecipeImageByte(recipeImageBytes);

            return recipeIn;
        }
        catch(JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<RecipeComment>parsingCommentList(String data)       //내 댓글 조회, 레시피 디테일뷰 댓글 조회
    {
        ArrayList<RecipeComment> list = new ArrayList<>();
        try
        {
            JSONArray resCommentList = new JSONArray(data);
            System.out.println(data);

            for(int i=0; i<resCommentList.length(); i++)
            {
                JSONObject comment = resCommentList.getJSONObject(i);

                RecipeComment recipeComment= new RecipeComment();

                recipeComment.setRecipeId(comment.getInt("recipeInId"));
                recipeComment.setCommentId(comment.getInt("commentId"));
                recipeComment.setRecipeTitle(comment.getString("title"));
                recipeComment.setUserId(comment.getString("userId"));
                recipeComment.setContent(comment.getString("content"));
                recipeComment.setUploadDate(dateToString(comment.getString("uploadDate")));

                JSONArray jsonArrayImage = comment.getJSONArray("recipeImageBytes");
                String[] recipeImageBytes = new String [jsonArrayImage.length()];

                for(int j= 0; j < jsonArrayImage.length(); j++)
                {
                    JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(j);
                    recipeImageBytes[j] = jsonObjectImage.getString("recipeImageByte");
                }
                recipeComment.setRecipeImageByte(recipeImageBytes);

                list.add(recipeComment);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<LikeIn> parsingLikeInList(String data)      //내 좋아요 목록 외부 레시피 조회
    {
        ArrayList<LikeIn> list = new ArrayList<>();
        try
        {
            JSONArray likeInList = new JSONArray(data);

            for(int i=0;i<likeInList.length(); i++)
            {
                JSONObject object = likeInList.getJSONObject(i);

                RecipeIn recipeIn= new RecipeIn();
                LikeIn likeIn = new LikeIn();

                recipeIn.setRecipeInId(object.getInt("recipeInId"));
                recipeIn.setTitle(object.getString("title"));
                recipeIn.setUserId(object.getString("userId"));      //레시피를 쓴 사람의 id
                likeIn.setUploadDate(dateToString(object.getString("uploadDate")));

                JSONArray jsonArrayImage = object.getJSONArray("recipeImageBytes");
                String[] recipeImageBytes = new String [jsonArrayImage.length()];

                for(int j= 0; j < jsonArrayImage.length(); j++)         //이미지 bytes
                {
                    JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(j);
                    recipeImageBytes[j] = jsonObjectImage.getString("recipeImageByte");
                }
                recipeIn.setRecipeImageByte(recipeImageBytes);
                likeIn.setRecipeIn(recipeIn);

                list.add(likeIn);
            }
            return list;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<LikeOut> parsingLikeOutList(String data)        //내 좋아요 목록 외부 레시피 조회
    {
        ArrayList<LikeOut> list = new ArrayList<>();
        try
        {
            JSONArray likeOutList = new JSONArray(data);

            for(int i=0;i<likeOutList.length(); i++)
            {
                JSONObject object = likeOutList.getJSONObject(i);
                RecipeOut recipeOut = new RecipeOut();
                LikeOut likeOut = new LikeOut();

                recipeOut.setRecipeOutId(object.getInt("recipeOutId"));
                recipeOut.setTitle(object.getString("title"));
                recipeOut.setLink(object.getString("link"));
                likeOut.setUploadDate(dateToString(object.getString("uploadDate")));

                JSONArray jsonArrayImage = object.getJSONArray("recipeImageBytes");
                String[] recipeImageBytes = new String [jsonArrayImage.length()];

                for(int j= 0; j < jsonArrayImage.length(); j++)         //이미지 bytes
                {
                    JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(j);
                    recipeImageBytes[j] = jsonObjectImage.getString("recipeImageByte");
                }
                recipeOut.setRecipeImageByte(recipeImageBytes);

                list.add(new LikeOut(recipeOut));
            }
            return list;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
}
