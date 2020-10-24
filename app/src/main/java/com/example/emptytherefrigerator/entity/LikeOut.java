package com.example.emptytherefrigerator.entity;

public class LikeOut
{
    private int recipeOutId;
    private String userId;

    public LikeOut(int recipeOutId, String userId)
    {
        this.recipeOutId = recipeOutId; this.userId = userId;
    }

    public int getRecipeOutId() { return recipeOutId; }
    public String getUserId() { return userId; }

    public void setRecipeOutId(int recipeOutId) { this.recipeOutId = recipeOutId; }
    public void setUserId(String userId) { this.userId = userId; }

}
