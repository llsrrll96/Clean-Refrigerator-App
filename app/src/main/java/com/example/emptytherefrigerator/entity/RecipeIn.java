package com.example.emptytherefrigerator.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;

public class RecipeIn implements Serializable
{
    private int recipeInId;
    private String title;
    private String userId;
    private String ingredient;      //식재료
    private String ingredientUnit;   //식재료 단위
    private int recipePerson;       //몇 인분
    private int recipeTime;         //조리 시간
    private String[] recipeImageByte; //다중 레시피 이미지 데이터
    private String recipeImagePath; //레시피 조리 이미지 경로(들) : ㅇㅇㅇ, ㅇㅇㅇ
    private String contents;  //레시피 조리법 설명
    private int commentCount;         //댓글 개수
    private int likeCount;         //좋아요 개수
    private String uploadDate;      //작성 날짜

    /*private byte[row][col] recipeImageByte 데이터는
     * row 는 사진 갯수, col 은 해당 이미지 데이터 byte 코드
     * */

    public RecipeIn(){}
    public RecipeIn(int recipeInId, String title, String userId, String ingredient,
                    int recipePerson, int recipeTime, String[] recipeImageByte, String contents, int commentCount, int likeCount, String uploadDate)
    {
        this.recipeInId = recipeInId;
        this.title = title;
        this.userId = userId;
        this.ingredient = ingredient;
        this.recipePerson = recipePerson;
        this.recipeTime = recipeTime;
        this.recipeImageByte = recipeImageByte;
        this.contents = contents;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.uploadDate = uploadDate;
    }

    public RecipeIn(int recipeInId, String title, String userId, String ingredient,
                    int recipePerson, int recipeTime, String recipeImagePath, String contents, int commentCount, int likeCount, String uploadDate) {
        this.recipeInId = recipeInId;
        this.title = title;
        this.userId = userId;
        this.ingredient = ingredient;
        this.recipePerson = recipePerson;
        this.recipeTime = recipeTime;
        this.recipeImagePath = recipeImagePath;
        this.contents = contents;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.uploadDate = uploadDate;
    }

    public static Bitmap StringToBitmap(String encodedString)                      //스트링 이미지 데이터 -> 비트맵으로
    {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
    public int getRecipeInId() {
        return recipeInId;
    }

    public void setRecipeInId(int recipeInId) {
        this.recipeInId = recipeInId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public int getRecipePerson() {
        return recipePerson;
    }

    public void setRecipePerson(int recipePerson) {
        this.recipePerson = recipePerson;
    }

    public int getRecipeTime() {
        return recipeTime;
    }

    public void setRecipeTime(int recipeTime) {
        this.recipeTime = recipeTime;
    }

    public String[] getRecipeImageByte() {
        return recipeImageByte;
    }

    public void setRecipeImageByte(String[] recipeImageByte) {
        this.recipeImageByte = recipeImageByte;
    }

    public String getRecipeImagePath() {
        return recipeImagePath;
    }

    public void setRecipeImagePath(String recipeImagePath) {
        this.recipeImagePath = recipeImagePath;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}