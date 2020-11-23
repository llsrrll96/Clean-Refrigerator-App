package com.example.emptytherefrigerator.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.AsyncTasks.RecipeMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.login.UserInfo;
import com.example.emptytherefrigerator.userView.MyRecipe.MyRecipeListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class RecipeDetailUpdateView extends AppCompatActivity
{
    private EditText etTitle;
    private ImageView imageRecipeDetail;        //대표 이미지
    private EditText etInfoPersonCnt;           //인원
    private EditText etInfoTime;                 //조리시간
    private ImageButton btnAddIngredient;
    private ImageButton btnRemoveIngredient;
    private ImageButton btnAddRecipe;
    private ImageButton btnRemoveRecipe;
    private Button btnUpdate;
    private Button btnCancel;

    private RecipeIn recipe;

    //EditText에 set하는 용도
    private String[] ingredientNames;               //여러개의 재료들
    private String[] ingredientUnits;               //여러개의 재료 단위 (없앰)
    private String[] recipeContents;                //여러개의 요리 방법

    //수정시 서버에 데이터 보내는 용도
    private List<EditText> etIngredientList;        //재료 리스트
    private List<EditText> etIngredientUnitList;    //재료단위 리스트
    private List<Spinner> spIngredientUnitList;     //ex)개, kg
    private List<TextView> recipeCountList;         //요리 방법 카운트 텍스트 뷰
    private List<ImageView> recipeImageViewList;    //요리 방법 이미지 리스트 ( 첫 이미지 경로는 대표이미지 경로 두번째 부터 요리방법 이미지 )
    private List<EditText> recipeContentList;       //요리 방법 설명 editText 리스트

    private int imageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_update);

        initializeView();
        getRecipeDataFromServer();
        setListener();

        //받아온 레시피를 String[] 배열로
        ingredientNames = recipe.getIngredient().split("`");
        ingredientUnits = recipe.getIngredientUnit().split("`");
        recipeContents = recipe.getContents().split("`");

        showTitle();
        showImageRecipeDetail();
        showRecipeInfo();
        showIngredients();
        showRecipeContents();

    }

    public void initializeView()
    {
        etTitle = (EditText) findViewById(R.id.et_Title);
        imageRecipeDetail = (ImageView) findViewById(R.id.image_recipeDetail);
        etInfoPersonCnt = (EditText) findViewById(R.id.et_info_personCount);
        etInfoTime = (EditText) findViewById(R.id.et_info_time);
        btnAddIngredient = (ImageButton)findViewById(R.id.btn_updateLayout_addIngredient);
        btnRemoveIngredient = (ImageButton) findViewById(R.id.btn_updateLayout_removeIngredient);
        btnAddRecipe = (ImageButton)findViewById(R.id.btn_updateLayout_addRecipe);
        btnRemoveRecipe = (ImageButton) findViewById(R.id.btn_updateLayout_removeRecipe);
        btnUpdate = (Button) findViewById(R.id.btn_Update);
        btnCancel = (Button) findViewById(R.id.btn_Cancel);

        //식재료와 단위 저장 리스트
        etIngredientList = new ArrayList<EditText>();
        etIngredientUnitList = new ArrayList<EditText>();
        spIngredientUnitList = new ArrayList<Spinner>();
        recipeCountList = new ArrayList<TextView>();
        recipeImageViewList = new ArrayList<ImageView>();
        recipeContentList = new ArrayList<EditText>();
    }


    public void setListener()
    {
        //대표 이미지 버튼
        imageRecipeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDetailImage();
            }
        });

        //재료 추가 버튼
        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ingredientIsEmpty()) setIngredient();
            }
        });

        //재료 삭제 버튼
        btnRemoveIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popIngredient();
            }
        });

        //요리 방법 추가 버튼
        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!recipeValueIsEmpty())
                    setRecipeContent();
            }
        });

        //요리 방법 삭제 버튼
        btnRemoveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popRecipe();
            }
        });
        //수정 버튼
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String result = "2";
                try {
                    result = sendRegisterDataToServer();
                } catch (JSONException e) { e.printStackTrace();
                } catch (ExecutionException e) { e.printStackTrace();
                } catch (InterruptedException | IOException e) { e.printStackTrace();
                } catch (Exception e) {}
                if(result.equals("1"))
                {
                    Toast.makeText(v.getContext(),"수정 성공",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MyRecipeListView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    getApplicationContext().startActivity(intent);
                }else
                {
                    Toast.makeText(v.getContext(),"입력 양식에 맞게 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //취소 버튼
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    //재료 값 있으면 트루
    private boolean ingredientIsEmpty()
    {
        boolean result = false;
        for(int i =0 ; i < etIngredientList.size(); i++)
        {
            if(etIngredientList.get(i).getText().toString().matches("") || etIngredientUnitList.get(i).getText().toString().matches(""))
                result = true;
        }
        return result;
    }
    //요리 방법 있는 지 없는지
    private boolean recipeValueIsEmpty()
    {
        boolean result = false;
        for(int i = 0; i < recipeContentList.size(); i++)
        {
            if(recipeContentList.get(i).getText().toString().matches("")||
                    etIngredientUnitList.get(i).getText().toString().matches(""))
                result = true;
        }
        return result;
    }
    /////////////////////////////////////////////////////////////////////////////
    ////////////////////Intent에서 Recipe 데이터를 가져온다.
    private void getRecipeDataFromServer()
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

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    public void showTitle()
    {
        etTitle.setText(recipe.getTitle());
    }

    public void showImageRecipeDetail() //대표 이미지
    {
        imageRecipeDetail.setImageBitmap(stringToBitmap(recipe.getRecipeImageByte()[0]));
        recipeImageViewList.add(imageRecipeDetail);
    }

    public void showRecipeInfo()
    {
        etInfoPersonCnt.setText(Integer.toString(recipe.getRecipePerson()));
        etInfoTime.setText(Integer.toString(recipe.getRecipeTime()));
    }
    /////////////////////////////////////////////////////////////////////////////
    //재료
    private void showIngredients() //예) 식재료1      1개
    {
        int spinnerPosition = 0;
        //데이터 넣는 부분
        for (int i = 0; i < ingredientNames.length; i++)
        {
            //1. TextView 객체 생성 한다, 2. 속성등록 한다. 반복문
            EditText etIngredient = new EditText(this);
            EditText etIngredientCnt = new EditText(this);

            etIngredient.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
            etIngredient.setPadding(20,20,20,20);
            etIngredient.setTextSize(15);
            etIngredient.setText(ingredientNames[i]);

            etIngredientCnt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
            etIngredientCnt.setPadding(20,20,20,20);
            etIngredientCnt.setTextSize(15);
            etIngredientCnt.setHint("재료 양");
            etIngredientCnt.setInputType(0x00000002);                       //숫자만
            etIngredientCnt.setText(ingredientUnits[i].replaceAll("[^0-9]", ""));

            Spinner spIngredientUnit = new Spinner(this);
            spIngredientUnit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
            spIngredientUnit.setPadding(20,20,20,20);
            //단위 추가
            String[] units = getResources().getStringArray(R.array.unit);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, units);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spIngredientUnit.setAdapter(adapter);

            spinnerPosition = adapter.getPosition(ingredientUnits[i].replaceAll("\\d", ""));
            spIngredientUnit.setSelection(spinnerPosition);

            //recipe_detail에서 식재료 레이아웃 그려줄 목표 레이아웃, 추가한다.
            LinearLayout ingredientsLayout = (LinearLayout)findViewById(R.id.ingredientsLayout_update);
            LinearLayout subIngredientLayout = new LinearLayout(this);

            subIngredientLayout.setOrientation(LinearLayout.HORIZONTAL);
            subIngredientLayout.addView(etIngredient);
            subIngredientLayout.addView(etIngredientCnt);
            subIngredientLayout.addView(spIngredientUnit);

            ingredientsLayout.addView(subIngredientLayout);

            //수정을 위한 리스트 추가
            etIngredientList.add(etIngredient);
            etIngredientUnitList.add(etIngredientCnt);
            spIngredientUnitList.add(spIngredientUnit);
        }
    }
    /////////////////////////////////////////////////////////////////////////////
    //요리 방법
    private void showRecipeContents()
    {
        for(int i =0; i < recipeContents.length; i++)
        {
            //1. TextView 객체 생성 한다, 2. 속성등록 한다. 반복문 예정
            TextView tvCount = new TextView(this);        //조리순서
            ImageView imageViewContents = new ImageView(this);  //조리 이미지
            EditText etContents = new EditText(this);     //조리 설명

            // 요리 방법 //
            LinearLayout.LayoutParams paramsCnt = new LinearLayout.LayoutParams(100, 100);
            paramsCnt.gravity = Gravity.CENTER;

            // 요리 방법 카운트
            tvCount.setLayoutParams(paramsCnt);
            tvCount.setPadding(20, 0, 0, 0);
            tvCount.setTextSize(15);

            // 요리 방법 이미지
            LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(300, 300);
            imageViewContents.setLayoutParams(paramsImg);
            imageViewContents.setPadding(0, 20, 0, 20);
            imageViewContents.setScaleType(ImageView.ScaleType.FIT_XY);
            if(imageViewContents.getDrawable() == null)
                imageViewContents.setImageResource(R.drawable.logo);                                        //이미지

            final int finalI = i+1;                         //대표 이미지의 index = 0, 나머지 1부터
            imageViewContents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 앨범 호출
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    imageIndex = finalI;
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 2);
                }
            });

            //요리 방법 내용
            LinearLayout.LayoutParams paramsContents = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsContents.gravity = Gravity.CENTER;
            etContents.setLayoutParams(paramsContents);
            etContents.setTextSize(15);
            etContents.setPadding(10, 0, 0, 0);

            //////////////////데이터 넣는 부분////////////////////////////////////////////
            tvCount.setText(Integer.toString(i+1));           // 1, 2, 3 ..
            imageViewContents.setImageBitmap(stringToBitmap(recipe.getRecipeImageByte()[i+1]));             //이미지
            etContents.setText(recipeContents[i]);


            //recipe_detail에서 식재료 레이아웃 그려줄 목표 레이아웃, 추가한다.
            LinearLayout recipeContentLayoutUpdate = (LinearLayout) findViewById(R.id.recipeContentLayout_update);
            LinearLayout subRecipeContentLayout = new LinearLayout(this);
            subRecipeContentLayout.setOrientation(LinearLayout.HORIZONTAL);

            subRecipeContentLayout.addView(tvCount);
            subRecipeContentLayout.addView(imageViewContents);
            subRecipeContentLayout.addView(etContents);
            recipeContentLayoutUpdate.addView(subRecipeContentLayout);

            recipeCountList.add(tvCount);
            recipeImageViewList.add(imageViewContents);
            recipeContentList.add(etContents);
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    private Bitmap stringToBitmap(String encodedString)                      //스트링 이미지 데이터 -> 비트맵으로
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

    //모든 이미지 데이터를 가져온다.
    //이미지 갯수는 대표 이미지 + 요리 방법에 들어가는 이미지 갯수
    private String[] getByteArrayFromBitmap(List<ImageView> imageViewList, int count)
    {
        String[] data = new String[count];
        System.out.println("count: " + count);

        for(int idx = 0; idx < count; idx++) {
            //비트맵으로 바꾼다
            BitmapDrawable drawable = (BitmapDrawable) imageViewList.get(idx).getDrawable();
            Bitmap bitmapImage = drawable.getBitmap();
            //바이트로 바꾸는 작업
            System.out.println(idx + " : " + imageViewList.get(idx).getWidth());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

            String streamData = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            data[idx] = streamData;
        }

        return data;
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    ///////////////////////수정을 위한 데이터 처리 //////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    public void setDetailImage() // 앨범에서 이미지 가져오기 버튼
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
    private void popIngredient()
    {
        if(etIngredientList.size() >=2) {
            etIngredientList.get(etIngredientList.size() - 1).setVisibility(EditText.GONE);
            etIngredientList.remove(etIngredientList.size() - 1);
            etIngredientUnitList.get(etIngredientUnitList.size() - 1).setVisibility(EditText.GONE);
            etIngredientUnitList.remove(etIngredientUnitList.size() - 1);
            spIngredientUnitList.get(spIngredientUnitList.size() - 1).setVisibility(Spinner.GONE);
            spIngredientUnitList.remove(spIngredientUnitList.size() - 1);
        }
    }
    private void popRecipe()
    {
        if(recipeCountList.size() >=2) {
            recipeCountList.get(recipeCountList.size() - 1).setVisibility(View.INVISIBLE);
            recipeCountList.remove(recipeCountList.size() - 1);
            recipeImageViewList.get(recipeImageViewList.size() - 1).setVisibility(ImageView.GONE);
            recipeImageViewList.remove(recipeImageViewList.size() - 1);
            recipeContentList.get(recipeContentList.size() - 1).setVisibility(EditText.GONE);
            recipeContentList.remove(recipeContentList.size() - 1);
        }
    }
    ////////////////////////////재료////////////////////////////
    public void setIngredient()
    {
        EditText etIngredient = new EditText(this);
        etIngredient.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
        etIngredient.setPadding(20,20,20,20);
        etIngredient.setTextSize(15);
        etIngredient.setHint("재료명");

        EditText etIngredientUnit = new EditText(this);
        etIngredientUnit.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
        etIngredientUnit.setPadding(20,20,20,20);
        etIngredientUnit.setHint("재료 양");
        etIngredientUnit.setTextSize(15);
        etIngredientUnit.setInputType(0x00000002);           //숫자만

        Spinner spIngredientUnit = new Spinner(this);
        spIngredientUnit.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
        spIngredientUnit.setPadding(20,20,20,20);
        //단위 추가
        String[] units = getResources().getStringArray(R.array.unit);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, units);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spIngredientUnit.setAdapter(adapter);

        //recipe_detail에서 식재료 레이아웃 그려줄 목표 레이아웃, 추가한다.
        LinearLayout ingredientsLayout = (LinearLayout)findViewById(R.id.ingredientsLayout_update);
        LinearLayout subIngredientLayout = new LinearLayout(this);

        subIngredientLayout.addView(etIngredient);
        subIngredientLayout.addView(etIngredientUnit);
        subIngredientLayout.addView(spIngredientUnit);
        ingredientsLayout.addView(subIngredientLayout);

        etIngredientList.add(etIngredient);                         //생성된 재료 editText 를 넣는다.
        etIngredientUnitList.add(etIngredientUnit);                 //생성된 재료단위 editText 를 넣는다.
        spIngredientUnitList.add(spIngredientUnit);
    }

    /////////////////////요리방법 추가///////////////////////////
    public void setRecipeContent()
    {
        final int recipeContCnt = recipeImageViewList.size();

        // 요리 방법 //
        TextView tvRecipeCount = new TextView(this);
        LinearLayout.LayoutParams paramsCnt = new LinearLayout.LayoutParams(100, 100);
        paramsCnt.gravity = Gravity.CENTER;
        tvRecipeCount.setLayoutParams(paramsCnt);
        tvRecipeCount.setPadding(20,0,0,0);
        tvRecipeCount.setText(Integer.toString(recipeContCnt));

        final ImageView recipeImageView = new ImageView(this);
        // 요리 방법 이미지
        LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(300, 300);
        recipeImageView.setLayoutParams(paramsImg);
        recipeImageView.setPadding(0,20,0,20);
        recipeImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        recipeImageView.setImageResource(R.drawable.logo);
        recipeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 앨범 호출
                Intent intent = new Intent();
                intent.setType("image/*");
                imageIndex = recipeContCnt;
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });

        EditText etContents = new EditText(this);
        LinearLayout.LayoutParams paramsContents = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsContents.gravity = Gravity.CENTER;
        etContents.setLayoutParams(paramsContents);
        etContents.setPadding(10,0,0,0);
        etContents.setTextSize(15);
        etContents.setHint("설명");

        //recipe_detail에서 식재료 레이아웃 그려줄 목표 레이아웃, 추가한다.
        LinearLayout recipeContentLayoutUpdate = (LinearLayout) findViewById(R.id.recipeContentLayout_update);
        LinearLayout subRecipeContentLayout = new LinearLayout(this);
        subRecipeContentLayout.setOrientation(LinearLayout.HORIZONTAL);

        subRecipeContentLayout.addView(tvRecipeCount);
        subRecipeContentLayout.addView(recipeImageView);
        subRecipeContentLayout.addView(etContents);
        recipeContentLayoutUpdate.addView(subRecipeContentLayout);

        recipeCountList.add(tvRecipeCount);
        recipeImageViewList.add(recipeImageView);
        recipeContentList.add(etContents);
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap repPhotoBitmap;
        if (requestCode == 1)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    repPhotoBitmap = BitmapFactory.decodeStream(in);
                    in.close();

                    // 대표 이미지 설정
                    imageRecipeDetail.setImageBitmap(repPhotoBitmap); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == 2)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    repPhotoBitmap = BitmapFactory.decodeStream(in);
                    in.close();

                    // 요리 정보 이미지 표시
                    recipeImageViewList.get(imageIndex).setImageBitmap(repPhotoBitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    ////////////////////////////////사용자가 입력한 데이터를 recipe 객체에 set///////////////////////////////
    public void setRecipeData() throws IOException
    {
        try
        {
            //유저아이디, 타이틀, 인원, 조리시간
            String strUserId = UserInfo.getString(this, "USER_ID");
            recipe.setUserId(strUserId);
            recipe.setTitle(etTitle.getText().toString());
            recipe.setRecipePerson(Integer.parseInt(etInfoPersonCnt.getText().toString()));
            recipe.setRecipeTime(Integer.parseInt(etInfoTime.getText().toString()));
            //

            //대표 이미지, 저장
            //이미지 갯수는 대표 이미지 + 요리 방법에 들어가는 이미지 갯수
            recipe.setRecipeImageByte(getByteArrayFromBitmap(recipeImageViewList, recipeImageViewList.size()));
            //

            //재료 데이터
            String ingredient = "";String ingredientUnit= "";
            int i = 0;int j = 0;
            for (; i < etIngredientList.size(); i++, j++) {
                ingredient += etIngredientList.get(i).getText().toString();
                ingredientUnit += etIngredientUnitList.get(i).getText().toString();
                ingredientUnit += spIngredientUnitList.get(i).getSelectedItem().toString();
                if (i != etIngredientList.size() - 1) {
                    ingredient += "`";
                    ingredientUnit += "`";
                }
            }
            recipe.setIngredient(ingredient);
            recipe.setIngredientUnit(ingredientUnit);
            //

            //요리 방법 데이터
            String recipeContents = "";
            for (int contCnt = 0; contCnt < recipeContentList.size(); contCnt++) {
                recipeContents += recipeContentList.get(contCnt).getText().toString();
                if (contCnt != recipeContentList.size() - 1) {
                    recipeContents += "`";
                }
            }
            recipe.setContents(recipeContents);

        }catch (Exception e)
        {
            Toast.makeText(this.getApplicationContext(),"내용을 모두 입력해 주세요",Toast.LENGTH_SHORT).show();
        }
    }

    // 레시피 정보 JSON 전송 및 결과 수신
    public String sendRegisterDataToServer() throws JSONException, ExecutionException, InterruptedException, IOException
    {
        JSONObject recipeJSON = new JSONObject();
        setRecipeData();
        makeRecipeJSON(recipeJSON);

        String result = new RecipeMngAsyncTask().execute("updateRecipe", recipeJSON.toString()).get();
        return result;
    }

    // 레시피 JSON 만들기
    public void makeRecipeJSON(JSONObject json) throws JSONException
    {
        json.accumulate("recipeInId", recipe.getRecipeInId());
        json.accumulate("title", recipe.getTitle());
        json.accumulate("userId", recipe.getUserId());
        json.accumulate("ingredient", recipe.getIngredient());
        json.accumulate("ingredientUnit", recipe.getIngredientUnit());
        json.accumulate("recipePerson", recipe.getRecipePerson());
        json.accumulate("recipeTime", recipe.getRecipeTime());
        json.accumulate("contents", recipe.getContents());

        JSONArray recipeImages = new JSONArray();
        for (int i = 0; i < recipe.getRecipeImageByte().length; i++) {
            char n = (char) (i + '0');
            JSONObject imgJSON = new JSONObject();
            imgJSON.accumulate("recipeImageByte", recipe.getRecipeImageByte()[i]);
            recipeImages.put(imgJSON);
        }
        json.accumulate("recipeImageBytes", recipeImages.toString());
    }
}