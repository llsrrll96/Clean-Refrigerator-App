package com.example.emptytherefrigerator.entity;

public class LikeIn
{
    private RecipeIn recipe;
    private String userId;
    private int likeInId;

    public LikeIn(RecipeIn recipe, String userId, int likeInId)
    {
        this.recipe = recipe;
        this.userId = userId;
        this.likeInId = likeInId;
    }

    public RecipeIn getRecipe() {return recipe;}
    public void setRecipe(RecipeIn recipe) {this.recipe = recipe;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public int getLikeInId() { return likeInId; }
    public void setLikeInId(int likeInId) { this.likeInId = likeInId; }
}
