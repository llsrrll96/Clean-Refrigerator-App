package com.example.emptytherefrigerator.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.AsyncTasks.RecipeMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//레시피 등록 화면
//등록버튼으로 데이터 서버에 전송 후 완료 메시지를 빋아온다.
public class RecipeDetailCreateView extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;

    private EditText editTextTitle;

    private ImageView recipeDetailImage;
    private int recipeDetailImageIdx;
    private boolean isDetail;

    private EditText recipeInfoCount;
    private EditText recipeInfoTime;

    private LinearLayout recipeIngredientLayout;
    private LinearLayout recipeContentLayout;

    private ImageButton btnIngredient;          //재료 edittext 추가 버튼
    private ImageButton btnRecipe;
    private int ingredientCnt, recipeCnt;

    private List<EditText> etIngredientList;        //재료 리스트
    private List<EditText> etIngredientUnitList;    //재료단위 리스트
    private List<ImageView> recipeImageViewList;    //요리 방법 이미지 리스트 ( 첫 이미지 경로는 대표이미지 경로 두번째 부터 요리방법 이미지 )
    private List<EditText> recipeContentList;       //요리 방법 설명 editText 리스트


    private Button btnRegister;
    private Button btnCancel;

    private Uri mImageCaptureUri;
    private String absoultePath ="";

    private int recipeContCnt;                  //요리방법 갯수
    private RecipeIn recipe;                      //레시피 클래스 객체
    private String ingredient = "";
    private String ingredientUnit ="";
    private Bitmap repPhotoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_create_view);

        initialize();

        setListener();
    }
