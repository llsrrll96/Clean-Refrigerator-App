package com.example.emptytherefrigerator.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.emptytherefrigerator.AsyncTasks.RecipeSearchAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.entity.RecipeOut;
import com.example.emptytherefrigerator.network.JsonParsing;

import org.json.JSONObject;

import java.util.ArrayList;

//외부 검색 결과
public class MainSearchResultRecipeOut extends AppCompatActivity
{
    private Button btnBack;
    private TextView textView;
    private RecyclerView resultoutRecyclerView;
    private ArrayList<RecipeOut> resultList;
    private Intent intent;
    private boolean preIsChk;                  //이전 화면에서의 식재료 체크 여부
    private String preQuery;                    //이전 화면 검색어

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_search_result_recipeout);

        initializeView();
        setListener();
        showResultRecipe();
    }
    private void initializeView()
    {
        btnBack = (Button)findViewById(R.id.btnBack);
        textView = (TextView) findViewById(R.id.textView);
        resultoutRecyclerView = (RecyclerView) findViewById(R.id.resultoutRecyclerView);

        resultList = new ArrayList<RecipeOut>( );

        //검색어, 체크박스 값 받아오기
        intent = getIntent();
        preIsChk = intent.getExtras().getBoolean("IS_CHECKED");    //식재료 체크
        preQuery = intent.getExtras().getString("QUERY");          //검색어

        textView.setText(preQuery + " 에 대한 검색 결과");
    }

    private void setListener()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //검색 결과를 뿌려준다.
    public void showResultRecipe()
    {
        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); //상하
        resultoutRecyclerView.addItemDecoration(new RecyclerDecoration(50)); //아이템 간격
        resultoutRecyclerView.setLayoutManager(layoutManager);
        resultoutRecyclerView.setAdapter(new MainSearchResultRecipeOutAdapter(insertItemList()));       //어댑터

        //리사이클러뷰 구분선
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(resultoutRecyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        resultoutRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    // 서버로부터 레시피 데이터 얻는 함수
    ArrayList insertItemList()
    {
        try
        {
            //받은 검색 값으로 서버에 값 넘기기
            JSONObject jsonObjectQuery = new JSONObject();
            jsonObjectQuery.accumulate("preQuery", preQuery);

            String recipeListData="";
            if(preIsChk)
                recipeListData =  new RecipeSearchAsyncTask().execute("readIngOutRecipe",jsonObjectQuery.toString()).get(); //재료 기반
            else
                recipeListData =  new RecipeSearchAsyncTask().execute("readFoodOutRecipe",jsonObjectQuery.toString()).get();    //요리 기반

            resultList = JsonParsing.parsingRecipeOut(recipeListData);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return resultList;
    }

}