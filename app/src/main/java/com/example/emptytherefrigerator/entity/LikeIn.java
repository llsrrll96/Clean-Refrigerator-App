package com.example.emptytherefrigerator.entity;

public class LikeIn
{
    private int recipeInId;
    private String userId;
    //private int likeInId;
    private String title;
    private String writerId;
    private String uploadDate;
    private String[] recipeImageByte; //다중 레시피 이미지 데이터

    public LikeIn(int recipeInId, String userId, /*int likeInId, */String writerId, String uploadDate, String[] recipeImageByte)
    {
        this.recipeInId = recipeInId;
        this.userId = userId;
       // this.likeInId = likeInId;
        this.writerId = writerId;
        this.uploadDate = uploadDate;
        this.recipeImageByte = recipeImageByte;
    }

    public int getRecipeInId() {return recipeInId;}
    public void setRecipe(int recipe) {this.recipeInId = recipeInId;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

//    public int getLikeInId() { return likeInId; }
//    public void setLikeInId(int likeInId) { this.likeInId = likeInId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getWriterId() { return writerId; }
    public void setWriterId(String writerId) { this.writerId = writerId; }

    public String getUploadDate() { return uploadDate; }
    public void setUploadDate(String uploadDate) { this.uploadDate = uploadDate; }

    public String[] getRecipeImageByte() { return recipeImageByte; }
    public void setRecipeImageByte(String[] recipeImageByte) { this.recipeImageByte = recipeImageByte; }
}
