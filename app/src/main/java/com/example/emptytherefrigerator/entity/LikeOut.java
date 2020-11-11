package com.example.emptytherefrigerator.entity;

public class LikeOut
{
    private RecipeOut recipeOut;
    private String uploadDate;

    public LikeOut(){}
    public LikeOut(RecipeOut recipeOut)
    {
        this.recipeOut= recipeOut;
    }

    public RecipeOut getRecipeOut() { return recipeOut; }
    public void setRecipeOut(RecipeOut recipeOut) { this.recipeOut = recipeOut; }

    public String getUploadDate() { return uploadDate; }
    public void setUploadDate(String uploadDate) { this.uploadDate = uploadDate; }
}
