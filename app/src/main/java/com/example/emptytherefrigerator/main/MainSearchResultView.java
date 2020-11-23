package com.example.emptytherefrigerator.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.RecipeMngAsyncTask;
import com.example.emptytherefrigerator.AsyncTasks.RecipeSearchAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.network.JsonParsing;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//검색 결과 레시피 리스트 화면
public class MainSearchResultView extends AppCompatActivity {

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
    public void onRestart()
    {
        super.onRestart();
        // do some stuff here
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_search_result);

        initializeView();
        setListener();
        clickSearchRecipe();
        showResultRecipe();
    }

    public void initializeView()
    {
        btnBack = (Button)findViewById(R.id.btnBack);
        searchRecipe = (SearchView)findViewById(R.id.searchRecipe);
        recipeChkBox = (CheckBox) findViewById(R.id.recipeChkBox);
        resultRecyclerView = (RecyclerView)findViewById(R.id.resultRecyclerView);
        btnRecipeOut = (TextView)findViewById(R.id.btnRecipeOut);

        resultList = new ArrayList<RecipeIn>( );

        if(!recipeChkBox.isChecked())
            searchRecipe.setQueryHint("음식명을 검색해주세요");

        //검색 값 받기
        intent = getIntent();
        preIsChk = intent.getExtras().getBoolean("IS_CHECKED");    //식재료 체크
        preQuery = intent.getExtras().getString("QUERY");          //검색어

        if(preIsChk)    //식재료 기반
        {
            recipeChkBox.setChecked(true);
            searchRecipe.setQueryHint(trimEmptyString(toComma(preQuery)));
        }
        else
        {
            searchRecipe.setQueryHint(trimEmptyString(preQuery));
        }
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
                //intent 에 검색어와 같이 보낸다.
                intent = new Intent(v.getContext(), MainSearchResultRecipeOut.class);       //외부 검색
                intent.putExtra("IS_CHECKED",preIsChk);
                intent.putExtra("QUERY", preQuery);
                v.getContext().startActivity(intent);
            }
        });
        recipeChkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipeChkBox.isChecked())
                    searchRecipe.setQueryHint("재료 여러개 입력시 쉼표로 구분");
                else
                    searchRecipe.setQueryHint("음식명을 검색해주세요");
            }
        });
    }

    //검색 처리 함수
    public void clickSearchRecipe()
    {
        searchRecipe.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                //검색어 버튼이 눌러졌을 때 이벤트 처리
                Toast.makeText(searchRecipe.getContext(),"검색어: "+query,Toast.LENGTH_SHORT).show();

                intent = new Intent(getApplicationContext(), MainSearchResultView.class);

                if(recipeChkBox.isChecked())                //식재료 검색일때
                    intent.putExtra("IS_CHECKED", true);
                else
                    intent.putExtra("IS_CHECKED",false);
                intent.putExtra("QUERY", query);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                getApplicationContext().startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //검색어가 변경되었을 때 이벤트 처리

                return false;
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
    private String toBacktick(String query)
    {
        String[] querys = query.split(",");
        String result = "";
        for(int i = 0; i < querys.length; i++)
        {
            result += querys[i].replaceAll(" ","");
            if(i != querys.length -1)
                result += "`";
        }
        return result;
    }
    private String toComma(String query)
    {
        String[] querys = query.split("`");
        String result = "";
        for(int i = 0; i < querys.length; i++)
        {
            result += querys[i];
            if(i != querys.length -1)
                result += ",";
        }
        return result;
    }
    private String trimEmptyString(String query)
    {
        return query.trim();
    }

    // 서버로부터 레시피 데이터 얻는 함수
    ArrayList insertItemList()
    {
        try {
            //받은 검색 값으로 서버에 값 넘기기
            JSONObject jsonObjectQuery = new JSONObject();
            String recipeListData = "";

            if (preIsChk)
            {
                jsonObjectQuery.accumulate("ingredient", toBacktick(preQuery));
                recipeListData = new RecipeSearchAsyncTask().execute("reqSearchRecipeIng", jsonObjectQuery.toString()).get(); //재료 기반
            } else
            {
                jsonObjectQuery.accumulate("title", trimEmptyString(preQuery));
                recipeListData = new RecipeSearchAsyncTask().execute("reqSearchRecipe", jsonObjectQuery.toString()).get();    //요리 기반
            }
            resultList = JsonParsing.parsingRecipe(recipeListData);

            if(resultList.size() == 0)//값이 없을때
            {
                Toast toast = Toast.makeText(getApplicationContext(),"검색 결과 없음 !",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }


        } catch (Exception e)
        {
            e.printStackTrace();

        }

        return resultList;
    }
}