package com.example.emptytherefrigerator.entity;

public class LikeIn
{
    private RecipeIn recipe;
    private String userId;

    public LikeIn(RecipeIn recipe, String userId)
    {
        this.recipe = recipe;
        userId = userId;
    }

    public RecipeIn getRecipe() {return recipe;}
    public void setRecipe(RecipeIn recipe) {this.recipe = recipe;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
}
