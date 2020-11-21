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
import android.widget.TextView;

import com.example.emptytherefrigerator.R;

import org.w3c.dom.Text;

public class MainSearchRecipeOutWebView extends AppCompatActivity {

    private TextView main_recipeout_title;
    private Button btnBack;
    private WebView webView;
    private WebSettings webSettings;
    private Intent intent;
    private String link, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_search_recipe_out_web_view);

        init();
        startWebView();
    }
    private void init()
    {
        intent = getIntent();
        link = intent.getExtras().getString("LINK");          //검색어
        title = intent.getExtras().getString("TITLE");
        main_recipeout_title = (TextView) findViewById(R.id.main_recipeout_title);
        main_recipeout_title.setText(title + " 에 대한 검색 결과");
        main_recipeout_title.setTextSize(18);
        main_recipeout_title.setGravity(Gravity.CENTER_VERTICAL);

        btnBack = (Button)findViewById(R.id.main_recipeout_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
}