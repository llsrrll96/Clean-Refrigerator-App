package com.example.emptytherefrigerator.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeResult;

import java.util.ArrayList;

//조회된 레시피
//레시피, 식재료 검색결과 바운더리
public class MainSearchResult extends AppCompatActivity {

    private View view;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ArrayList<RecipeResult> resultList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_search_result);

        initializeView();
        setResultRecipe();
    }

    public void initializeView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.resultRecyclerView);
        searchView = (SearchView) findViewById(R.id.searchRecipe);
    }

    public void setResultRecipe()
    {
        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); //상하
        recyclerView.addItemDecoration(new RecyclerDecoration(50)); //아이템 간격
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MainSearchResultAdapter(insertItemList()));       //어댑터

        //리사이클러뷰 구분선
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    //데이터 넣는 곳
    ArrayList insertItemList(){
        resultList = new ArrayList<>(  );
        for(int i =0; i < 10; i++)
        {
            RecipeResult recipeResult = new RecipeResult(R.drawable.logo, "제목",i,i+1,"2020.09.20");
            resultList.add(recipeResult);
        }

//        RecipeList recipeList1 = new RecipeList(R.drawable.logo,"1111","name", 12);
//        RecipeList recipeList2 = new RecipeList(R.drawable.logo,"2222","hname",12);
//        RecipeList recipeList3 = new RecipeList(R.drawable.logo,"3333","name", 9);
//        RecipeList recipeList4 = new RecipeList(R.drawable.logo,"4444","name", 7);
//        RecipeList recipeList5 = new RecipeList(R.drawable.logo,"5555", "name",4);
//        RecipeList recipeList6 = new RecipeList(R.drawable.logo,"6666 ","' name", 3);

//        list.add(recipeList1);list.add(recipeList2);list.add(recipeList3);list.add(recipeList4);list.add(recipeList5);list.add(recipeList6);
        return resultList;
    }
}