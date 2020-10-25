package com.example.emptytherefrigerator.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;

import java.util.ArrayList;

//조회된 레시피 엑티비티
public class RecipeDetailView extends AppCompatActivity {

    private ArrayList<Integer> imageList;
    private TextView titleTextView;
    private ImageView recipeImage;
    private TextView userIdTextView;
    private TextView recipeInfoCountTextView;
    private TextView recipeInfoTimeTextView;
    private ImageButton btnComments;
    private ImageButton btnHeart;

    private RecipeIn recipe;

    private String[] recipeImagePaths;               //여러개의 이미지 경로들
    private String[] ingredientNames;               //여러개의 재료들
    private String[] ingredientUnits;               //여러개의 재료 단위
    private String[] recipeContents;                //여러개의 요리 방법

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        setInitial();
        setListener();

        setTitle();                  //제목
        setRecipeImg();              //요리이미지
        setUserId();                //유저 아이디
        setRecipeInfo();            //요리정보
        setIngredients();            //식재료
        setPrice();                 //식재료 가격 (크롤링)
        setRecipeContents();         //조리방법
    }

    private void setInitial()
    {
        titleTextView = (TextView)findViewById(R.id.titleTextView);
        recipeImage = (ImageView)findViewById(R.id.recipe_image);
        userIdTextView = (TextView)findViewById(R.id.userIdTextView); //유저 id
        recipeInfoCountTextView = (TextView)findViewById(R.id.recipeInfoCount);//몇 인분
        recipeInfoTimeTextView = (TextView)findViewById(R.id.recipeInfoTime);//조리 시간
        btnComments = (ImageButton)findViewById(R.id.btnComments);
        btnHeart = (ImageButton) findViewById(R.id.btnHeart);

        //레시피 받아오기
        getRecipeDataFromIntent();
        //받아온 레시피를 String[] 배열로
        recipeImagePaths = recipe.getRecipeImagePath().split(",");
        ingredientNames = recipe.getIngredient().split(",");
        ingredientUnits = recipe.getIngredientUnit().split(",");
        recipeContents = recipe.getContents().split(",");
    }
    ///////////////////////////////////////////////////////////////////////////
    public void setListener()
    {
        //채팅 버튼
        btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //하트 버튼
        btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////
    ////////////////////Intent에서 Recipe 데이터를 가져온다.
    private void getRecipeDataFromIntent()
    {
        //recipe = new Recipe();
        Intent intent = getIntent();        //데이터 수신
        recipe = (RecipeIn)intent.getSerializableExtra("RECIPE");


/*        String userId = "유저 아이디";
        String title = "타이틀";
        String recipeImagePath = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/420px-PNG_transparency_demonstration_1.png," +
                "https://upload.wikimedia.org/wikipedia/commons/5/5f/%EA%B4%91%ED%99%94%EB%AC%B8_Gwanghwamun_%E5%85%89%E5%8C%96%E9%96%80_-_panoramio.jpg," +
                "https://upload.wikimedia.org/wikipedia/commons/0/02/Nordsee_Wellen.JPG," +
                "https://upload.wikimedia.org/wikipedia/commons/5/5f/%EA%B4%91%ED%99%94%EB%AC%B8_Gwanghwamun_%E5%85%89%E5%8C%96%E9%96%80_-_panoramio.jpg";
        String recipePerson = "1";
        String recipeTime = "30";
        String ingredient = "식재료1,식재료2,식재료3";
        String ingredientUnit = "1개,2개,3개";
        String recipeContents = "요리방법 1,요리방법 2,요리방법3";

        recipe.setUserId(userId);
        recipe.setTitle(title);
        recipe.setRecipeImagePath(recipeImagePath);
        recipe.setRecipePerson(Integer.parseInt(recipePerson));
        recipe.setRecipeTime(Integer.parseInt(recipeTime));
        recipe.setIngredient(ingredient);
        recipe.setIngredientUnit(ingredientUnit);
        recipe.setRecipeContents(recipeContents);*/
    }
    ////////////////////////////////////////////////////////////////////////
    //타이틀
    private void setTitle()
    {
        titleTextView.setText(recipe.getTitle());
    }
    /////////////////////////////////////////////////////////////////////////
    //레시피 이미지 넣는다.
    private void setRecipeImg()
    {
        Glide.with(this)
                .load(recipeImagePaths[0])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(recipeImage);                         //이미지
    }
    //////////////////////////////////////////////////////////////////////////
    private void setUserId()
    {
        userIdTextView.setText(recipe.getUserId());
        userIdTextView.setGravity(Gravity.CENTER);
    }
    //////////////////////////////////////////////////////////////////////////
    private void setRecipeInfo()
    {
        //인원
        recipeInfoCountTextView.setText(Integer.toString(recipe.getRecipePerson())+ " 인분");
        //시간
        recipeInfoTimeTextView.setText(Integer.toString(recipe.getRecipeTime()) + " 분");
    }
    //////////////////////////////////////////////////////////////////////////
    //재료
    private void setIngredients() //예) 식재료1      1개
    {
        //데이터 넣는 부분
        for (int i = 0; i < ingredientNames.length; i++)
        {
            //1. TextView 객체 생성 한다, 2. 속성등록 한다. 반복문
            TextView ingredientTextView = new TextView(this);
            TextView ingredientCntTextView = new TextView(this);

            ingredientTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
            ingredientTextView.setPadding(20,20,20,20);
            ingredientTextView.setTextSize(15);
            ingredientTextView.setText(ingredientNames[i]);

            ingredientCntTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
            ingredientCntTextView.setPadding(20,20,20,20);
            ingredientCntTextView.setTextSize(15);
            ingredientCntTextView.setText(ingredientUnits[i]);

            //recipe_detail에서 식재료 레이아웃 그려줄 목표 레이아웃, 추가한다.
            LinearLayout ingredientsLayout = (LinearLayout)findViewById(R.id.ingredientsLayout);
            LinearLayout subIngredientLayout = new LinearLayout(this);
            subIngredientLayout.setOrientation(LinearLayout.HORIZONTAL);
            subIngredientLayout.addView(ingredientTextView);
            subIngredientLayout.addView(ingredientCntTextView);
            ingredientsLayout.addView(subIngredientLayout);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //식재료 가격
    private String getPrice(String ingredientName)        //식재료를 검색해 가격을 알아온다.//크롤링
    {
        return "";
    }

    private void setPrice()     //화면에 가격 출력
    {
        String ingredientName = "식재료";
        String ingredientPrice = "20200";

        //1. TextView 객체 생성 한다, 2. 속성등록 한다. 반복문 예정
        TextView ingredientTextView = new TextView(this);
        TextView ingredientCntTextView = new TextView(this);

        ingredientTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
        ingredientTextView.setPadding(20,20,20,20);
        ingredientTextView.setTextSize(15);
        ingredientTextView.setText(ingredientName);

        ingredientCntTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
        ingredientCntTextView.setPadding(20,20,20,20);
        ingredientCntTextView.setTextSize(15);
        ingredientCntTextView.setText(ingredientPrice);

        //recipe_detail에서 식재료 레이아웃 그려줄 목표 레이아웃, 추가한다.
        LinearLayout ingredientsLayout = (LinearLayout)findViewById(R.id.priceLayout);
        ingredientsLayout.addView(ingredientTextView);
        ingredientsLayout.addView(ingredientCntTextView);
    }


    /////////////////////////////////////////////////////////////////////////////
    //요리 정보
    private void setRecipeContents()
    {
        for(int i =0; i < recipeContents.length; i++)
        {
            //1. TextView 객체 생성 한다, 2. 속성등록 한다. 반복문 예정
            TextView countTextView = new TextView(this);        //조리순서
            ImageView contentsImageView = new ImageView(this);  //조리 이미지
            TextView contentsTextView = new TextView(this);     //조리 설명

            // 요리 방법
            LinearLayout.LayoutParams paramsCnt = new LinearLayout.LayoutParams(100, 100);
            paramsCnt.gravity = Gravity.CENTER;

            // 요리 방법 카운트
            countTextView.setLayoutParams(paramsCnt);
            countTextView.setPadding(20, 0, 0, 0);
            countTextView.setTextSize(15);

            // 요리 방법 이미지
            LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(300, 300);
            contentsImageView.setLayoutParams(paramsImg);
            contentsImageView.setPadding(0, 20, 0, 20);
            contentsImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //contentsImageView.setImageResource(R.drawable.logo);                                        //이미지

            //요리 방법 내용
            LinearLayout.LayoutParams paramsContents = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsContents.gravity = Gravity.CENTER;
            contentsTextView.setLayoutParams(paramsContents);
            contentsTextView.setTextSize(15);
            contentsTextView.setPadding(10, 0, 0, 0);
            //contentsTextView.setText(recipeContent);                                                    //요리내용

            //////////////////데이터 넣는 부분////////////////////////////////////////////
            countTextView.setText(Integer.toString(i+1));
            Glide.with(this)
                    .load(recipeImagePaths[i+1])
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(contentsImageView);                         //이미지
            contentsTextView.setText(recipeContents[i]);


            //recipe_detail에서 식재료 레이아웃 그려줄 목표 레이아웃, 추가한다.
            LinearLayout createRecipeContentLayout = (LinearLayout) findViewById(R.id.createRecipeContentLayout);
            LinearLayout subRecipeContentLayout = new LinearLayout(this);
            subRecipeContentLayout.setOrientation(LinearLayout.HORIZONTAL);
            subRecipeContentLayout.addView(countTextView);
            subRecipeContentLayout.addView(contentsImageView);
            subRecipeContentLayout.addView(contentsTextView);
            createRecipeContentLayout.addView(subRecipeContentLayout);
        }
    }
}