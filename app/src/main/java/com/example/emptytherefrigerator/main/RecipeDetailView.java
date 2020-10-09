package com.example.emptytherefrigerator.main;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.R;

import java.util.ArrayList;

//조회된 레시피 엑티비티
public class RecipeDetailView extends AppCompatActivity {

    private ArrayList<Integer> imageList;
    private TextView titleTextView;
    private ImageView recipeImage;
    private ImageView userImageView;
    private TextView userIdTextView;
    private TextView recipeInfoCountTextView;
    private TextView recipeInfoTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        //비동기식
        RecipeMngAsyncTask backgroundTask = new RecipeMngAsyncTask();
        backgroundTask.execute();   //doInBackground

        setInitial();
        setRecipeImg();              //요리이미지
        setIngredients();            //식재료
        setPrice();                 //식재료 가격 (크롤링)
        setRecipeContents();         //조리방법
    }

    void setInitial() {
        recipeImage = (ImageView) findViewById(R.id.recipe_image);
    }

    ////////////////////////////////////////////////////////////////////////
    void setRecipeImg() {
        recipeImage.setImageResource(R.drawable.logo);
    }


    //////////////////////////////////////////////////////////////////////////
    void setIngredients() //예) 식재료1      1개
    {
        //1. TextView 객체 생성 한다, 2. 속성등록 한다. / 반복문 예정
        TextView ingredientTextView = new TextView(this);
        TextView ingredientCntTextView = new TextView(this);
        String ingredientName = "식재료";
        String ingredientQuantity = "20";

        ingredientTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        ingredientTextView.setPadding(20, 20, 20, 20);
        ingredientTextView.setTextSize(15);
        ingredientTextView.setText(ingredientName);

        ingredientCntTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        ingredientCntTextView.setPadding(20, 20, 20, 20);
        ingredientCntTextView.setTextSize(15);
        ingredientCntTextView.setText(ingredientQuantity);

        //recipe_detail에서 식재료 레이아웃 그려줄 목표 레이아웃, 추가한다.
        LinearLayout ingredientsLayout = (LinearLayout) findViewById(R.id.ingredientsLayout);
        ingredientsLayout.addView(ingredientTextView);
        ingredientsLayout.addView(ingredientCntTextView);
    }

    ////////////////////////////////////////////////////////////////////////////
    void setPrice()     //크롤링
    {

        //1. TextView 객체 생성 한다, 2. 속성등록 한다. 반복문 예정
        TextView ingredientTextView = new TextView(this);
        TextView ingredientCntTextView = new TextView(this);
        String ingredientName = "식재료";
        String ingredientPrice = "20200";

        ingredientTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        ingredientTextView.setPadding(20, 20, 20, 20);
        ingredientTextView.setTextSize(15);
        ingredientTextView.setText(ingredientName);

        ingredientCntTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        ingredientCntTextView.setPadding(20, 20, 20, 20);
        ingredientCntTextView.setTextSize(15);
        ingredientCntTextView.setText(ingredientPrice);

        //recipe_detail에서 식재료 레이아웃 그려줄 목표 레이아웃, 추가한다.
        LinearLayout ingredientsLayout = (LinearLayout) findViewById(R.id.priceLayout);
        ingredientsLayout.addView(ingredientTextView);
        ingredientsLayout.addView(ingredientCntTextView);
    }

    String getPrice(String ingredientName)        //식재료를 검색해 가격을 알아온다.
    {
        return "";
    }

    /////////////////////////////////////////////////////////////////////////////
    void setRecipeContents() {
        //1. TextView 객체 생성 한다, 2. 속성등록 한다. 반복문 예정
        TextView countTextView = new TextView(this);        //조리순서
        ImageView contentsImageView = new ImageView(this);  //조리 이미지
        TextView contentsTextView = new TextView(this);     //조리 설명

        // 요리 방법 카운트
        String count = Integer.toString(12);
        LinearLayout.LayoutParams paramsCnt = new LinearLayout.LayoutParams(100, 100);
        paramsCnt.gravity = Gravity.CENTER;
        countTextView.setLayoutParams(paramsCnt);
        countTextView.setPadding(20, 0, 0, 0);
        countTextView.setTextSize(15);
        countTextView.setText(count);


        // 요리 방법 이미지
        LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(300, 300);
        contentsImageView.setLayoutParams(paramsImg);
        countTextView.setPadding(20, 0, 20, 0);
        contentsImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        contentsImageView.setImageResource(R.drawable.logo);

        //요리 방법 내용
        String recipeContent = "오징어 꼴뚜기 대구명태 거북이 연어알 물새알 \n 새들의 고향";
        LinearLayout.LayoutParams paramsContents = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsContents.gravity = Gravity.CENTER;
        contentsTextView.setLayoutParams(paramsContents);
        contentsTextView.setTextSize(15);
        countTextView.setPadding(20, 0, 0, 0);
        contentsTextView.setText(recipeContent);


        //recipe_detail에서 식재료 레이아웃 그려줄 목표 레이아웃, 추가한다.
        LinearLayout createRecipeContentLayout = (LinearLayout) findViewById(R.id.createRecipeContentLayout);
        createRecipeContentLayout.addView(countTextView);
        createRecipeContentLayout.addView(contentsImageView);
        createRecipeContentLayout.addView(contentsTextView);
    }


    //doinbackground 변수 타입, onProgress 타입, onPostExcute 타입
    class RecipeMngAsyncTask extends AsyncTask<Integer, Integer, Integer> {//프리미티브 타입 x, 레퍼런스 타입으로
        int values = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        //ui 처리하면 안된다.
        @Override
        protected Integer doInBackground(Integer... integers) {//Integer 가 여러개일 수 있음


            return null;
        }

        //백그라운드 스레드에서 작업 처리 중에 프로그레스바 진행 상태 업데이트 같은  UI작업이 필요한 경우
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        //doInBackground 종료후 리턴한 값을 파라메터로 받게 된다.
        @Override
        protected void onPostExecute(Integer integer) { //결과 가져와 실행
            super.onPostExecute(integer);

        }

    }
}
