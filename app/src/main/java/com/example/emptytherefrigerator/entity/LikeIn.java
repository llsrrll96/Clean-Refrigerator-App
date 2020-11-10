package com.example.emptytherefrigerator.entity;

public class LikeIn
{
    private RecipeIn recipeIn;

    public LikeIn(RecipeIn recipeIn)
    {
        this.recipeIn = recipeIn;
    }

    public RecipeIn getRecipeIn() { return recipeIn; }
    public void setRecipeIn(RecipeIn recipeIn) { this.recipeIn = recipeIn; }
}
