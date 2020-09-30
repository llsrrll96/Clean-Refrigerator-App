package com.example.emptytherefrigerator.entity;

public class Recipe
{
    private String recipeId;
    private String title;
    private String userId;
    private String ingredient;      //식재료
    private String recipeImgPath;   //요리 이미지 경로 (대표이미지 겸)
    private int recipePerson;       //몇 인분
    private int recipeTime;         //조리 시간
    private String recipeContents;  //레시피 조리법
    private int chatNumber;         //댓글 개수
    private int likeNumber;         //좋아요 개수
    private String uploadDate;      //작성 날짜

    public Recipe(String recipeId, String title, String userId, String ingredient, String recipeImgPath, int recipePerson, int recipeTime, String recipeContents, int chatNumber, int likeNumber, String uploadDate) {
        this.recipeId = recipeId;
        this.title = title;
        this.userId = userId;
        this.ingredient = ingredient;
        this.recipeImgPath = recipeImgPath;
        this.recipePerson = recipePerson;
        this.recipeTime = recipeTime;
        this.recipeContents = recipeContents;
        this.chatNumber = chatNumber;
        this.likeNumber = likeNumber;
        this.uploadDate = uploadDate;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
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

    public String getRecipeImgPath() {
        return recipeImgPath;
    }

    public void setRecipeImgPath(String recipeImgPath) {
        this.recipeImgPath = recipeImgPath;
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

    public String getRecipeContents() {
        return recipeContents;
    }

    public void setRecipeContents(String recipeContents) {
        this.recipeContents = recipeContents;
    }

    public int getChatNumber() {
        return chatNumber;
    }

    public void setChatNumber(int chatNumber) {
        this.chatNumber = chatNumber;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
