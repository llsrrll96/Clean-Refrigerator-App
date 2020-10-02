package com.example.emptytherefrigerator.entity;

public class Recipe
{
    private String recipeId;
    private String title;
    private String userId;
    private String ingredient;      //식재료
    private String recipeImgPath;   //요리 이미지 경로(들) (대표이미지 겸) : ㅇㅇㅇ, ㅁㅁㅁ
    private int recipePerson;       //몇 인분
    private int recipeTime;         //조리 시간
    private String recipeContentsImagePath; //레시피 조리 이미지 경로(들) : ㅇㅇㅇ, ㅇㅇㅇ
    private String recipeContents;  //레시피 조리법
    private int commentCount;         //댓글 개수
    private int likeCount;         //좋아요 개수
    private String uploadDate;      //작성 날짜

    //검색결과
    public Recipe(String recipeImgPath, String title, int commentCount, int likeCount, String uploadDate) {
        this.recipeImgPath = recipeImgPath;
        this.title = title;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.uploadDate = uploadDate;
    }

    //조회용
    public Recipe(String recipeId, String title, String userId, String ingredient, String recipeImgPath, int recipePerson, int recipeTime, String recipeContentsImagePath,String recipeContents, int commentCount, int likeCount, String uploadDate) {
        this.recipeId = recipeId;
        this.title = title;
        this.userId = userId;
        this.ingredient = ingredient;
        this.recipeImgPath = recipeImgPath;
        this.recipePerson = recipePerson;
        this.recipeTime = recipeTime;
        this.recipeContentsImagePath = recipeContentsImagePath;
        this.recipeContents = recipeContents;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.uploadDate = uploadDate;
    }

    public String getRecipeContentsImagePath() {
        return recipeContentsImagePath;
    }

    public void setRecipeContentsImagePath(String recipeContentsImagePath) {
        this.recipeContentsImagePath = recipeContentsImagePath;
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
