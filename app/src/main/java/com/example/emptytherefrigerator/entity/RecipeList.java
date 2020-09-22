package com.example.emptytherefrigerator.entity;

//이달의 추천 레시피 객체
public class RecipeList {
    private int image;
    private String title;
    private String name;
    private int count;

    RecipeList(){}
    public RecipeList(int image, String title, String name, int count)
    {
        this.image = image;
        this.title = title;
        this.name = name;
        this.count=count;
    }

    public int getImage(){return image;}
    public String getTitle(){return title;}
    public String getName(){return name;}
    public int getCount(){return count;}

    public void setImage(int image){this.image = image;}
    public void setTitle(String title){this.title = title;}
    public void setName(String name){this.name = name;}
    public void setCount(int count){this.count = count;}
}
