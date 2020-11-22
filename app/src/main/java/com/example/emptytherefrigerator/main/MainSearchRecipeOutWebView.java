package com.example.emptytherefrigerator.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emptytherefrigerator.AsyncTasks.MyAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.login.UserInfo;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainSearchRecipeOutWebView extends AppCompatActivity {

    private TextView main_recipeout_title;
    private Button btnBack;
    private WebView webView;
    private WebSettings webSettings;
    private Intent intent;
    private String link, title;
    ImageButton btnLikeOut;
    boolean liked=false;
    boolean alreadyLiked = false;
    int recipeOutId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_search_recipe_out_web_view);


        init();
        getLikeOut();
        startWebView();
    }
    private void init()
    {
        intent = getIntent();
        link = intent.getExtras().getString("LINK");          //검색어
        title = intent.getExtras().getString("TITLE");
        recipeOutId = intent.getExtras().getInt("recipeOutId");
        main_recipeout_title = (TextView) findViewById(R.id.main_recipeout_title);
        main_recipeout_title.setText(title + " 에 대한 검색 결과");
        main_recipeout_title.setTextSize(18);
        main_recipeout_title.setGravity(Gravity.CENTER_VERTICAL);

        btnLikeOut = findViewById(R.id.btnLIkeOut);
        btnLikeOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                liked = !liked;

                if(liked)
                {
                    btnLikeOut.setImageResource(R.drawable.like_filled1);
                    if(alreadyLiked==false)
                    createLikeOut();
                }
                else
                {
                    btnLikeOut.setImageResource(R.drawable.like);
                    if(alreadyLiked==true)
                    {
                        deleteLikeOut();
                        alreadyLiked=false;
                    }

                }
            }
        });

        btnBack = (Button)findViewById(R.id.main_recipeout_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void getLikeOut()
    {
        MyAsyncTask getLikeOut = new MyAsyncTask();
        JSONObject object = new JSONObject();
        try
        {
            object.accumulate("recipeOutId", recipeOutId);
            System.out.println("recipeId : " + recipeOutId);
            object.accumulate("userId", UserInfo.getString(this, UserInfo.ID_KEY));
            String result = getLikeOut.execute("readLikeOut", object.toString()).get();

            if(result.equals("1"))
            {
                alreadyLiked = true;        //이전에 좋아요를 했다
                liked=true;
                btnLikeOut.setImageResource(R.drawable.like_filled1);
            }
            else if(result.equals("3"))
            {
                alreadyLiked = false;
                liked=false;
                btnLikeOut.setImageResource(R.drawable.like);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void startWebView()
    {
        webView = (WebView)findViewById(R.id.main_recipeout_webView);

        webView.setWebViewClient(new WebViewClient());
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(false);                   //새창 띄우기
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);    //자바스크립트, 새창 허용 여부
        webSettings.setLoadWithOverviewMode(true);                      //메타 태그
        webSettings.setUseWideViewPort(true);                           //화면 사이즈 맞추기
        webSettings.setSupportZoom(false);                              //줌 허용
        webSettings.setBuiltInZoomControls(false);                      //화면 확대 축소
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);            //브라우저 캐시
        webSettings.setDomStorageEnabled(true);                         //로컬저장소 허용 여부

        webView.loadUrl(link);
    }

    public void createLikeOut()
    {
        MyAsyncTask createLikeOut = new MyAsyncTask();
        JSONObject object = new JSONObject();
        try
        {
            object.accumulate("recipeOutId", recipeOutId);
            object.accumulate("userId", UserInfo.getString(this, UserInfo.ID_KEY));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            object.accumulate("uploadDate", format.format(new Date()));

            String result = createLikeOut.execute("createLikeOut", object.toString()).get();
            if(result.equals("1"))
                return;
            else
                Toast.makeText(this,"내부 서버 문제로 실행할 수 없습니다", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public void deleteLikeOut()
    {
        MyAsyncTask deleteLikeOut = new MyAsyncTask();
        JSONObject object = new JSONObject();
        try
        {
            object.accumulate("recipeOutId", recipeOutId);
            object.accumulate("userId", UserInfo.getString(this, UserInfo.ID_KEY));
            String result = deleteLikeOut.execute("deleteLikeOut", object.toString()).get();
            if(result.equals("1"))
                return;
            else
                Toast.makeText(this,"내부 서버 문제로 실행할 수 없습니다", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}