/////////////////////////////////////////////////////////////////////////

    public void initialize()
    {
        isDetail = false;
        recipeDetailImageIdx = -1;
        editTextTitle=(EditText) findViewById(R.id.editTextTitle);
        //대표 이미지
        //이미지뷰를 눌러 이미지 선택하면 옆에 선택한 이미지 생성
        recipeDetailImage = (ImageView) findViewById(R.id.recipeDetailImage);
        recipeInfoCount = (EditText)findViewById(R.id.recipeInfoCount);
        recipeInfoTime = (EditText)findViewById(R.id.recipeInfoTime);
        //레이아웃
        recipeIngredientLayout = (LinearLayout)findViewById(R.id.recipeIngredientLayout);
        recipeContentLayout = (LinearLayout)findViewById(R.id.recipeContentLayout);
        //추가버튼
        btnIngredient = (ImageButton) findViewById(R.id.btnIngredient);
        btnRecipe = (ImageButton) findViewById(R.id.btnRecipe);
        //식재료와 단위 저장 리스트
        etIngredientList = new ArrayList<EditText>();
        etIngredientUnitList = new ArrayList<EditText>();
        recipeImageViewList = new ArrayList<ImageView>();
        recipeContentList = new ArrayList<EditText>();
        //등록 취소 버튼
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        //요리 방법
        recipeContCnt = 0;

        recipe = new RecipeIn();

        //대표이미지뷰를 리스트에 추가
        recipeImageViewList.add(recipeDetailImage);
        //권한 체크
        permissionCheck();

        //재료 첫 번째 레이아웃
        setIngredient();
        ingredientCnt = 1;
        //요리 방법 첫번째 레이아웃
        setRecipeContent();
        recipeCnt = 1;
    }

    public void setListener()
    {
        recipeDetailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDetailImage();
            }
        });
        //재료추가 버튼
        btnIngredient.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ingredientCnt++ < 10) {
                    setIngredient();
                }else {
                    Toast.makeText(v.getContext(),"더 이상 늘릴 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //요리 방법 추가 버튼
        btnRecipe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipeCnt++ < 10) {
                    setRecipeContent();
                }else
                    Toast.makeText(v.getContext(),"더 이상 늘릴 수 없습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        //등록 버튼
        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //서버에 등록데이터 보내고 서버에서 받은 메시지로 결과 출력
                //getServerData(sendRegisterDataToServer());
                try {
                    sendRegisterDataToServer();
                } catch (JSONException e) { e.printStackTrace();
                } catch (ExecutionException e) { e.printStackTrace();
                } catch (InterruptedException | IOException e) { e.printStackTrace(); }
            }
        });

        //취소 버튼
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////
    public void setDetailImage() // 앨범에서 이미지 가져오기 버튼
    {
        permissionCheck();

        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        //onActivityResult 호출
        isDetail = true;
        startActivityForResult(intent, PICK_FROM_ALBUM);
        recipeDetailImageIdx = recipeImageViewList.size();
    }

    // 레시피 정보 JSON 전송 및 결과 수신 
    public String sendRegisterDataToServer() throws JSONException, ExecutionException, InterruptedException, IOException {
        JSONObject recipeJSON = new JSONObject();
        setRecipeData();
        makeRecipeJSON(recipeJSON);

        String result = new RecipeMngAsyncTask().execute("createRecipe", recipeJSON.toString()).get();
        return result;
    }

    // 레시피 JSON 만들기
    public void makeRecipeJSON(JSONObject json) throws JSONException {
        json.accumulate("recipeId", recipe.getRecipeInId());
        json.accumulate("title", recipe.getTitle());
        json.accumulate("userId", recipe.getUserId());
        json.accumulate("ingredient", recipe.getIngredient());
        json.accumulate("ingredientUnit", recipe.getIngredientUnit());
        json.accumulate("recipePerson", recipe.getRecipePerson());
        json.accumulate("recipeTime", recipe.getRecipeTime());
        json.accumulate("recipeContents", recipe.getContents());

        JSONArray recipeImages = new JSONArray();
        for (int i = 0; i < recipe.getRecipeImageByte().length; i++) {
            char n = (char) (i + '0');
            JSONObject imgJSON = new JSONObject();
            imgJSON.accumulate("recipeImageByte", recipe.getRecipeImageByte()[i]);
            recipeImages.put(imgJSON);
        }
        json.accumulate("recipeImageBytes", recipeImages.toString());
    }

    //사용자가 입력한 데이터를 recipe 객체에 set
    public void setRecipeData() throws IOException {
        //타이틀, 인원, 조리시간
        recipe.setTitle(editTextTitle.getText().toString());
        recipe.setRecipePerson(Integer.parseInt(recipeInfoCount.getText().toString()));
        recipe.setRecipeTime(Integer.parseInt(recipeInfoTime.getText().toString()));

        //대표 이미지, 저장
        //이미지 갯수는 대표 이미지 + 요리 방법에 들어가는 이미지 갯수

        recipe.setRecipeImageByte(getByteArrayFromBitmap(recipeImageViewList,recipeContCnt+1));

        //재료 데이터
        int i = 0;int j = 0;
        for(; i < etIngredientList.size(); i++, j++)
        {
            ingredient += etIngredientList.get(i).getText().toString();
            ingredientUnit += etIngredientUnitList.get(i).getText().toString();
            if(i != etIngredientList.size() - 1) {
                ingredient += ",";
                ingredientUnit += ",";
            }
        }
        recipe.setIngredient(ingredient);
        recipe.setIngredientUnit(ingredientUnit);

        //요리 방법 데이터
        String recipeContents = "";
        for(int contCnt=0; contCnt < recipeContentList.size(); contCnt++)
        {
            recipeContents += recipeContentList.get(contCnt).getText().toString();
            if(i != recipeContentList.size() -1){
                recipeContents += ",";
            }
        }
        recipe.setContents(recipeContents);
    }

    //모든 이미지 데이터를 가져온다.
    public String[] getByteArrayFromBitmap(List<ImageView> imageViewList, int count)
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


    ///////////////////////////////////////////////////////////////////////////////
    public void permissionCheck()
    {
        //동의 후 실행
        //동적퍼미션 작업
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);                                     //onRequestPermissionsResult
            }
        }else { return;}
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10 :
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED) //사용자가 허가 했다면
                {
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 사용 가능", Toast.LENGTH_SHORT).show();

                }else{//거부했다면
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 제한", Toast.LENGTH_SHORT).show();
                    permissionCheck();
                }
                break;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//이미지 관련 처리
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {

            case PICK_FROM_ALBUM: {

                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();

                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 200); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 200); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_iMAGE); // CROP_FROM_iMAGE case문 이동
                System.out.println("Recipe"+ mImageCaptureUri.getPath().toString());
                break;
            }

            case CROP_FROM_iMAGE: {

                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if (resultCode != RESULT_OK)
                    return;

                final Bundle extras = data.getExtras();

                // CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Recipe/" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {
                    repPhotoBitmap = extras.getParcelable("data"); // CROP된 BITMAP
                    if (isDetail) {
                        recipeDetailImage.setImageBitmap(repPhotoBitmap); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
                        isDetail = false;
                    }
                    else {
                        recipeImageViewList.get(recipeImageViewList.size() - 1).setImageBitmap(repPhotoBitmap);
                    }

                    //storeCropImage(repPhotoBitmap, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                    //absoultePath = filePath;
                    break;
                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {

                    f.delete();
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setIngredient()
    {
        LinearLayout ingredLayout = new LinearLayout(RecipeDetailCreateView.this);
        ingredLayout.setOrientation(LinearLayout.HORIZONTAL);
        ingredLayout.setPadding(30,30,30,30);

        EditText etIngredient = new EditText(RecipeDetailCreateView.this);
        etIngredient.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
        etIngredient.setPadding(20,20,20,20);
        etIngredient.setHint("재료명");

        EditText etIngredientUnit = new EditText(RecipeDetailCreateView.this);
        etIngredientUnit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
        etIngredientUnit.setPadding(20,20,20,20);
        etIngredientUnit.setHint("재료 단위");

        etIngredientList.add(etIngredient);                         //생성된 재료 editText 를 넣는다.
        etIngredientUnitList.add(etIngredientUnit);                 //생성된 재료단위 editText 를 넣는다.
        ingredLayout.addView(etIngredient);
        ingredLayout.addView(etIngredientUnit);
        recipeIngredientLayout.addView(ingredLayout);
    }

    public void setRecipeContent()
    {
        LinearLayout recipeLayout = new LinearLayout(RecipeDetailCreateView.this);
        recipeLayout.setOrientation((LinearLayout.HORIZONTAL));
        recipeLayout.setPadding(30,30,30,30);

        TextView tvRecipeCount = new TextView(this);
        tvRecipeCount.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
        tvRecipeCount.setText(Integer.toString(recipeContCnt + 1));
        recipeContCnt++;

        final ImageView recipeImageView = new ImageView(this);
        // 요리 방법 이미지
        LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(300, 300,2f);
        recipeImageView.setLayoutParams(paramsImg);
        recipeImageView.setPadding(30,0,30,0);
        recipeImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        recipeImageView.setImageResource(R.drawable.logo);
        recipeImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 앨범 호출
                recipeImageViewList.add(recipeImageView);

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                //onActivityResult 호출
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        EditText etRecipe = new EditText(this);
        etRecipe.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,6f));
        //etRecipe.setPadding(20,20,20,20);
        etRecipe.setHint("설명");

        recipeContentList.add(etRecipe);
        recipeLayout.addView(tvRecipeCount);
        recipeLayout.addView(recipeImageView);
        recipeLayout.addView(etRecipe);
        recipeContentLayout.addView(recipeLayout);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void storeCropImage(Bitmap bitmap, String filePath) {                       //자른 이미지를 저장
        try {
            // Recipe 폴더를 생성하여 이미지를 저장하는 방식이다.
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Recipe";

            File directory_Recipe = new File(dirPath);
            if(!directory_Recipe.exists()) // Recipe 디렉터리에 폴더가 없다면 만든다(새로 이미지를 저장할 경우에 속한다.)
                directory_Recipe.mkdir();

            File copyFile = new File(filePath);
            BufferedOutputStream out = null;

            copyFile.createNewFile();

            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
