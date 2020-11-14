package com.example.emptytherefrigerator.entity;

public class RecipeOut
{
    private int recipeOutId;
    private String link;
    private String title;
    private String ingredient;
    private String[] recipeImageByte; //다중 레시피 이미지 데이터

    public RecipeOut() {}
    public RecipeOut(int reicpeOutId, String link, String title, String ingredient)
    {
        this.recipeOutId = reicpeOutId; this.link = link; this.title = title; this.ingredient = ingredient;
    }
    public RecipeOut(int reicpeOutId, String link, String title)
    {
        this.recipeOutId = reicpeOutId; this.link = link; this.title = title;
    }

    public int getRecipeOutId() { return recipeOutId; }
    public String getLink() { return link; }
    public String getTitle() { return title; }
    public String getIngredient() { return ingredient; }
    public String[] getRecipeImageByte() { return recipeImageByte; }

    public void setRecipeOutId(int recipeOutId) { this.recipeOutId = recipeOutId; }
    public void setLink(String link) { this.link = link; }
    public void setTitle(String title) { this.title = title; }
    public void setIngredient(String ingredient) { this.ingredient = ingredient; }
    public void setRecipeImageByte(String[] recipeImageByte) { this.recipeImageByte = recipeImageByte; }
}
