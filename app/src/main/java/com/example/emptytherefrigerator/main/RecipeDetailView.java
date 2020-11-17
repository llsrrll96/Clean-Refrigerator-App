package com.example.emptytherefrigerator.main;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.emptytherefrigerator.AsyncTasks.MyAsyncTask;
import com.example.emptytherefrigerator.AsyncTasks.RecipeMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.login.UserInfo;
import com.example.emptytherefrigerator.main.Comment.CommentListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
    private boolean liked=false;      //좋아요 값을 저장하고 있음, false면 안한거 true면 좋아요 한거
    private boolean alreadyLiked=false; //이전에 좋아요가 되어있는 상태인가

    private RecipeIn recipe;

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
    @Override
    protected void onDestroy()      //뷰가 꺼질때 좋아요에 관한 정보를 확인하고 서버로 관련 작업 요청, 나중에 직접 해봐야됨, 킹론상으로는 작동
    {
        super.onDestroy();
        if(!alreadyLiked&&liked)   //이전에 좋아요를 하지 않은 경우, 하트를 누른 상태
            createLikeIn();
        else if(alreadyLiked&&!liked)   //이전에 좋아요를 했고 하트가 누르지 않은 상태
            deleteLikeIn();

    }

    public void createLikeIn()      //좋아요 등록
    {
        MyAsyncTask inquiry = new MyAsyncTask();
        JSONObject object = new JSONObject();
        try
        {
            object.accumulate("recipeInId", recipe.getRecipeInId());
            object.accumulate("userId", UserInfo.getString(this, UserInfo.ID_KEY));

            String result = inquiry.execute("createLikeIn", object.toString()).get();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void deleteLikeIn()      //좋아요 삭제
    {
        MyAsyncTask inquiry = new MyAsyncTask();
        JSONObject object = new JSONObject();
        try
        {
            object.accumulate("recipeInId", recipe.getRecipeInId());
            object.accumulate("userId", UserInfo.getString(this, UserInfo.ID_KEY));

            String result = inquiry.execute("deleteLikeIn", object.toString()).get();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
//        if(readLikeIn().equals("1"))     //1이면 좋아요 한 상태
//        {
//            btnHeart.setImageResource(R.drawable.like_filled1);
//            liked=true;
//            alreadyLiked=true;
//        }
        //받아온 레시피를 String[] 배열로
        ingredientNames = recipe.getIngredient().split("`");
        ingredientUnits = recipe.getIngredientUnit().split("`");
        recipeContents = recipe.getContents().split("`");
    }
    ///////////////////////////////////////////////////////////////////////////
    public void setListener()
    {
        //채팅 버튼
        btnComments.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), CommentListView.class);
                intent.putExtra("recipeInId", recipe.getRecipeInId());
                startActivity(intent);
            }
        });

        //하트 버튼
        btnHeart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                liked=!liked;   //토글
                if(liked)
                    btnHeart.setImageResource(R.drawable.like_filled1);
                else
                    btnHeart.setImageResource(R.drawable.like);
            }
        });
    }
    public String readLikeIn()
    {
        MyAsyncTask inquiry = new MyAsyncTask();
        JSONObject object = new JSONObject();
        try
        {
            object.accumulate("recipeInId", recipe.getRecipeInId());
            object.accumulate("userId", UserInfo.getString(this, UserInfo.ID_KEY));

            String result = inquiry.execute("readLikeIn", object.toString()).get();
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    ///////////////////////////////////////////////////////////////////////////
    ////////////////////Intent에서 Recipe 데이터를 가져온다.////////////////////////////
    private void getRecipeDataFromIntent()
    {
        try {
            recipe = new RecipeIn();

            Intent intent = getIntent();        //데이터 수신
            int recipeInId = intent.getExtras().getInt("RECIPE");

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("recipeInId", recipeInId);

            RecipeMngAsyncTask recipeMngAsyncTask = new RecipeMngAsyncTask();
            String result = recipeMngAsyncTask.execute("readRecipeDetail", jsonObject.toString()).get();    //서버로 레시피 아이디 보낸다.
            JSONObject jsonObjectResult = new JSONObject(result);
            if(!result.equals("2"))
            {
                recipe.setRecipeInId(jsonObjectResult.getInt("recipeInId"));
                recipe.setTitle(jsonObjectResult.getString("title"));
                recipe.setUserId(jsonObjectResult.getString("userId"));
                recipe.setIngredient(jsonObjectResult.getString("ingredient"));
                recipe.setIngredientUnit(jsonObjectResult.getString("ingredientUnit"));
                recipe.setRecipePerson(jsonObjectResult.getInt("recipePerson"));
                recipe.setRecipeTime(jsonObjectResult.getInt("recipeTime"));
                recipe.setContents(jsonObjectResult.getString("contents"));
                recipe.setCommentCount(jsonObjectResult.getInt("commentCount"));
                recipe.setLikeCount(jsonObjectResult.getInt("likeCount"));
                recipe.setUploadDate(jsonObjectResult.getString("uploadDate"));


                JSONArray jsonArrayImage = jsonObjectResult.getJSONArray("recipeImageBytes");
                String[] recipeImageBytes = new String [jsonArrayImage.length()];

                for(int j= 0; j < jsonArrayImage.length(); j++)
                {
                    JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(j);
                    recipeImageBytes[j] = jsonObjectImage.getString("recipeImageByte");
                }
                recipe.setRecipeImageByte(recipeImageBytes);

            }

        }catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    ////////////////////////스트링 이미지 데이터 -> 비트맵으로///////////////////////////////////
    public Bitmap StringToBitmap(String encodedString)
    {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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
        recipeImage.setImageBitmap(StringToBitmap(recipe.getRecipeImageByte()[0]));//이미지
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

            // 요리 방법 //
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
            countTextView.setText(Integer.toString(i+1));           // 1, 2, 3 ..
            contentsImageView.setImageBitmap(StringToBitmap(recipe.getRecipeImageByte()[i+1]));             //이미지
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