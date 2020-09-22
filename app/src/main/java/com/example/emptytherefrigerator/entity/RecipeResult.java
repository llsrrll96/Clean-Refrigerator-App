package com.example.emptytherefrigerator.entity;

public class RecipeResult
{
    private int image;
    private String title;
    private int chatNumber;
    private int likeNumber;
    private String uploadDate;

    public RecipeResult(int image, String title, int chatNumber, int likeNumber, String uploadDate) {
        this.image = image;
        this.title = title;
        this.chatNumber = chatNumber;
        this.likeNumber = likeNumber;
        this.uploadDate = uploadDate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChatNumber() {
        return chatNumber;
    }

    public void setChatNumber(int chatNumber) {
        this.chatNumber = chatNumber;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
