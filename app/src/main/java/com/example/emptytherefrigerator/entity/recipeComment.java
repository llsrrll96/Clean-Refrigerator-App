package com.example.emptytherefrigerator.entity;

public class recipeComment
{
    private int recipeId;
    private int commentId;
    private String recipeTitle;
    private String userId;  //댓글 등록자 id
    private String content; //댓글 내용
    private String uploadDate;  //댓글 작성 날짜

    public recipeComment()
    {

    }
    public recipeComment(int recipeId, int commentId, String recipeTitle, String userId, String content, String uploadDate)
    {
        this.recipeId = recipeId;
        this.commentId = commentId;
        this.recipeTitle = recipeTitle;
        this.userId = userId;
        this.content = content;
        this.uploadDate = uploadDate;
    }

    public int getRecipeId() {return recipeId;}
    public void setRecipeId(int recipeId) {this.recipeId = recipeId;}
    public int getCommentId() {return commentId;}
    public void setCommentId(int commentId) {this.commentId = commentId;}
    public String getRecipeTitle() {return recipeTitle;}
    public void setRecipeTitle(String recipeTitle) {this.recipeTitle = recipeTitle;}
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}
    public String getUploadDate() {return uploadDate;}
    public void setUploadDate(String uploadDate) {this.uploadDate = uploadDate;}
}
