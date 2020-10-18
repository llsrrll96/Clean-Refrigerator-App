package com.example.emptytherefrigerator.entity;

public class Recipe
{
    private String recipeId;
    private String title;
    private String userId;
    private String ingredient;      //식재료
    private String ingredientUnit;   //식재료 단위
    private int recipePerson;       //몇 인분
    private int recipeTime;         //조리 시간
    private String[] recipeImageByte; //다중 레시피 이미지 데이터
    private String recipeImagePath; //레시피 조리 이미지 경로(들) : ㅇㅇㅇ, ㅇㅇㅇ
    private String recipeContents;  //레시피 조리법 설명
    private int commentCount;         //댓글 개수
    private int likeCount;         //좋아요 개수
    private String uploadDate;      //작성 날짜

    /*private byte[row][col] recipeImageByte 데이터는
     * row 는 사진 갯수, col 은 해당 이미지 데이터 byte 코드
     * */

    public Recipe() {
    }

    //검색결과
    public Recipe(String recipeImagePath, String title, int commentCount, int likeCount, String uploadDate) {
        this.recipeImagePath = recipeImagePath;
        this.title = title;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.uploadDate = uploadDate;
    }

    //등록
    public Recipe(String title, String ingredient, String ingredientUnit, int recipePerson, int recipeTime, String recipeImagePath, String recipeContents) {
        this.title = title;
        this.ingredient = ingredient;
        this.ingredientUnit = ingredientUnit;
        this.recipePerson = recipePerson;
        this.recipeTime = recipeTime;
        this.recipeImagePath = recipeImagePath;
        this.recipeContents = recipeContents;
    }

    //조회용
    public Recipe(String recipeId, String title, String userId, String ingredient , int recipePerson, int recipeTime, String recipeImagePath,String recipeContents, int commentCount, int likeCount, String uploadDate) {
        this.recipeId = recipeId;
        this.title = title;
        this.userId = userId;
        this.ingredient = ingredient;
        this.recipePerson = recipePerson;
        this.recipeTime = recipeTime;
        this.recipeImagePath = recipeImagePath;
        this.recipeContents = recipeContents;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.uploadDate = uploadDate;
    }

    public String[] getRecipeImageByte() {
        return recipeImageByte;
    }

    public void setRecipeImageByte(String[] recipeImageByte) {
        this.recipeImageByte = recipeImageByte;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
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

    public String getRecipeImagePath() {
        return recipeImagePath;
    }

    public void setRecipeImagePath(String recipeImagePath) {
        this.recipeImagePath = recipeImagePath;
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