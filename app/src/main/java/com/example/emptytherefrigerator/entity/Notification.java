package com.example.emptytherefrigerator.entity;

public class Notification
{
    private int notificationId;
    private String userId;
    private RecipeIn recipeIn;
    private int type;

    public Notification() {}
    public Notification(int notificationId) { this.notificationId = notificationId; }

    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public RecipeIn getRecipe() { return recipeIn; }
    public void setRecipe(RecipeIn recipe) { this.recipeIn = recipe; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
}
