package com.example.emptytherefrigerator.entity;

public class LikeIn
{
    private Recipe recipe;
    private String userId;

    public LikeIn(Recipe recipe, String userId)
    {
        this.recipe = recipe;
        userId = userId;
    }

    public Recipe getRecipe() {return recipe;}
    public void setRecipe(Recipe recipe) {this.recipe = recipe;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
}
