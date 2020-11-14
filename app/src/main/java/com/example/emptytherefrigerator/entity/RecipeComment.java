package com.example.emptytherefrigerator.entity;

public class RecipeComment
{
    private int recipeId;
    private int commentId;
    private String recipeTitle;
    private String userId;  //댓글 등록자 id
    private String content; //댓글 내용
    private String uploadDate;  //댓글 작성 날짜
    private String[] recipeImageByte; //다중 레시피 이미지 데이터
    private String recipeImagePath; //레시피 조리 이미지 경로(들) : ㅇㅇㅇ, ㅇㅇㅇ

    public RecipeComment()
    {

    }
    public RecipeComment(int recipeId, String userId, String content, String uploadDate)
    {
        this.recipeId = recipeId; this.userId = userId; this.content = content; this.uploadDate = uploadDate;
    }
    public RecipeComment(int recipeId, int commentId, String recipeTitle, String userId, String content, String uploadDate)
    {
        this.recipeId = recipeId;
        this.commentId = commentId;
        this.recipeTitle = recipeTitle;
        this.userId = userId;
        this.content = content;
        this.uploadDate = uploadDate;
    }
    public RecipeComment(int recipeId, int commentId, String recipeTitle, String userId, String content, String uploadDate, String []recipeImageByte)
    {
        this.recipeId = recipeId;
        this.commentId = commentId;
        this.recipeTitle = recipeTitle;
        this.userId = userId;
        this.content = content;
        this.uploadDate = uploadDate;
        this.recipeImageByte = recipeImageByte;
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

    public String[] getRecipeImageByte() { return recipeImageByte; }
    public String getRecipeImagePath() { return recipeImagePath; }

    public void setRecipeImageByte(String[] recipeImageByte) { this.recipeImageByte = recipeImageByte; }
    public void setRecipeImagePath(String recipeImagePath) { this.recipeImagePath = recipeImagePath; }
}
