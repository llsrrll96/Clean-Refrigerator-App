package com.example.emptytherefrigerator.entity;

public class LikeIn
{
    private RecipeIn recipeIn;
    private String uploadDate;      //좋아요를 등록한 날짜

    public LikeIn() { }
    public LikeIn(RecipeIn recipeIn)
    {
        this.recipeIn = recipeIn;
    }

    public RecipeIn getRecipeIn() { return recipeIn; }
    public void setRecipeIn(RecipeIn recipeIn) { this.recipeIn = recipeIn; }

    public String getUploadDate() {return uploadDate;}
    public void setUploadDate(String uploadDate) {this.uploadDate=uploadDate;}
}
