package com.example.emptytherefrigerator.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.RecipeMngAsyncTask;
import com.example.emptytherefrigerator.AsyncTasks.RecipeSearchAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.network.JsonParsing;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//검색 결과 레시피 리스트 화면
public class MainSearchResultView extends AppCompatActivity {

    private View view;

    private Button btnBack;
    private SearchView searchRecipe;
    private CheckBox recipeChkBox;
    private RecyclerView resultRecyclerView;
    private TextView btnRecipeOut;

    private ArrayList<RecipeIn> resultList;
    private Intent intent;
    private boolean preIsChk;                  //이전 화면에서의 식재료 체크 여부
    private String preQuery;                    //이전 화면 검색어

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_search_result);

        initializeView();
        setListener();
        showResultRecipe();
    }

    public void initializeView()
    {
        btnBack = (Button)findViewById(R.id.btnBack);
        searchRecipe = (SearchView)findViewById(R.id.searchRecipe);
        recipeChkBox = (CheckBox) findViewById(R.id.recipeChkBox);
        resultRecyclerView = (RecyclerView)findViewById(R.id.resultRecyclerView);
        btnRecipeOut = (TextView)findViewById(R.id.btnRecipeOut);
    }

    public void setListener()
    {
        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //외부 레시피
        btnRecipeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //검색 결과를 뿌려준다.
    public void showResultRecipe()
    {
        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); //상하
        resultRecyclerView.addItemDecoration(new RecyclerDecoration(50)); //아이템 간격
        resultRecyclerView.setLayoutManager(layoutManager);
        resultRecyclerView.setAdapter(new MainSearchResultAdapter(insertItemList()));       //어댑터

        //리사이클러뷰 구분선
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(resultRecyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        resultRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    // 서버로부터 레시피 데이터 얻는 함수
    ArrayList insertItemList()
    {
        resultList = new ArrayList<RecipeIn>( );
        intent = getIntent();
        preIsChk = intent.getExtras().getBoolean("IS_CHECKED");    //식재료 체크
        preQuery = intent.getExtras().getString("QUERY");          //검색어

        try
        {
            String recipeListData =  new RecipeSearchAsyncTask().execute("reqSearchRecipe","123").get();//서버쪽에 따라 변경될 수 있음
            resultList = JsonParsing.parsingRecipe(recipeListData);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return resultList;
    }
}